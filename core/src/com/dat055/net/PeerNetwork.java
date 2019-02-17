package com.dat055.net;

import com.dat055.net.threads.Receiver;
import com.dat055.net.threads.Sender;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

import java.io.IOException;
import java.net.*;

public class PeerNetwork extends Thread {
    private DatagramSocket socket;
    private InetAddress destAddr;
    private int port;

    private Sender sender;
    private Receiver receiver;
    private boolean waitForPeer;

    /**
     * Ready-to-join-another-peer-constructor
     * @param port
     * @param destAddr
     */
    public PeerNetwork(int port, String destAddr) {
        this.port = port;
        try {
            this.destAddr = InetAddress.getByName(destAddr);
            socket = new DatagramSocket(port);
        }
        catch (UnknownHostException e) { e.printStackTrace(); }
        catch (SocketException e) { e.printStackTrace(); }
        sender = new Sender(socket, this.destAddr);
        receiver = new Receiver(socket);
        start();
    }

    /**
     * Awaits-another-peer-to-join-constructor
     * @param port
     */
    public PeerNetwork(int port) {
        this.port = port;
        try { socket = new DatagramSocket(port); }
        catch (SocketException e) { e.printStackTrace(); }
        receiver = new Receiver(socket);
        waitForPeer = true;
        start();
    }

    @Override
    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
            receive();

            if(waitForOtherPlayer)
                send(RESPONSE_JOIN);
            else
                send("test");
        }
    }

    /**
     * Sends a join request to other peer then awaiting answer
     */
    public void sendJoinRequest() {
        Message msg = new Message(Message.OP_JOIN, )
    }

    /**
     * Sends string to socket
     * @param msg
     */
    public void send(String msg) {
        byte[] data = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, destAddr, port);
        try {
            socket.send(packet);
        } catch (IOException e) { System.out.println(e); }
    }

    /**
     * Receive packet from socket
     * @throws IOException
     */
    private void receive() {
        DatagramPacket packet;
        byte[] data = new byte[1024];
        packet = new DatagramPacket(data, data.length);

        try { socket.receive(packet); }
        catch (IOException e) { e.printStackTrace(); }

        if(data == RESPONSE_JOIN.getBytes()) {
            if(setSocketConn(packet.getAddress())) // Try to set new socket to player
                waitForOtherPlayer = false;
        } else if( data == RESPONSE_CLOSE.getBytes()) {
            close();  // Close connection and kill thread
        }

        // For debug
        System.out.printf("(%s:%s): %s\n",
                packet.getAddress(), packet.getPort(), new String(data));
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

    /**
     * Tries to create a new socket for an address
     * @param addr
     * @return
     */
    private boolean setSocketConn (InetAddress addr) {
        try {
            this.destAddr = addr;
            socket = new DatagramSocket(port);
            waitForPeer = false;
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
