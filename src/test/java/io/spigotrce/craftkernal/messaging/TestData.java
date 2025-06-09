package io.spigotrce.craftkernal.messaging;

import io.spigotrce.craftkernal.common.data.Data;
import io.spigotrce.craftkernal.common.data.DataByteBuf;

public class TestData implements Data {
    private String message;

    public TestData() {
    }

    public TestData(String message) {
        this.message = message;
    }

    @Override
    public void encode(DataByteBuf buffer) {
        buffer.writeString(message);
    }

    @Override
    public void decode(DataByteBuf buffer) {
        message = buffer.readString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
