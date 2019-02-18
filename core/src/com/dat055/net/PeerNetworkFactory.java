package com.dat055.net;

import com.dat055.net.threads.Server;
import com.dat055.net.threads.Client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class PeerNetworkFactory {

    public PeerNetwork getPeerNetwork(int port, String Addr) {
        InetAddress destAddr;
        Client client;
        Server server;

        // Try if
        try {
            destAddr = InetAddress.getByName(Addr);
        }
        catch (UnknownHostException e) {
            return null;
        }

        try {
            server = new Server(new DatagramSocket());
            client = new Client(new DatagramSocket(), destAddr);
        } catch (SocketException e) {
            e.printStackTrace();
        }


        try {
            sender = new Client(new DatagramSocket(), this.destAddr);
            receiver = new Server(new DatagramSocket(port));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        sendJoinRequest("Kjelle");  // Tells sender to send join requests
        //TODO: Set name through menu
        start();
        try {
            sender.start();     // Starts sending current message
            receiver.start();
        } catch (Exception e) {
            throw new IllegalArgumentException("asdasd");
        }

        PeerNetwork peerNetwork = new PeerNetwork(port, destAddr);
    }
}
