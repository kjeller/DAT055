package com.dat055.net.threads;

import com.dat055.net.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * A thread responsible for sending messages through a socket
 */
public class Sender extends Thread {
    private DatagramSocket socket;
    private InetAddress destAddr;
    private Message msg;

    public Sender(DatagramSocket socket, InetAddress destAddr) {
        this.socket = socket;
        this.destAddr = destAddr;
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
           send();
        }
    }

    /**
     * Sends message to socket
     * @param msg
     */
    public void send() {
        byte[] data;
        DatagramPacket packet = new DatagramPacket(data, data.length, destAddr, socket.getPort());
        try {
            socket.send(packet);
        } catch (IOException e) { System.out.println(e); }
    }

    public void setMessageToSend(Message msg) {
        this.msg = msg;
    }

}
