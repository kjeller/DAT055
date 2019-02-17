package com.dat055.net;

import com.dat055.net.message.JoinMessage;
import com.dat055.net.message.Message;
import com.dat055.net.message.Protocol;
import com.dat055.net.threads.Receiver;
import com.dat055.net.threads.Sender;

import java.io.*;
import java.net.*;

public class PeerNetwork extends Thread {
    private DatagramSocket socket;
    private InetAddress destAddr;

    private Sender sender;
    private Receiver receiver;
    private boolean waitForPeer = true;

    /**
     * Ready-to-join-another-peer-constructor
     * @param port
     * @param destAddr
     */
    public PeerNetwork(int port, String destAddr) {
        try {
            this.destAddr = InetAddress.getByName(destAddr);
            socket = new DatagramSocket(port);
        }
        catch (UnknownHostException e) { e.printStackTrace(); }
        catch (SocketException e) { e.printStackTrace(); }
        sender = new Sender(socket, this.destAddr);
        receiver = new Receiver(socket);
        sendJoinRequest();  // Tells sender to send join requests
        start();
        sender.start();     // Starts sending current message
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

    public void sendJoinRequest() { sendMessage(new Message(Protocol.OP_JOIN)); }

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
    }

    /**
     * Deserializes message and translates op codes to determine what to do
     */
    private void receiveMessage() {
        byte[] data = receiver.getData();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream objIn;
        Message msg = null;
        try {
            objIn = new ObjectInputStream(in);
            msg = (Message)objIn.readObject();
        } catch (IOException ignored) {
        } catch (ClassNotFoundException ignored) {}

        // Translate messages
        if(msg != null && waitForPeer) {
            switch (msg.getOp()) {
                case Protocol.OP_JOIN:
                    System.out.println(((JoinMessage)msg).getName());
                    sendJoinRequest();
                    if(setSocketConn(receiver.getCurrent().getAddress()))  // Sets address to other peer
                        waitForPeer = false;
                    break;
                case Protocol.OP_LEAVE: break;
                case Protocol.OP_PLAYER: break;
                case Protocol.OP_HOOK: break;
            }
        }
    }

    /**
     * Sets socket for sender. Needs to be called by "host"
     * @param addr
     * @return true if it worked, false if it did not work
     */
    private boolean setSocketConn (InetAddress addr) {
        try {
            this.destAddr = addr;
            sender = new Sender(socket, destAddr);
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
