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

    int t = 0;
    public Client(DatagramSocket socket, InetAddress destAddr, int port) {
        this.port = port;
        this.socket = socket;
        this.destAddr = destAddr;
        data = new byte[1024];
        System.out.println(this);
    }

    @Override
    public void run() {
        while(!interrupted()) {
            System.out.println("[Line: 27] Client running."+t++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { break; }
           send();
        }
    }

    /**
     * Sends message to socket
     */
    private void send() {
        if(data != null) {
            DatagramPacket packet = new DatagramPacket(data, data.length, destAddr, port);
            try {
                System.out.printf("==> Client sent packet to %s \n", destAddr.getHostAddress());
                socket.send(packet);
            } catch (IOException e) { System.out.println(e); }
        }
    }

    /**
     * Interrupts thread and closes socket
     */
    public void close() {
        System.out.println("!!Client thread and socket will be closed.!!");
        socket.close();
        this.interrupt();
    }

    public void dataToBeSent(byte[] data) {
        this.data = data;
    }

    public String toString() {
        return String.format("Client: [source] %s:%s, [dest]: %s:%d",
                socket.getLocalAddress(), socket.getLocalPort(), destAddr.toString(), port);
    }
}
