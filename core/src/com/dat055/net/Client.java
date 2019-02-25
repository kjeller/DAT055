package com.dat055.net;

import com.dat055.net.message.Message;

import java.io.*;
import java.net.*;

public class Client extends Thread {
    private final int PERIOD = 1000;
    private Socket cs;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private DatagramSocket ds;

    private int port;
    private InetAddress hostname;
    byte[] data;

    private Client(int port) {
        this.port = port;
    }

    public Client(String hostname, int port) {
        this(port);
        try {
            this.hostname = InetAddress.getByName(hostname);
        } catch (UnknownHostException ignored) {}
        start();
    }

    public Client(InetAddress hostname, int port) {
       this(port);
       this.hostname = hostname;
       start();
    }

    private void initialize() {
        try {
            cs = new Socket(hostname, port);
            out = new ObjectOutputStream(cs.getOutputStream());
            in = new ObjectInputStream(cs.getInputStream());
            ds = new DatagramSocket();
        } catch (IOException ignored) {}
    }

    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(PERIOD);
            } catch (InterruptedException ignored) {}
            if(cs == null) {
                initialize();
            } else {
                if(cs.isConnected()) {
                    receiveTCP();
                    sendUDP();
                }
            }
        }
    }

    /**
     * Writes to outputstream to connected client
     * @param msg
     */
    private void sendTCP(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException ignored) { }
    }

    /**
     * Creates new thread that awaits tcp response.
     */
    private void receiveTCP() {
        Thread t = new Thread(this::sendUDP);
        t.start();
        t.interrupt();
    }

    /**
     * Write message to output stream - will be sent to clients input stream
     * @param msg that will be sent
     */
    public void writeMessage(Message msg) {
        try {
            out.writeObject(msg);
            System.out.printf("[Client] {%s} sent to server. \n", msg);
        } catch (IOException ignored) {}
    }

    /**
     * Sends packet to addr
     */
    private void sendUDP() {
        if(data != null) {
            DatagramPacket packet = new DatagramPacket(data, data.length, hostname, port);
            try {
                System.out.printf("==> Client sent UDP packet to %s:%d \n", hostname.getHostAddress(), port);
                ds.send(packet);
            } catch (IOException e) { System.out.println(e); }
        }
    }

    /**
     * Sets data that will be carried in a datagram packet
     * @param data
     */
    public void setPacketData(byte[] data) {
        this.data = data;
    }

    public boolean isConnected() { return cs.isConnected(); }
    public String getAddress() { return cs.getInetAddress().getHostAddress(); }
}
