package com.dat055.net.threads;

import com.dat055.net.message.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * A thread responsible for sending messages through a socket
 */
public class Client extends Thread {
    private DatagramSocket socket;
    private InetAddress destAddr;
    private byte[] data;

    public Client(DatagramSocket socket, InetAddress destAddr) {
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
     * @param msg
     */
    public void send() {
        DatagramPacket packet = new DatagramPacket(data, data.length, destAddr, 1337);
        try {
            socket.send(packet);
        } catch (IOException e) { System.out.println(e); }
    }

    public void dataToBeSent(byte[] data) {
        this.data = data;
    }
}
