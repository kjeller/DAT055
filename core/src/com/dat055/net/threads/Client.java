package com.dat055.net.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * A thread responsible for sending messages through a socket
 */
public class Client extends Thread {
    private DatagramSocket socket;  // Socket that will be used to send packets.
    private InetAddress destAddr;   // IP address client will send packets.
    private int port;               // port used create packets.
    private byte[] data;

    public Client(DatagramSocket socket, InetAddress destAddr, int port) {
        this.port = port;
        this.socket = socket;
        this.destAddr = destAddr;
        data = new byte[1024];
    }

    @Override
    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
           send();
        }
    }

    /**
     * Sends message to socket
     */
    public void send() {
        DatagramPacket packet = new DatagramPacket(data, data.length, destAddr, port);
        try {
            socket.send(packet);
        } catch (IOException e) { System.out.println(e); }
    }

    /**
     * Interrupts thread and closes socket
     */
    public void close() {
        socket.close();
        this.interrupt();
    }


    public void dataToBeSent(byte[] data) {
        this.data = data;
    }
}
