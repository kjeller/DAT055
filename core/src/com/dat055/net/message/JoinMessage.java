package com.dat055.net.message;

public class JoinMessage extends Message {
    private String name;
    private String map;

    public JoinMessage(String name, String map) {
        super(Protocol.OP_JOIN);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getMap() { return map; }

    @Override
    public String toString() { return super.toString() + String.format(", Name: %s, Map: %s", name, map); }
}
