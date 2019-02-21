package com.dat055.net;

import com.dat055.model.entity.Player;
import com.dat055.net.message.JoinMessage;
import com.dat055.net.message.Message;
import com.dat055.net.message.PlayerMessage;
import com.dat055.net.message.Protocol;

import java.io.*;
import java.net.*;


/**
 * A Client and Server running on computer to communicate with other
 * peers.
 */
public class PeerNetwork extends Thread {
    private final int PERIOD = 1000;   // ms
    private final int TIMEOUT = 10; // Time until timout in seconds

    // TCP communication
    private ServerSocket ss;
    private ObjectInputStream in;     // will be connected to client's output stream
    private ObjectOutputStream out;    // will be connected to client's input stream
    private int listenPort; // Port used by server

    // UDP communication
    private DatagramSocket ds;
    private DatagramPacket current; // will be used to determine where to packet came from
    private byte[] data; // data is put here after deserialization

    private Client client;  // Client added to server

    // Server properties
    private String name; // Name of this client
    private String choosenMap;
    private boolean isWaitingForPeer;
    private boolean isTimeOut;
    private boolean isConnected;

    private float timeout;

    private PeerNetwork(String name, int listenPort) {
        this.name = name;
        this.listenPort = listenPort;
        isWaitingForPeer = true;
        isTimeOut = false;
        isConnected = false;
        timeout = 0;
        runServer();
    }

    /**
     * Host of server uses this constructor
     * @param name
     * @param listenPort
     */
    public PeerNetwork(String name, int listenPort, String choosenMap) {
        this(name, listenPort);
        this.choosenMap = choosenMap;
    }

    /**
     * The one who joins uses this
     * @param name of this server
     * @param client to connect to other server
     * @param listenPort
     */
    public PeerNetwork(String name, String addr, int listenPort) {
        this(name, listenPort);
        try {
            client = new Client(InetAddress.getByName(addr), listenPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        start();
    }


    @Override
    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(PERIOD);
            } catch (InterruptedException e) { break; }

            // Calculate if there is a timeout
            /*if(isWaitingForPeer) {
                timeout += (float)PERIOD/1000;
                if(timeout >= TIMEOUT) {
                    System.out.println("PeerNetwork timed out!");
                    isTimeOut = true;
                    isWaitingForPeer = false;
                    close();
                }
            }*/

            // Send udp packets if client is connected
            if(client.isConnected())
                receiveDatagramPacket();
        }
    }

    public void runServer() {
        ss = null;
        System.out.println("Trying to start server..");
        try {
            ss = new ServerSocket(listenPort);
            System.out.println("Waiting for other client..");
            Socket cs = ss.accept(); // Wait for connection
            System.out.println("Client connected: " + cs);
            out = new ObjectOutputStream(cs.getOutputStream()); // ObjectOutputStream before inputstream!
            in = new ObjectInputStream(cs.getInputStream());

            if(client == null)
                setClient(cs.getInetAddress());
            writeMessage(new Message(Protocol.OP_JOIN)); // Writes to connected client

            // Create a datagramsocket to handle udp connection
            ds = new DatagramSocket(listenPort);
            start();
        } catch (Exception e) { System.out.println(e); }
    }


    /**
     * Write message to output stream - will be sent to clients input stream
     * @param msg that will be sent
     */
    public void writeMessage(Message msg) {
        try {
            out.writeObject(msg);
            System.out.printf("Msg: {%s} sent to other client. \n", msg);
        } catch (IOException ignored) {}
    }

    /**
     * Reads message from stream - from client
     * @return message from stream
     */
    public Message readMessage() {
        try {
            return (Message)in.readObject();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /**
     * Receive a packet from DatagramSocket
     */
    private void receiveDatagramPacket() {
        byte[] data = new byte[1024];
        current = new DatagramPacket(data, data.length);
        try {
            ds.receive(current);
            this.data = data;
            System.out.printf("--Received package from %s!\n", current.getAddress());
        } catch (IOException ignored) {}
    }

    /**
     * Handles UDP packets sent from other client
     * by deserializing message and translating the op code in the msg.
     * It then determines what the host will answer with.
     * This method is called by thread if tcp connection has been
     * established.
     */
    private void handleUpdates() {
        if(data == null)
            return;
        System.out.println("-Peernetwork got data from Server.");
        ObjectInputStream objIn;
        Message msg;
        try {
            objIn =  new ObjectInputStream(new ByteArrayInputStream(data));
            msg = (Message) objIn.readObject();
            objIn.close();
            data = null; // Clear data for next read - this is needed to get the "handshake" right
            System.out.println("--Message de-serializes read!");

            // Translate messages to a format which can be handled.
            if(msg != null) {
                // Translate OP code in message and cast based on code.
                switch (msg.getOp()) {
                    case Protocol.OP_PLAYER: System.out.println(msg);break;
                    case Protocol.OP_HOOK: break;
                    case Protocol.OP_LEAVE: close(); break;
                }
            }
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {e.printStackTrace();}
    }

    public void sendJoinRequest() { sendMessage(new JoinMessage(name)); }
    public void sendPlayerUpdate(Player player) { sendMessage(new PlayerMessage(player)); }

    /**
     * Serializes message and tells Client to start send message
     * @param msg
     */
    private void sendMessage(Message msg) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objOut;
        try {
            objOut = new ObjectOutputStream(out);
            objOut.writeObject(msg);
        } catch (IOException ignored) {}
        System.out.println("==> Message put in client!");
        client.setPacketData(out.toByteArray());
    }


    private void setClient (InetAddress addr) {
        System.out.println("=== Client created! ===");
        client = new Client(addr, listenPort);
    }
    public void setMap(String map) {choosenMap = map;}

    /**
     * Call this to see if another player has joined
     * @return
     */
    public boolean getIsWaiting() { return isWaitingForPeer; }
    public boolean getIsTimeout() {return isTimeOut;}
    public boolean getIsConnected() { return isConnected; }
    public byte[] getData() { return data; }
    public DatagramPacket getCurrent() { return current; }
    public String getChoosenMap() { return choosenMap; }

    /**
     * Closes socket, client and server then tries to stop all threads within network
     */
    private void close() {
        if(ss != null) {
            try {
                ss.close();
            } catch (IOException e) { e.printStackTrace(); }
        }
        if(client != null)
            client.close();
        this.interrupt();
    }

    public String getAddress() { return client.getAddress(); }
}
