package io.spigotrce.craftkernal.messaging;

import io.spigotrce.craftkernal.common.messaging.Packet;
import io.spigotrce.craftkernal.common.messaging.PacketBuffer;

public class TestPacket implements Packet {
    private String message;

    public TestPacket() {
    }

    public TestPacket(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeString(message);
    }

    @Override
    public void decode(PacketBuffer buffer) {
        message = buffer.readString();
    }
}

