package com.dat055.net.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * A thread responsible for receiving messages through a socket
 */
public class Receiver extends Thread {
    DatagramSocket socket;
    DatagramPacket current; // current packet
    public Receiver(DatagramSocket socket) {
        this.socket = socket;
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
        }
    }

    /**
     * Receive a datagrampacket from socket
     * @throws IOException
     */
    private void receive() {
        byte[] data = new byte[1024];
        current = new DatagramPacket(data, data.length);

        try {
            socket.receive(current);
        } catch (IOException ignored) { return;}

        // TODO: Signal that data is avaliable somehow
    }

}
