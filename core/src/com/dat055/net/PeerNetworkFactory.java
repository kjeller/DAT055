package com.dat055.net;

import com.dat055.net.threads.Server;
import com.dat055.net.threads.Client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public abstract class PeerNetworkFactory {

    public static PeerNetwork getPeerNetwork(int port, String addr) {
        return new PeerNetwork( client = getClient(addr), getServer(port));
    }

    public static PeerNetwork getPeerNetwork(int port) {
        return new PeerNetwork(getServer(port));
    }

    public static Client getClient(InetAddress addr) {
        Client client;
        try {
            client = new Client(new DatagramSocket(), addr);
        }
        catch (SocketException e) { return null; }
        return client;
    }

    public static Client getClient(String addr) {
        InetAddress destAddr;
        Client client;
        try {
            destAddr = InetAddress.getByName(addr);
            client = new Client(new DatagramSocket(), destAddr);
        }
        catch (UnknownHostException e) { return null; }
        catch (SocketException e) { return null; }
        return client;
    }

    public static Server getServer(int port) {
        Server server;

        try {
            server = new Server(new DatagramSocket(port));
        } catch (SocketException e) {
            return null;
        }
        return server;
    }
}
