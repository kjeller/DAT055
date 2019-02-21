package com.dat055.net.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * A thread responsible for sending messages through a ds
 */
public class Client extends Thread {
    private Socket cs;          // Socket that will be used to sendPacket w/ TCP
    private DatagramSocket ds;  // Socket that will be used to sendPacket w/ UDP

    private InetAddress addr;   // IP address client will sendPacket packets.
    private int port;               // port used create packets.
    private byte[] data;            // data that will be sent as DatagramPacket

    int t = 0;
    public Client(Socket cs, DatagramSocket ds, InetAddress addr, int port) {
        this.cs = cs;
        this.ds = ds;
        this.addr = addr;
        this.port = port;
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
            if(cs.isConnected())
                sendPacket();
        }
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
            cs.close();
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

    public String toString() {
        return String.format("Client: [source] %s:%s, [dest]: %s:%d",
                ds.getLocalAddress(), ds.getLocalPort(), addr.toString(), port);
    }

    public boolean isConnected() { return cs.isConnected(); }
}
