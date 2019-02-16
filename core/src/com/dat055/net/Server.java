package com.dat055.net;

import java.io.IOException;
import java.net.*;

public class Server extends Thread {
    private final String RESPONSE_JOIN = "join";
    private final String RESPONSE_CLOSE = "close";

    private DatagramSocket socket;
    private InetAddress destAddr;
    private int port;

    private boolean waitForOtherPlayer;

    public Server(int port, String destAddr) {
        this.port = port;
        try {
            this.destAddr = InetAddress.getByName(destAddr);
            socket = new DatagramSocket(port);
            waitForOtherPlayer = false;
        }
        catch (UnknownHostException e) { e.printStackTrace(); }
        catch (SocketException e) { e.printStackTrace(); }
        start();
    }

    /**
     * Awaits another player to join constructor
     * @param port
     */
    public Server(int port) {
        this.port = port;
        waitForOtherPlayer = true;
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
     * Closes socket and tries to stop thread
     */
    private void close() {
        socket.close();
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
            waitForOtherPlayer = false;
        } catch (Exception ignored) { return false; }
        return true;
    }

    /**
     * Call this to see if another player has joined
     * @return
     */
    public boolean getStatus() {
        return waitForOtherPlayer;
    }
}
