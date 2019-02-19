package com.dat055.net.message;

public abstract class Protocol {
    // Server OP codes
    public static final char OP_JOIN = 0xFE;    // Join request
    public static final char OP_LEAVE = 0xFF;

    // Player OP codes
    public static final char OP_PLAYER = 0x01;
    public static final char OP_HOOK = 0x02;
}
