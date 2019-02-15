package com.dat055.net;

import java.io.IOException;
import java.net.*;

public class Server extends Thread {
    private DatagramSocket socket;
    private int destPort;
    private InetAddress destAddr;
    private boolean isAlive = true;

    public Server(int srcPort, int destPort, String destAddr) {
        this.destPort = destPort;
        try {
            this.destAddr = InetAddress.getByName(destAddr);
            socket = new DatagramSocket(srcPort);
        }
        catch (UnknownHostException e) { e.printStackTrace(); }
        catch (SocketException e) { e.printStackTrace(); }
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
            send("snokN");
            receive();

            if(!isAlive) close();
        }
    }

    /**
     * Sends string to socket
     * @param msg
     */
    public void send(String msg) {
        byte[] data = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, destAddr, destPort);
        try {
            socket.send(packet);
        } catch (IOException e) { System.out.println(e); }
    }

    /**
     * Receive packet from socket
     * @throws IOException
     */
    public void receive() {
        DatagramPacket packet;
        byte[] data = new byte[1024];
        packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
        } catch (IOException e) { e.printStackTrace(); }
        System.out.printf("(%s:%s): %s\n",
                packet.getAddress(), packet.getPort(), new String(data));

        data = new byte[1024]; //clear buffer
        if(new String(data).equals("close"))
            isAlive = false;
    }

    /**
     * Closes socket and tries to stop thread
     */
    public void close() {
        socket.close();
        this.interrupt();
    }
}
