package com.dat055.net;

import com.dat055.model.entity.Player;
import com.dat055.net.message.JoinMessage;
import com.dat055.net.message.Message;
import com.dat055.net.message.PlayerMessage;
import com.dat055.net.message.Protocol;
import com.dat055.net.threads.Receiver;
import com.dat055.net.threads.Sender;

import java.io.*;
import java.net.*;

public class PeerNetwork extends Thread {
    private DatagramSocket socket;
    private InetAddress destAddr;
    private int port;

    private Sender sender;
    private Receiver receiver;
    private boolean waitForPeer = true;

    /**
     * Ready-to-join-another-peer-constructor
     * @param port
     * @param destAddr
     */
    public PeerNetwork(int port, String destAddr) {
        this.port = port;
        try {
            this.destAddr = InetAddress.getByName(destAddr);
        }
        catch (UnknownHostException e) { e.printStackTrace(); }
        //TODO: Socket wont bind to port, fix this plox

        try {
            sender = new Sender(new DatagramSocket(), this.destAddr);
            receiver = new Receiver(new DatagramSocket(port));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        sendJoinRequest("Kjelle");  // Tells sender to send join requests
        //TODO: Set name through menu
        start();
        sender.start();     // Starts sending current message
        receiver.start();
    }

    /**
     * Awaits-another-peer-to-join-constructor
     * @param port
     */
    public PeerNetwork(int port) {
        try { socket = new DatagramSocket(port); }
        catch (SocketException e) { e.printStackTrace(); }
        receiver = new Receiver(socket);
        start();
        receiver.start();
    }

    @Override
    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
            receiveMessage();
        }
    }

    /**
     * Closes socket and tries to stop all threads within network
     */
    private void close() {
        socket.close();
        sender.interrupt();
        receiver.interrupt();
        this.interrupt();
    }

    public void sendJoinRequest(String name) { sendMessage(new JoinMessage(name)); }
    public void sendPlayerUpdate(Player player) {
        sendMessage(new PlayerMessage(player));
    }

    /**
     * Serializes message and tells Sender to start send
     * @param msg
     */
    private void sendMessage(Message msg) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objOut;
        try {
            objOut = new ObjectOutputStream(out);
            objOut.writeObject(msg);
        } catch (IOException ignored) {}

        sender.dataToBeSent(out.toByteArray());
        if(!sender.isAlive())
            sender.start();
    }


    /**
     * Deserializes message and translates op codes to determine what to do
     */
    private void receiveMessage() {
        byte[] data = receiver.getData();
        if(data == null) { return; }
        ObjectInputStream objIn;
        Message msg;
        try {
            objIn =  new ObjectInputStream(new ByteArrayInputStream(data));
            msg = (Message) objIn.readObject();
            objIn.close();

            // Translate messages
            if(msg != null && waitForPeer) {
                switch (msg.getOp()) {
                    case Protocol.OP_JOIN:
                        System.out.println((JoinMessage)msg);
                        if(setSocketConn(receiver.getCurrent().getAddress()))  // Sets address to other peer
                            waitForPeer = false;
                        sendJoinRequest("kjelle"); //TODO: Fix this too
                        break;

                    case Protocol.OP_PLAYER: System.out.println((PlayerMessage)msg);break;
                    case Protocol.OP_HOOK: break;
                    case Protocol.OP_LEAVE: close(); break;
                }
            }
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {e.printStackTrace();}


    }

    /**
     * Sets socket for sender. Needs to be called by "host"
     * @param addr
     * @return true if it worked, false if it did not work
     */
    private boolean setSocketConn (InetAddress addr) {
        try {
            this.destAddr = addr;
            sender = new Sender(new DatagramSocket(), this.destAddr);
        } catch (Exception ignored) { return false; }
        return true;
    }

    /**
     * Call this to see if another player has joined
     * @return
     */
    public boolean getStatus() {
        return waitForPeer;
    }
}
