package io.spigotrce.craftkernal.common.messaging;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Abstract class representing a packet.
 */
public abstract class Packet {
    public abstract void encode(ByteBuf buffer);

    public abstract void decode(ByteBuf buffer);

    public static void writeString(ByteBuf buffer, String value) {
        byte[] bytes = value.getBytes(Charset.defaultCharset());
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
    }

    public static String readString(ByteBuf buffer) {
        int length = buffer.readInt();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        return new String(bytes, Charset.defaultCharset());
    }

    public static void writeBoolean(ByteBuf buffer, boolean value) {
        buffer.writeByte(value ? 1 : 0);
    }

    public static boolean readBoolean(ByteBuf buffer) {
        return buffer.readByte() == 1;
    }

    public static void writeLong(ByteBuf buffer, long value) {
        buffer.writeLong(value);
    }

    public static long readLong(ByteBuf buffer) {
        return buffer.readLong();
    }

    public static void writeInt(ByteBuf buffer, int value) {
        buffer.writeInt(value);
    }

    public static int readInt(ByteBuf buffer) {
        return buffer.readInt();
    }

    public static void writeShort(ByteBuf buffer, short value) {
        buffer.writeShort(value);
    }

    public static short readShort(ByteBuf buffer) {
        return buffer.readShort();
    }

    public static void writeByte(ByteBuf buffer, byte value) {
        buffer.writeByte(value);
    }

    public static byte readByte(ByteBuf buffer) {
        return buffer.readByte();
    }

    public static void writeDouble(ByteBuf buffer, double value) {
        buffer.writeDouble(value);
    }

    public static double readDouble(ByteBuf buffer) {
        return buffer.readDouble();
    }

    public static void writeFloat(ByteBuf buffer, float value) {
        buffer.writeFloat(value);
    }

    public static float readFloat(ByteBuf buffer) {
        return buffer.readFloat();
    }

    public static void writeUUID(ByteBuf buffer, UUID uuid) {
        buffer.writeLong(uuid.getMostSignificantBits());
        buffer.writeLong(uuid.getLeastSignificantBits());
    }

    public static UUID readUUID(ByteBuf buffer) {
        return new UUID(buffer.readLong(), buffer.readLong());
    }
}
