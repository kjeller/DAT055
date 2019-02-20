package com.dat055.net.message;

import java.io.Serializable;

/**
 * Message used to update other peer.
 */
public abstract class Message implements Serializable {
    protected char op;

    public Message(char op) {
        this.op = op;
    }

    public char getOp() { return op; }
    public String toString() {
        return String.format("0x%02x", (int)op);
    }
}
