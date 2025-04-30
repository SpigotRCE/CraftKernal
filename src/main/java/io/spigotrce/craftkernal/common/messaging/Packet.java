package io.spigotrce.craftkernal.common.messaging;

/**
 * Abstract class representing a packet.
 */
public interface Packet {
    void encode(PacketBuffer buffer);

    void decode(PacketBuffer buffer);
}
