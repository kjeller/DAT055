package com.dat055.net;

import com.dat055.net.threads.TCPHandler;
import com.dat055.net.threads.UDPHandler;

import java.io.*;
import java.net.*;

public class Client extends Thread{
    private final int PERIOD = 50;
    private Socket cs;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    TCPHandler tcpHandler;

    private DatagramSocket ds;
    UDPHandler udpHandler;

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
        initialize();
    }

    public Client(InetAddress hostname, int port) {
       this(port);
       this.hostname = hostname;
       initialize();
    }

    private void initialize() {
        try {
            cs = new Socket(hostname, port);
            out = new ObjectOutputStream(cs.getOutputStream());
            in = new ObjectInputStream(cs.getInputStream());
            ds = new DatagramSocket();
            tcpHandler = new TCPHandler(this); // Create a tcphandler to send messages
            udpHandler = new UDPHandler(this);
            System.out.println("[Client] Sockets created");
        } catch (IOException ignored) {}
    }

    private int t = 0;
    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(PERIOD);
                System.out.println("[Client] " + t++);
            } catch (InterruptedException ignored) {}
            if(cs != null) {
                if(cs.isConnected()) {
                    udpHandler.send(ds, data, hostname, port);
                }
            }
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
    public byte[] getData() { return data; }
    public DatagramSocket getDatagramSocket() { return ds; }
    public ObjectOutputStream getOut() { return out; }
}
