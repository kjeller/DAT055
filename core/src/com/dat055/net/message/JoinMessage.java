package com.dat055.net.message;

/**
 * A message for sending join requests.
 * Only used when writing to streams (TCP).
 * @author Karl Strålman
 * @version 2019-02-22
 */
public class JoinMessage extends Message {
    private String name;
    private String map;

    public JoinMessage(String name, String map) {
        super(Protocol.OP_JOIN);
        this.name = name;
        this.map = map;
    }

    public String getName() {
        return name;
    }
    public String getMap() { return map; }

    @Override
    public String toString() { return super.toString() + String.format(", Name: %s, Map: %s", name, map); }
}
