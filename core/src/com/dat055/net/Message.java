package com.dat055.net;

import com.badlogic.gdx.math.Vector2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Message {
    // Server OP codes
    public static final char OP_JOIN = 0xFE;
    public static final char OP_LEAVE = 0xFF;

    // Player OP codes
    public static final char OP_POSITION = 0x01;
    public static final char OP_HOOK = 0x02;
    public static final char OP_DIE = 0x03;

    private char op;
    private String name;
    private byte[] data;

    /**
     * For joining another peer
     * @param op
     * @param name
     */
    public Message(char op) {
        this.op = op;
        data = new byte[1024];
    }

    public void addtoByteArray(byte[] array) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(op);
        try {
            out.write(array);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public byte[] toByteArray() {
        return this.toByteArray();
    }
}
