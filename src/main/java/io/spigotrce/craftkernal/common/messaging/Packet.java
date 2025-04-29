package io.spigotrce.craftkernal.common.messaging;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Abstract class representing a packet.
 */
public interface Packet {
    void encode(PacketBuffer buffer);

    void decode(PacketBuffer buffer);
}
