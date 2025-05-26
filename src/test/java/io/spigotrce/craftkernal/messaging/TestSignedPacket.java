package io.spigotrce.craftkernal.messaging;

import io.spigotrce.craftkernal.common.data.DataBuffer;
import io.spigotrce.craftkernal.common.messaging.SignedPacket;

import java.util.UUID;

public class TestSignedPacket extends SignedPacket {
    private String message;

    public TestSignedPacket(UUID playerUuid, String message) {
        super(playerUuid);
        this.message = message;
    }

    public TestSignedPacket() {
        super(UUID.randomUUID());
    }

    @Override
    public void encode(DataBuffer buffer) {
        super.encode(buffer);
        buffer.writeString(message);
    }

    @Override
    public void decode(DataBuffer buffer) {
        super.decode(buffer);
        this.message = buffer.readString();
    }

    public String getMessage() {
        return message;
    }
}
