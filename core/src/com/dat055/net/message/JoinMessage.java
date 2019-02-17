package com.dat055.net.message;

public class JoinMessage extends Message {
    private String name;

    public JoinMessage(char op, String name) {
        super(op);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
