package com.dat055.net;

import com.dat055.model.entity.Player;
import com.dat055.net.message.JoinMessage;
import com.dat055.net.message.Message;
import com.dat055.net.message.PlayerMessage;

import java.io.*;
import java.net.*;

import static com.dat055.net.message.Protocol.*;


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
    private PlayerMessage lastPlayerMessage;
    private byte[] data; // data is put here after deserialization

    private Client client;  // Client used to communicate with other server
    private String otherClient;

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
    }

    /**
     * Host of server uses this constructor
     * @param name
     * @param listenPort
     */
    public PeerNetwork(String name, int listenPort, String choosenMap) {
        this(name, listenPort);
        this.choosenMap = choosenMap;
        runServer();
        writeMessage(new Message(OP_CHAR_SEL));
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
            System.out.printf("Socket created for for %s:%d", addr, listenPort);
            client = new Client(InetAddress.getByName(addr), listenPort);
        } catch (UnknownHostException e) { System.out.println("Unknown host"); }
        runServer();
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

            Message msg = client.readMessage();
            if(msg != null) {
                // Translate OP code in message and cast based on code.
                switch (msg.getOp()) {
                    case OP_JOIN:
                        otherClient = ((JoinMessage)msg).getName();
                        String choosenMap = ((JoinMessage) msg).getMap();
                        System.out.println(otherClient);

                        // Assign map to peer
                        if(choosenMap != null) {
                            this.choosenMap = choosenMap;
                            System.out.printf("Map %s selected.");
                        }

                        break;
                    case OP_CHAR_SEL:
                        System.out.println("Character select time!");
                        //TODO: Somehow get menucontroller method call here?
                        break;
                    case OP_LEAVE:
                        writeMessage(new Message(OP_LEAVE));
                        close();
                        break;
                }

                handleUpdates();
            }

            // Send udp packets if client is connected
            /* if(client.isConnected())
                receiveDatagramPacket();*/
        }
    }

    public void runServer() {
        ss = null;
        System.out.println("Trying to start server..");
        try {
            ss = new ServerSocket(listenPort);
            System.out.println("Waiting for other client..");
            Socket cs = ss.accept(); // Wait for connection
            isConnected = true;
            System.out.println("Client connected: " + cs);
            out = new ObjectOutputStream(cs.getOutputStream()); // ObjectOutputStream before inputstream!
            in = new ObjectInputStream(cs.getInputStream());

            // Creates new client for hosting peer
            if(client == null)
                setClient(cs.getInetAddress());

            writeMessage(new JoinMessage(name, choosenMap)); // Writes to connected client

            // Create a datagramsocket to handle udp connection
            //ds = new DatagramSocket(listenPort);
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
        } catch (Exception e) {System.out.println("Connection lost."); close(); }
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
            data = null; // Clear data for next read - this is needed to get the "handshake" right
            System.out.println("--Message de-serializes read!");

            // Translate messages to a format which can be handled.
            if(msg != null) {
                // Translate OP code in message and cast based on code.
                switch (msg.getOp()) {
                    case OP_PLAYER:
                        lastPlayerMessage = (PlayerMessage)msg;
                        break;
                    case OP_HOOK: break;
                    case OP_LEAVE: close(); break;
                }
            }
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {e.printStackTrace();}
    }

    public void updatePlayer(Player player) { if(lastPlayerMessage != null)lastPlayerMessage.setPlayerProperties(player); }

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
    public boolean isWaiting() { return isWaitingForPeer; }
    public boolean isTimeout() {return isTimeOut;}
    public boolean isConnected() { return isConnected; }
    public byte[] getData() { return data; }
    public DatagramPacket getCurrent() { return current; }
    public String getChoosenMap() { return choosenMap; }
    public String getOtherClient() { return otherClient;}
    public String getAddress() { return client.getAddress(); }

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


}
