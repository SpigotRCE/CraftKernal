package io.spigotrce.craftkernal.common.messaging;

import io.spigotrce.craftkernal.common.data.DataByteBuf;

import java.util.UUID;

/**
 * Abstract class representing a packet that is signed by an uuid.
 */
public abstract class SignedPacket implements Packet {
    /**
     * The UUID used to sign this packet.
     */
    private UUID uuid;

    /**
     * Constructs a new SignedPacket with the specified UUID.
     *
     * @param playerUuid The UUID used to sign this packet.
     */
    public SignedPacket(UUID playerUuid) {
        this.uuid = playerUuid;
    }

    /**
     * Encodes the uuid into the specified buffer.
     */
    @Override
    public void encode(DataByteBuf buffer) {
        buffer.writeUUID(uuid);
    }

    /**
     * Decodes the uuid from the specified buffer.
     */
    @Override
    public void decode(DataByteBuf buffer) {
        this.uuid = buffer.readUUID();
    }

    /**
     * Gets the UUID used to sign this packet.
     *
     * @return The UUID used to sign this packet.
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the UUID used to sign this packet.
     *
     * @param uuid The UUID to set.
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
