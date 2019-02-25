package com.dat055.net.threads;

import com.dat055.net.Client;
import com.dat055.net.Server;
import com.dat055.net.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TCPHandler extends Thread {

    private Server server;
    private Client client;

    public TCPHandler(Server server) {
        this.server = server;
    }

    public TCPHandler(Client client) {
        this.client = client;
    }

    public void run() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        receive();
    }

    /**
     * Creates new thread that awaits tcp response.
     */
    private void receive() {
        server.handleServerResponses(readMessage());
    }


    /**
     * Reads message from stream - from client
     * @return message from stream
     */
    public Message readMessage() {
        try {
            ObjectInputStream in = server.getIn();
            if(in != null)
                return (Message)in.readObject();
        } catch (Exception e) { System.out.println(e); }
        return null;
    }


    /**
     * Write message to output stream - to server
     * @param msg that will be sent
     */
    public boolean writeClientMessage(Message msg) {
        if(client.isConnected()) {
            ObjectOutputStream out = client.getOut();
            try {
                out.writeObject(msg);
                System.out.printf("[Client] {%s} sent to server. \n", msg);
                //out.reset();
                return true;
            } catch (IOException ignored) {}
        }
        return false;
    }

    /**
     * Write message to output stream - to server
     * @param msg that will be sent
     */
    public boolean writeServerMessage(Message msg) {
        if(server.isConnected()) {
            ObjectOutputStream out = server.getOut();
            try {
                out.writeObject(msg);
                System.out.printf("[Client] {%s} sent to server. \n", msg);
                //out.reset();
                return true;
            } catch (IOException ignored) {}
        }
        return false;
    }

    public void setClient(Client client) { this.client = client; }
}
