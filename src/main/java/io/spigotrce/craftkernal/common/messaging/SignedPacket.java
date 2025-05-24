package io.spigotrce.craftkernal.common.messaging;

import java.util.UUID;

/**
 * Abstract class representing a packet that is signed by an uuid.
 */
public abstract class SignedPacket implements Packet {
    private UUID uuid;

    public SignedPacket(UUID playerUuid) {
        this.uuid = playerUuid;
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeUUID(uuid);
    }

    @Override
    public void decode(PacketBuffer buffer) {
        this.uuid = buffer.readUUID();
    }

    public UUID getUuid() {
        return uuid;
    }
}
