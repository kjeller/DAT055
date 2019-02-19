package com.dat055.net;

import com.dat055.net.threads.Server;
import com.dat055.net.threads.Client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public abstract class PeerNetworkFactory {
    public static final int PORT = 1337;
    /**
     * Peer network with server and client
     * @param addr address used by client
     * @return peer network
     */
    public static PeerNetwork getPeerNetwork(String name, String addr) {
        return new PeerNetwork(name, getClient(addr), getServer());
    }

    /**
     * Peer network with only server
     * @return peer network
     */
    public static PeerNetwork getPeerNetwork(String name) {
        return new PeerNetwork(name, getServer());
    }

    /**
     * Returns a client with a socket designated to send packet
     * @param addr InetAddress
     * @return client
     */
    public static Client getClient(InetAddress addr) {
        Client client;
        try {
            client = new Client(new DatagramSocket(), addr, PORT);
        }
        catch (SocketException e) { return null; }
        return client;
    }

    /**
     * Returns a client with a socket designated to send packet
     * @param addr string converted to InetAddress
     * @return client
     */
    public static Client getClient(String addr) {
        InetAddress destAddr;
        Client client;
        try {
            destAddr = InetAddress.getByName(addr);
            client = new Client(new DatagramSocket(), destAddr, PORT);
        }
        catch (UnknownHostException e) { return null; }
        catch (SocketException e) { return null; }
        return client;
    }

    /**
     * Returns a server for designated port
     * @return
     */
    public static Server getServer() {
        Server server;

        try {
            server = new Server(new DatagramSocket(PORT));
        } catch (SocketException e) {
            return null;
        }
        return server;
    }
}
