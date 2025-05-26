package io.spigotrce.craftkernal.common.messaging;

/**
 * Abstract class representing a packet.
 */
public abstract class Packet {
    public Packet() {
    }

    /**
     * Encodes the packet into the specified buffer.
     *
     * @param buffer The buffer to encode the packet into.
     */
    protected abstract void encode(PacketBuffer buffer);

    /**
     * Decodes the packet from the specified buffer.
     *
     * @param buffer The buffer to decode the packet from.
     */
    protected abstract void decode(PacketBuffer buffer);
}
