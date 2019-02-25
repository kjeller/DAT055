package com.dat055.net.message;

public abstract class Protocol {
    // Server OP codes - used for TCP communication
    public static final char OP_JOIN = 0xFE;
    public static final char OP_LEAVE = 0xFF;
    public static final char OP_CHAR_SEL = 0xE0;

    // Player OP codes - used for UDPHandler communcation
    public static final char OP_PLAYER = 0x01;
    public static final char OP_HOOK = 0x02;
}
