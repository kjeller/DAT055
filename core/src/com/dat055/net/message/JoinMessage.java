package com.dat055.net.message;

public class JoinMessage extends Message {
    private String name;

    public JoinMessage(String name) {
        super(Protocol.OP_JOIN);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() { return super.toString() + ", Name: " + name; }
}
