package com.dat055.net;

import com.dat055.net.threads.TCPHandler;
import com.dat055.net.threads.UDPHandler;

import java.io.*;
import java.net.*;

/**
 * A thread responsible for communicating with a server.
 * @author Karl Strålman
 * @version 2019-02-25
 */
public class Client extends Thread{
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
        data = new byte[1024];
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

    public void run() {
        while(!interrupted()) {
            if(cs != null) {
                if(cs.isConnected()) {
                    udpHandler.send(ds, data, hostname, port);
                } else {
                    interrupt();
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
    public ObjectOutputStream getOut() { return out; }
}
