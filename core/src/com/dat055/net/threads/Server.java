package com.dat055.net.threads;

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
    int t = 0;

    public Server(DatagramSocket socket) {
        this.socket = socket;
        System.out.println(this);
    }

    @Override
    public void run() {
        while(!interrupted()) {
            System.out.println("[Line: 22] Server running." + t++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { break; }
            receive();
        }
    }

    /**
     * Receive a packet from socket
     */
    private void receive() {
        byte[] data = new byte[1024];
        current = new DatagramPacket(data, data.length);
        try {
            socket.receive(current);
            this.data = data;
            System.out.printf("--Received package from %s!\n", current.getAddress());
        } catch (IOException ignored) {}
    }

    /**
     * Interrupts thread and closes socket
     */
    public void close() {
        System.out.println("Server thread and socket will be closed.");
        socket.close();
        this.interrupt();
    }

    public byte[] getData() { return data; }
    public DatagramPacket getCurrent() { return current; }

    public String toString() {
        return String.format("Server: %s:%s", socket.getLocalAddress().getHostAddress(), socket.getLocalPort());
    }
}
