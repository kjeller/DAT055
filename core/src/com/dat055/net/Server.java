package com.dat055.net;

import com.dat055.model.entity.character.Player;
import com.dat055.net.message.JoinMessage;
import com.dat055.net.message.Message;
import com.dat055.net.message.PlayerMessage;
import com.dat055.net.threads.TCPHandler;
import com.dat055.net.threads.UDPHandler;

import java.io.*;
import java.net.*;

import static com.dat055.net.message.Protocol.*;

/**
 * A thread that waits for connection
 * to a socket from another client. When connected
 * a datagramsocket is created to receive packets
 * from same client. This class also handles responses
 * and is basically the connection between the game and
 * the other peer.
 * @author Karl Strålman
 * @version 2019-02-26
 */
public class Server extends Thread{
    // TCPcommunication
    private ServerSocket ss;
    private Socket cs; // Connected socket
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private TCPHandler tcpHandler;

    private Client client; // Client that communicates with other server

    // UDP communication
    private DatagramSocket ds;
    private DatagramPacket current;
    private UDPHandler udpHandler;

    // Last message received (UDPHandler)
    private PlayerMessage lastPlayerMessage;

    // Server properties
    private int port;
    private boolean isRunning;

    // Game properties
    private String chosenMap;
    private String name;

    public Server(String name, int port) {
        this.port = port;
        this.name = name;
        isRunning = false;
        tcpHandler = new TCPHandler(this);
        udpHandler = new UDPHandler(this);
    }

    /**
     * Starts server and creates a client that responds to other server
     * Use this when joining another server.
     * @return
     */
    public boolean startServerAndClient(String addr) {
        tcpHandler.setClient(client = new Client(addr, port));
        return startServer(null);
    }

    /**
     * Starts server when a client is not specified.
     * Use this if you are hosting a server.
     * @param chosenMap map chosen by player
     * @return true if succeeded
     */
    public boolean startServer(String chosenMap) {
        this.chosenMap = chosenMap;
        start();
        return true;
    }

    /**
     * Creates server socket and waits for socket to connect.
     */
    private boolean initialize() {
        try {
            ss = new ServerSocket(port);
            // Serversocket is nowcreated
            cs = ss.accept();   // Blocking method awaiting client to connect
            // A client has now connected to the server.
            out = new ObjectOutputStream(cs.getOutputStream());
            in = new ObjectInputStream(cs.getInputStream());
            ds = new DatagramSocket(port);  // Create datagramsocket to receive UDPHandler packets

            // For host - create client
            if(client == null) {
                tcpHandler.setClient(client = new Client(cs.getInetAddress(), port));
                tcpHandler.writeClientMessage(new JoinMessage(name, chosenMap));
            }
            tcpHandler.start();
            udpHandler.start();
        } catch (IOException e) { System.out.println(e); return false;}
        return true;
    }

    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {}
            // If there exist no socket connected to server
            if(cs == null) {
                    initialize(); // Initialize server and get socket connected.
            } else {
                // Check if socket is still connected
                if(!cs.isConnected()) {
                    cs = null;
                    isRunning = false;
                    close();
                }
            }
        }
    }

    /**
     * Closes sockets and interrupts threads.
     */
    private void close() {
        try {
            ss.close();
        } catch (IOException ignored) {}
        ds.close();
        tcpHandler.interrupt();
        udpHandler.interrupt();
        client.tcpHandler.interrupt();
        client.udpHandler.interrupt();

        this.interrupt();
    }

    // == Functions that handle the responses TCPHandler/UDPHandler ==

    /**
     * Handles UDP packets sent from a client
     * by deserializing a message and translating the op code in the msg.
     * It then determines what the host will answer with.
     * This method is called by a UDPHandler.
     * to serversocket.
     */
    public void handlePackets(byte[] data) {
        if(data == null)
            return;
        // Data deserialized to be handled
        ObjectInputStream objIn;
        Message msg;
        try {
            objIn =  new ObjectInputStream(new ByteArrayInputStream(data));
            msg = (Message) objIn.readObject();
            // Deserialized message now read

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
        } catch (Exception ignored) {}
    }

    /**
     * Handle TCP messages and responds to them. This is basically
     * the flow of the server.
     * @param msg
     */
    public void handleServerResponses(Message msg) {
        if(msg != null) {
            // Translate OP code in message and cast message based on code.
            switch (msg.getOp()) {
                case OP_JOIN:
                    String chosenMap = ((JoinMessage) msg).getMap();
                    // Assign map to peer
                    if(chosenMap != null) {
                        this.chosenMap = chosenMap; // map selected
                         // Send response to other peer for getting this name.
                        tcpHandler.writeClientMessage(new JoinMessage(name, null));
                    }
                    tcpHandler.writeClientMessage(new Message(OP_CHAR_SEL));
                    isRunning = true; // server is now running
                    client.start();
                    break;
                case OP_CHAR_SEL:
                    //TODO: Fix character selection here
                    break;
                case OP_LEAVE:
                    tcpHandler.writeClientMessage(new Message(OP_LEAVE));
                    close();
                    break;
            }
        }
    }

    /**
     * Updates player with last received message
     * @param player
     */
    public void updatePlayer(Player player) { if(lastPlayerMessage != null)lastPlayerMessage.setPlayerProperties(player); }

    /**
     * Packages player updates to client
     * @param player
     */
    public void sendPlayerUpdate(Player player) { setClientPacketData(new PlayerMessage(player)); }

    /**
     * Serializes message and set to client
     * @param msg
     */
    private void setClientPacketData(Message msg) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objOut;
        try {
            objOut = new ObjectOutputStream(out);
            objOut.writeObject(msg);
        } catch (IOException ignored) {}
        client.setPacketData(out.toByteArray());
    }

    /**
     * @return name of other client
     */
    public String getClientName() { return cs.getInetAddress().getHostName(); }

    /**
     * @return chosen map.
     */
    public String getChosenMap() { return chosenMap; }

    /**
     * @return datagramsocket used receive packets.
     */
    public DatagramSocket getDatagramSocket() { return ds; }

    /**
     * @return outstream to other peer.
     */
    public ObjectOutputStream getOut() { return out; }

    /**
     * @return instream to other peer.
     */
    public ObjectInputStream getIn() { return in; }

    public boolean isRunning() { return isRunning; }
    public boolean isConnected() { return cs.isConnected(); }
}
