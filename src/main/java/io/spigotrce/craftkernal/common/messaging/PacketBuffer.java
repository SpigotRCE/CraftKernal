package io.spigotrce.craftkernal.common.messaging;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.util.UUID;

public record PacketBuffer(ByteBuf buffer) {
    public PacketBuffer() {
        this(Unpooled.buffer());
    }

    public PacketBuffer(byte[] bytes) {
        this(Unpooled.wrappedBuffer(bytes));
    }

    public PacketBuffer(ByteBuf buffer) {
        this.buffer = buffer;
    }

    public void writeString(String value) {
        byte[] bytes = value.getBytes(Charset.defaultCharset());
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
    }

    public String readString() {
        int length = buffer.readInt();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return new String(bytes, Charset.defaultCharset());
    }

    public void writeBoolean(boolean value) {
        buffer.writeByte(value ? 1 : 0);
    }

    public boolean readBoolean() {
        return buffer.readByte() == 1;
    }

    public void writeLong(long value) {
        buffer.writeLong(value);
    }

    public long readLong() {
        return buffer.readLong();
    }

    public void writeInt(int value) {
        buffer.writeInt(value);
    }

    public int readInt() {
        return buffer.readInt();
    }

    public void writeShort(short value) {
        buffer.writeShort(value);
    }

    public short readShort() {
        return buffer.readShort();
    }

    public void writeByte(byte value) {
        buffer.writeByte(value);
    }

    public byte readByte() {
        return buffer.readByte();
    }

    public void writeDouble(double value) {
        buffer.writeDouble(value);
    }

    public double readDouble() {
        return buffer.readDouble();
    }

    public void writeFloat(float value) {
        buffer.writeFloat(value);
    }

    public float readFloat() {
        return buffer.readFloat();
    }

    public void writeUUID(UUID uuid) {
        buffer.writeLong(uuid.getMostSignificantBits());
        buffer.writeLong(uuid.getLeastSignificantBits());
    }

    public UUID readUUID() {
        return new UUID(buffer.readLong(), buffer.readLong());
    }

    public void writeEnumConstant(Enum<?> instance) {
        this.writeInt(instance.ordinal());
    }

    public <T extends Enum<T>> T readEnumConstant(Class<T> enumClass) {
        return (T)enumClass.getEnumConstants()[this.readInt()];
    }
}
