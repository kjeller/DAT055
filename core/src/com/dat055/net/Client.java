package com.dat055.net;

import com.dat055.net.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * A thread responsible for sending messages through a ds
 */
public class Client extends Thread {
    private InetAddress addr;   // IP address client will sendPacket packets.
    private Socket ps;          // Socket that will be used to sendPacket w/ TCP
    private ObjectInputStream in;     // will be connected to server's output stream
    private ObjectOutputStream out;    // will be connected to server's input stream


    private DatagramSocket ds;  // Socket that will be used to sendPacket w/ UDP
    private int port;               // port used deliver packets.
    private byte[] data;            // data that will be sent as DatagramPacket

    int t = 0;
    public Client(InetAddress addr, int port) {
        this.addr = addr;
        this.port = port;
        data = new byte[1024];
        try {
            ds = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        connect();
    }

    @Override
    public void run() {
        while(!interrupted()) {
            System.out.println("[Line: 27] Client running."+t++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { break; }
            if(ds.isConnected())
                sendPacket();
        }
    }

    public void connect() {
        ps = null;
        try {
            ps = new Socket(addr, port);
            System.out.println("Socket created!");
            out = new ObjectOutputStream(ps.getOutputStream());
            in = new ObjectInputStream(ps.getInputStream());

        } catch (Exception e) { System.out.println(e); }
    }

    /**
     * Write message to output stream - will be sent to clients input stream
     * @param msg that will be sent
     */
    public void writeMessage(Message msg) {
        try {
            out.writeObject(msg);
            System.out.printf("Msg: {%s} sent to other peer. \n", msg);
        } catch (IOException ignored) {}
    }

    /**
     * Reads message from stream - from client
     * @return message from stream
     */
    public Message readMessage() {
        try {
            return (Message)in.readObject();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }



    /**
     * Sends udp packet to address assigned in constructor
     */
    private void sendPacket() {
        if(data != null) {
            DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
            try {
                System.out.printf("==> Client sent UDP packet to %s \n", addr.getHostAddress());
                ds.send(packet);
            } catch (IOException e) { System.out.println(e); }
        }
    }

    /**
     * Interrupts thread and closes sockets
     */
    public void close() {
        System.out.println("!!Client thread and ds will be closed.!!");
        ds.close();
        try {
            ps.close();
        } catch (IOException e) { e.printStackTrace(); }
        this.interrupt();
    }

    /**
     * Sets data that will be carried in a datagram packet
     * @param data
     */
    public void setPacketData(byte[] data) {
        this.data = data;
    }

    public boolean isConnected() { return ps.isConnected(); }
    public String getAddress() { return ps.getInetAddress().getHostAddress(); }
}
