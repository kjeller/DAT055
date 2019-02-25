package com.dat055.net;

import com.dat055.model.entity.Player;
import com.dat055.net.message.JoinMessage;
import com.dat055.net.message.Message;
import com.dat055.net.message.PlayerMessage;

import java.io.*;
import java.net.*;

import static com.dat055.net.message.Protocol.*;

public class Server extends Thread {
    private final int PERIOD = 50;

    // TCP communication
    private ServerSocket ss;
    private Socket cs; // Connected socket
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Client client; // Client that communicates with other server

    // UDP communication
    private DatagramSocket ds;
    private DatagramPacket current;

    // Last message received (UDP)
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
    }

    /**
     * Creates server socket and waits for socket to connect.
     */
    private boolean initialize() {
        try {
            ss = new ServerSocket(port);
            System.out.println("[Server] Serversocket created on port " + port);
            cs = ss.accept();   // Blocking method awaiting client to connect
            System.out.println("[Server] A client has connected to the server!");
            out = new ObjectOutputStream(cs.getOutputStream());
            in = new ObjectInputStream(cs.getInputStream());

            // For host - create client
            if(client == null) {
                client = new Client(cs.getInetAddress(), port);
                client.writeMessage(new JoinMessage(name, chosenMap));
            }

            ds = new DatagramSocket(port);  // Create datagramsocket to receive UDP packets
        } catch (IOException e) { return false;}
        return true;
    }

    /**
     * Starts server and creates a client that responds to other server
     * @return
     */
    public boolean startServerAndClient(String addr) {
        client = new Client(addr, port);
        System.out.println("[Server] Created a client");
        return startServer(null);
    }

    public boolean startServer(String chosenMap) {
        this.chosenMap = chosenMap;
        start();
        System.out.println("[Server] Thread started.");
        return true;
    }

    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(PERIOD);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // If there exist no socket connected to server
            if(cs == null) {
                    initialize(); // Initialize server and gets socket connected
            } else {
                // Check if socket is still connected
                if(cs.isConnected()) {
                    receiveTCP();
                    receiveUDP();
                } else {
                    cs = null;
                    isRunning = false;
                    System.out.println("[Server] Lost connection to client.");
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
        this.interrupt();
    }

    /**
     * Creates new thread that awaits tcp response.
     */
    private void receiveTCP() {
        Thread t = new Thread(() -> {
            handleServerResponses(readMessage());
        });
        t.start();
        t.interrupt();
    }

    /**
     * Creates new thread that awaits udp packets
     */
    private void receiveUDP() {
        Thread t = new Thread(() -> {
            try {
                byte[] data = new byte[1024];
                current = new DatagramPacket(data, data.length);
                ds.receive(current);
                System.out.printf("<=== Received package from %s!\n", current.getAddress());
                handlePackets(data);
            } catch (IOException e) { System.out.println(e);}
        });
        t.start();
        t.interrupt();
    }

    /**
     * Reads message from stream - from client
     * @return message from stream
     */
    public Message readMessage() {
        try {
            return (Message)in.readObject();
        } catch (Exception e) { System.out.println(e); }
        return null;
    }


    // == Functions that handle the responses TCP/UDP ==

    /**
     * Handles UDP packets sent from other client
     * by deserializing message and translating the op code in the msg.
     * It then determines what the host will answer with.
     * This method is called by thread if a client is connected
     * to serversocket.
     */
    private void handlePackets(byte[] data) {
        if(data == null)
            return;
        System.out.println("Data deserialized to be handled.");
        ObjectInputStream objIn;
        Message msg;
        try {
            objIn =  new ObjectInputStream(new ByteArrayInputStream(data));
            msg = (Message) objIn.readObject();
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
        } catch (Exception ignored) {}
    }

    /**
     * Handle TCP messages and responds to them. This is basically
     * the flow of the server.
     * @param msg
     */
    private void handleServerResponses(Message msg) {
        if(msg != null) {
            // Translate OP code in message and cast message based on code.
            switch (msg.getOp()) {
                case OP_JOIN:
                    System.out.println("=== JOIN ===");
                    String chosenMap = ((JoinMessage) msg).getMap();
                    System.out.printf("Message from other peer: %s\n", msg);

                    // Assign map to peer
                    if(chosenMap != null) {
                        this.chosenMap = chosenMap;
                        System.out.printf("Map %s selected.\n", chosenMap);
                        client.writeMessage(new JoinMessage(name, null));
                    }
                    client.writeMessage(new Message(OP_CHAR_SEL));
                    isRunning = true;
                    break;
                case OP_CHAR_SEL:
                    System.out.println("=== CHAR_SEL ===");
                    System.out.println("nothing happens here yet");
                    //TODO: Somehow get menucontroller method call here?
                    //isRunning = true;

                    System.out.println("Started client!");
                    //client.start();
                    break;
                case OP_LEAVE:
                    client.writeMessage(new Message(OP_LEAVE));
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

    private void setClient (InetAddress addr) {
        System.out.println("=== Client created! ===");
        client = new Client(addr, port);
    }

    public String getClientName() { return cs.getInetAddress().getHostName(); }
    public String getChosenMap() { return chosenMap; }
    public boolean isRunning() { return isRunning; }
}
