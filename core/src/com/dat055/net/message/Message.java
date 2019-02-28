package com.dat055.net.message;

import java.io.Serializable;

/**
 * A serializable class that will be serialized before
 * sent to a socket and then deserialized when read.
 * @author Karl Strålman
 * @version 2019-02-20
 */
public class Message implements Serializable {
    protected char op;

    public Message(char op) {
        this.op = op;
    }

    public char getOp() { return op; }
    public String toString() {
        return String.format("0x%02x", (int)op);
    }
}

