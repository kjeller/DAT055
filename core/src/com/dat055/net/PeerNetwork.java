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
    private final int PERIOD = 2;   // ms
    private final int TIMEOUT = 10; // Time until timout in seconds
    private Client client;
    private Server server;
    private String name; // Name of this peer

    private float timeout = 0;
    private boolean isWaitingForPeer = true;
    private boolean isTimeOut = false;
    private boolean isConnected = false;

    /**
     * Ready-to-join-another-peer-constructor
     * @param client
     * @param server
     */
    public PeerNetwork(String name, Client client, Server server) {
        this.name = name;
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
        this.name = name;
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
            /* if(isWaitingForPeer) {
                timeout += (float)PERIOD/1000;
                if(timeout >= TIMEOUT) {
                    isTimeOut = true;
                    isWaitingForPeer = false;
                }
            }*/
            receiveMessage();
        }
    }

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

    /**
     * Deserializes message and translates op codes to determine what to do
     */
    private void receiveMessage() {
        byte[] data = server.getData();
        if(data == null)  return;
        System.out.println("Peernetwork got data from Server.");
        ObjectInputStream objIn;
        Message msg;
        try {
            objIn =  new ObjectInputStream(new ByteArrayInputStream(data));
            msg = (Message) objIn.readObject();
            objIn.close();
            System.out.println("Message de-serializes read!");
            // Translate messages to a format which can be handled.
            if(msg != null && isWaitingForPeer) {
                // Translate OP code in message and cast based on code.
                switch (msg.getOp()) {
                    case Protocol.OP_JOIN:
                        System.out.println((JoinMessage)msg);
                        if(isWaitingForPeer)
                            setClient(server.getCurrent().getAddress());
                        break;

                    case Protocol.OP_PLAYER: System.out.println((PlayerMessage)msg);break;
                    case Protocol.OP_HOOK: break;
                    case Protocol.OP_LEAVE: close(); break;
                }
            }
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {e.printStackTrace();}
    }

    public void sendJoinRequest(String name) { sendMessage(new JoinMessage(name)); }
    public void sendPlayerUpdate(Player player) {
        sendMessage(new PlayerMessage(player));
    }

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
        System.out.println("Message put in client!");
        client.dataToBeSent(out.toByteArray());
        if(!client.isAlive())
            client.start();
    }

    /**
     * Adds a peer to this network
     * @param addr
     * @return true if it worked, false if it did not work
     */
    private boolean setClient (InetAddress addr) {
        return (client = PeerNetworkFactory.getClient(addr)) != null;
    }

    /**
     * Call this to see if another player has joined
     * @return
     */
    public boolean getIsWaiting() {
        return isWaitingForPeer;
    }
    public boolean getIsTimeout() {return isTimeOut;}
    public boolean getIsConnected() { return isConnected; }
}
