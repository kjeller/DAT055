package com.dat055.net.message;

/**
 * A message for sending a leave message
 * Only used when writing to streams (TCP).
 * @author Karl Strålman
 * @version 2019-02-22
 */
public class LeaveMessage extends Message {
    public LeaveMessage() {
        super(Protocol.OP_LEAVE);
    }

    @Override
    public String toString() { return super.toString() + "Left";}
}
