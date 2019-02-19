package com.dat055.net.threads;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * A thread responsible for receiving messages through a socket
 */
public class Server extends Thread {
    private DatagramSocket socket;
    private DatagramPacket current; // will be used to determine where to packet came from
    private byte[] data;

    public Server(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) { break; }
            receive();
        }
    }

    /**
     * Receive a packet from socket
     */
    private void receive() {
        data = new byte[1024];
        current = new DatagramPacket(data, data.length);

        try {
            socket.receive(current);
        } catch (IOException ignored) {}
    }

    /**
     * Interrupts thread and closes socket
     */
    public void close() {
        socket.close();
        this.interrupt();
    }

    public byte[] getData() { return data; }
    public DatagramPacket getCurrent() { return current; }
}
