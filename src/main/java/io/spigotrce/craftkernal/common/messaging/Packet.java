package io.spigotrce.craftkernal.common.messaging;

/**
 * Abstract class representing a packet.
 */
public abstract class Packet {
    public Packet() {
    }

    protected abstract void encode(PacketBuffer buffer);

    protected abstract void decode(PacketBuffer buffer);
}
