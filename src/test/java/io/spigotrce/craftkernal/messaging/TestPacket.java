package io.spigotrce.craftkernal.messaging;

import io.spigotrce.craftkernal.common.messaging.Packet;
import io.spigotrce.craftkernal.common.data.DataByteBuf;

public class TestPacket implements Packet {
    private String message;
    private TestData data;

    public TestPacket() {
        super();
    }

    public TestPacket(String message, TestData data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TestData getData() {
        return data;
    }

    public void setData(TestData data) {
        this.data = data;
    }

    @Override
    public void encode(DataByteBuf buffer) {
        buffer.writeString(message);
        buffer.writeData(data);
    }

    @Override
    public void decode(DataByteBuf buffer) {
        message = buffer.readString();
        data = buffer.readData(TestData.class);
    }
}

