package io.spigotrce.craftkernal.messaging;

import io.spigotrce.craftkernal.common.data.Data;
import io.spigotrce.craftkernal.common.data.DataBuffer;

public class TestData implements Data {
    private String message;

    public TestData() {
    }

    public TestData(String message) {
        this.message = message;
    }

    @Override
    public void encode(DataBuffer buffer) {
        buffer.writeString(message);
    }

    @Override
    public void decode(DataBuffer buffer) {
        message = buffer.readString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
