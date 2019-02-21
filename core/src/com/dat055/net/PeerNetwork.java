package com.dat055.net;

import com.dat055.model.entity.Player;
import com.dat055.net.message.JoinMessage;
import com.dat055.net.message.Message;
import com.dat055.net.message.PlayerMessage;
import com.dat055.net.message.Protocol;
import com.dat055.net.threads.Server;
import com.dat055.net.threads.Client;

import java.io.*;
import java.net.*;

/**
 * A Client and Server running on computer to communicate with other
 * peers.
 */
public class PeerNetwork extends Thread {
    private final int PERIOD = 1000;   // ms
    private final int TIMEOUT = 10; // Time until timout in seconds

    private Client client;
    private Server server;

    // Peer properties
    private String name; // Name of this peer
    private String peer; // Name of peer

    // Booleans
    private boolean isWaitingForPeer;
    private boolean isTimeOut;
    private boolean isConnected;

    private float timeout;
    /**
     * Sets default values
     * @param name
     */
    private PeerNetwork(String name) {
        this.name = name;
        isWaitingForPeer = true;
        isTimeOut = false;
        isConnected = false;
        timeout = 0;
    }

    /**
     * Ready-to-join-another-peer-constructor
     * @param client
     * @param server
     */
    public PeerNetwork(String name, Client client, Server server) {
        this(name);
        this.server = server;
        this.client = client;
        server.start();
        client.start();
        start();
    }

    /**
     * Awaits-another-peer-to-join-constructor
     */
    public PeerNetwork(String name, Server server) {
        this(name);
        this.server = server;
        server.start();
        start();
    }

    @Override
    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(PERIOD);
            } catch (InterruptedException e) { break; }

            // Calculate if there is a timeout
             if(isWaitingForPeer) {
                timeout += (float)PERIOD/1000;
                if(timeout >= TIMEOUT) {
                    System.out.println("Timeout!");
                    isTimeOut = true;
                    isWaitingForPeer = false;
                }
            }
            handleMessage();
        }
    }

    /**
     * Deserializes message and translates op codes to determine what to do next.
     * This method is called by run() for threads
     */
    private void handleMessage() {
        byte[] data;
        if((data = server.getData())== null)
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
                    case Protocol.OP_JOIN:
                        peer = ((JoinMessage)msg).getName();    // Get name of peer
                        System.out.println(peer + " has joined the battle!");

                        // Create a client for this peer if needed. (host side only)
                        if(isWaitingForPeer && client == null)
                            if(setClient(server.getCurrent().getAddress())) {
                                sendJoinRequest();  // Sends join request to peer
                                isWaitingForPeer = false;
                                isConnected = true;
                            }
                        break;

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
        client.dataToBeSent(out.toByteArray());
    }

    /**
     * Adds a peer to this network
     * @param addr
     * @return true if it worked, false if it did not work
     */
    private boolean setClient (InetAddress addr) {
        System.out.println("=== Client created! ===");
        return (client = PeerNetworkFactory.getClient(addr)) != null;
    }

    /**
     * Call this to see if another player has joined
     * @return
     */
    public boolean getIsWaiting() { return isWaitingForPeer; }
    public boolean getIsTimeout() {return isTimeOut;}
    public boolean getIsConnected() { return isConnected; }
    public String getPeerName() { return peer; }

    /**
     * Closes socket, client and server then tries to stop all threads within network
     */
    public void close() {
        if(server != null)
            server.close();
        if(client != null)
            client.close();
        this.interrupt();
    }
}
