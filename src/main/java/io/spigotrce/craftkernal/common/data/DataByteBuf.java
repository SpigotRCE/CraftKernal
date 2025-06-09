package io.spigotrce.craftkernal.common.data;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;

public class DataByteBuf extends ByteBuf {
    private final ByteBuf parent;

    public DataByteBuf(ByteBuf parent) {
        this.parent = parent;
    }

    public DataByteBuf() {
        this(Unpooled.buffer());
    }

    public DataByteBuf(byte[] bytes) {
        this(Unpooled.wrappedBuffer(bytes));
    }

    public DataByteBuf writeString(String value) {
        this.writeString(value, Charset.defaultCharset());
        return this;
    }

    public String readString() {
        return this.readString(Charset.defaultCharset());
    }

    public DataByteBuf writeString(String value, Charset charset) {
        byte[] bytes = value.getBytes(charset);
        this.writeInt(bytes.length);
        this.writeBytes(bytes);
        return this;
    }

    public String readString(Charset charset) {
        int length = readInt();
        byte[] bytes = new byte[length];
        this.readBytes(bytes);
        return new String(bytes, charset);
    }

    public DataByteBuf writeUUID(UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
        return this;
    }

    public UUID readUUID() {
        return new UUID(this.readLong(), this.readLong());
    }

    public DataByteBuf writeEnumConstant(Enum<?> instance) {
        this.writeInt(instance.ordinal());
        return this;
    }

    public <T extends Enum<T>> T readEnumConstant(Class<T> enumClass) {
        return enumClass.getEnumConstants()[this.readInt()];
    }

    public DataByteBuf writeByteArray(byte[] array) {
        this.writeInt(array.length);
        this.writeBytes(array);
        return this;
    }

    public byte[] readByteArray() {
        int length = readInt();
        byte[] array = new byte[length];
        this.readBytes(array);
        return array;
    }

    public <T> DataByteBuf writeList(List<T> list, BiConsumer<DataByteBuf, T> writer) {
        this.writeInt(list.size());
        for (T item : list) writer.accept(this, item);
        return this;
    }

    public <T> List<T> readList(IntFunction<T[]> generator, Function<DataByteBuf, T> reader) {
        int size = this.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) list.add(reader.apply(this));
        return list;
    }

    public DataByteBuf writeData(Data data) {
        data.encode(this);
        return this;
    }

    public <T extends Data> T readData(Class<T> dataClass) {
        try {
            T instance = dataClass.getDeclaredConstructor().newInstance();
            instance.decode(this);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read data of type: " + dataClass.getName(), e);
        }
    }

    public int capacity() {
        return this.parent.capacity();
    }

    public DataByteBuf capacity(int i) {
        this.parent.capacity(i);
        return this;
    }

    public int maxCapacity() {
        return this.parent.maxCapacity();
    }

    public ByteBufAllocator alloc() {
        return this.parent.alloc();
    }

    @Deprecated
    public ByteOrder order() {
        return this.parent.order();
    }

    @Deprecated
    public ByteBuf order(ByteOrder byteOrder) {
        return this.parent.order(byteOrder);
    }

    public ByteBuf unwrap() {
        return this.parent;
    }

    public boolean isDirect() {
        return this.parent.isDirect();
    }

    public boolean isReadOnly() {
        return this.parent.isReadOnly();
    }

    public ByteBuf asReadOnly() {
        return this.parent.asReadOnly();
    }

    public int readerIndex() {
        return this.parent.readerIndex();
    }

    public DataByteBuf readerIndex(int i) {
        this.parent.readerIndex(i);
        return this;
    }

    public int writerIndex() {
        return this.parent.writerIndex();
    }

    public DataByteBuf writerIndex(int i) {
        this.parent.writerIndex(i);
        return this;
    }

    public DataByteBuf setIndex(int i, int j) {
        this.parent.setIndex(i, j);
        return this;
    }

    public int readableBytes() {
        return this.parent.readableBytes();
    }

    public int writableBytes() {
        return this.parent.writableBytes();
    }

    public int maxWritableBytes() {
        return this.parent.maxWritableBytes();
    }

    public boolean isReadable() {
        return this.parent.isReadable();
    }

    public boolean isReadable(int size) {
        return this.parent.isReadable(size);
    }

    public boolean isWritable() {
        return this.parent.isWritable();
    }

    public boolean isWritable(int size) {
        return this.parent.isWritable(size);
    }

    public DataByteBuf clear() {
        this.parent.clear();
        return this;
    }

    public DataByteBuf markReaderIndex() {
        this.parent.markReaderIndex();
        return this;
    }

    public DataByteBuf resetReaderIndex() {
        this.parent.resetReaderIndex();
        return this;
    }

    public DataByteBuf markWriterIndex() {
        this.parent.markWriterIndex();
        return this;
    }

    public DataByteBuf resetWriterIndex() {
        this.parent.resetWriterIndex();
        return this;
    }

    public DataByteBuf discardReadBytes() {
        this.parent.discardReadBytes();
        return this;
    }

    public DataByteBuf discardSomeReadBytes() {
        this.parent.discardSomeReadBytes();
        return this;
    }

    public DataByteBuf ensureWritable(int i) {
        this.parent.ensureWritable(i);
        return this;
    }

    public int ensureWritable(int minBytes, boolean force) {
        return this.parent.ensureWritable(minBytes, force);
    }

    public boolean getBoolean(int index) {
        return this.parent.getBoolean(index);
    }

    public byte getByte(int index) {
        return this.parent.getByte(index);
    }

    public short getUnsignedByte(int index) {
        return this.parent.getUnsignedByte(index);
    }

    public short getShort(int index) {
        return this.parent.getShort(index);
    }

    public short getShortLE(int index) {
        return this.parent.getShortLE(index);
    }

    public int getUnsignedShort(int index) {
        return this.parent.getUnsignedShort(index);
    }

    public int getUnsignedShortLE(int index) {
        return this.parent.getUnsignedShortLE(index);
    }

    public int getMedium(int index) {
        return this.parent.getMedium(index);
    }

    public int getMediumLE(int index) {
        return this.parent.getMediumLE(index);
    }

    public int getUnsignedMedium(int index) {
        return this.parent.getUnsignedMedium(index);
    }

    public int getUnsignedMediumLE(int index) {
        return this.parent.getUnsignedMediumLE(index);
    }

    public int getInt(int index) {
        return this.parent.getInt(index);
    }

    public int getIntLE(int index) {
        return this.parent.getIntLE(index);
    }

    public long getUnsignedInt(int index) {
        return this.parent.getUnsignedInt(index);
    }

    public long getUnsignedIntLE(int index) {
        return this.parent.getUnsignedIntLE(index);
    }

    public long getLong(int index) {
        return this.parent.getLong(index);
    }

    public long getLongLE(int index) {
        return this.parent.getLongLE(index);
    }

    public char getChar(int index) {
        return this.parent.getChar(index);
    }

    public float getFloat(int index) {
        return this.parent.getFloat(index);
    }

    public double getDouble(int index) {
        return this.parent.getDouble(index);
    }

    public DataByteBuf getBytes(int i, ByteBuf byteBuf) {
        this.parent.getBytes(i, byteBuf);
        return this;
    }

    public DataByteBuf getBytes(int i, ByteBuf byteBuf, int j) {
        this.parent.getBytes(i, byteBuf, j);
        return this;
    }

    public DataByteBuf getBytes(int i, ByteBuf byteBuf, int j, int k) {
        this.parent.getBytes(i, byteBuf, j, k);
        return this;
    }

    public DataByteBuf getBytes(int i, byte[] bs) {
        this.parent.getBytes(i, bs);
        return this;
    }

    public DataByteBuf getBytes(int i, byte[] bs, int j, int k) {
        this.parent.getBytes(i, bs, j, k);
        return this;
    }

    public DataByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        this.parent.getBytes(i, byteBuffer);
        return this;
    }

    public DataByteBuf getBytes(int i, OutputStream outputStream, int j) throws IOException {
        this.parent.getBytes(i, outputStream, j);
        return this;
    }

    public int getBytes(int index, GatheringByteChannel channel, int length) throws IOException {
        return this.parent.getBytes(index, channel, length);
    }

    public int getBytes(int index, FileChannel channel, long pos, int length) throws IOException {
        return this.parent.getBytes(index, channel, pos, length);
    }

    public CharSequence getCharSequence(int index, int length, Charset charset) {
        return this.parent.getCharSequence(index, length, charset);
    }

    public DataByteBuf setBoolean(int i, boolean bl) {
        this.parent.setBoolean(i, bl);
        return this;
    }

    public DataByteBuf setByte(int i, int j) {
        this.parent.setByte(i, j);
        return this;
    }

    public DataByteBuf setShort(int i, int j) {
        this.parent.setShort(i, j);
        return this;
    }

    public DataByteBuf setShortLE(int i, int j) {
        this.parent.setShortLE(i, j);
        return this;
    }

    public DataByteBuf setMedium(int i, int j) {
        this.parent.setMedium(i, j);
        return this;
    }

    public DataByteBuf setMediumLE(int i, int j) {
        this.parent.setMediumLE(i, j);
        return this;
    }

    public DataByteBuf setInt(int i, int j) {
        this.parent.setInt(i, j);
        return this;
    }

    public DataByteBuf setIntLE(int i, int j) {
        this.parent.setIntLE(i, j);
        return this;
    }

    public DataByteBuf setLong(int i, long l) {
        this.parent.setLong(i, l);
        return this;
    }

    public DataByteBuf setLongLE(int i, long l) {
        this.parent.setLongLE(i, l);
        return this;
    }

    public DataByteBuf setChar(int i, int j) {
        this.parent.setChar(i, j);
        return this;
    }

    public DataByteBuf setFloat(int i, float f) {
        this.parent.setFloat(i, f);
        return this;
    }

    public DataByteBuf setDouble(int i, double d) {
        this.parent.setDouble(i, d);
        return this;
    }

    public DataByteBuf setBytes(int i, ByteBuf byteBuf) {
        this.parent.setBytes(i, byteBuf);
        return this;
    }

    public DataByteBuf setBytes(int i, ByteBuf byteBuf, int j) {
        this.parent.setBytes(i, byteBuf, j);
        return this;
    }

    public DataByteBuf setBytes(int i, ByteBuf byteBuf, int j, int k) {
        this.parent.setBytes(i, byteBuf, j, k);
        return this;
    }

    public DataByteBuf setBytes(int i, byte[] bs) {
        this.parent.setBytes(i, bs);
        return this;
    }

    public DataByteBuf setBytes(int i, byte[] bs, int j, int k) {
        this.parent.setBytes(i, bs, j, k);
        return this;
    }

    public DataByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        this.parent.setBytes(i, byteBuffer);
        return this;
    }

    public int setBytes(int index, InputStream stream, int length) throws IOException {
        return this.parent.setBytes(index, stream, length);
    }

    public int setBytes(int index, ScatteringByteChannel channel, int length) throws IOException {
        return this.parent.setBytes(index, channel, length);
    }

    public int setBytes(int index, FileChannel channel, long pos, int length) throws IOException {
        return this.parent.setBytes(index, channel, pos, length);
    }

    public DataByteBuf setZero(int i, int j) {
        this.parent.setZero(i, j);
        return this;
    }

    public int setCharSequence(int index, CharSequence sequence, Charset charset) {
        return this.parent.setCharSequence(index, sequence, charset);
    }

    public boolean readBoolean() {
        return this.parent.readBoolean();
    }

    public byte readByte() {
        return this.parent.readByte();
    }

    public short readUnsignedByte() {
        return this.parent.readUnsignedByte();
    }

    public short readShort() {
        return this.parent.readShort();
    }

    public short readShortLE() {
        return this.parent.readShortLE();
    }

    public int readUnsignedShort() {
        return this.parent.readUnsignedShort();
    }

    public int readUnsignedShortLE() {
        return this.parent.readUnsignedShortLE();
    }

    public int readMedium() {
        return this.parent.readMedium();
    }

    public int readMediumLE() {
        return this.parent.readMediumLE();
    }

    public int readUnsignedMedium() {
        return this.parent.readUnsignedMedium();
    }

    public int readUnsignedMediumLE() {
        return this.parent.readUnsignedMediumLE();
    }

    public int readInt() {
        return this.parent.readInt();
    }

    public int readIntLE() {
        return this.parent.readIntLE();
    }

    public long readUnsignedInt() {
        return this.parent.readUnsignedInt();
    }

    public long readUnsignedIntLE() {
        return this.parent.readUnsignedIntLE();
    }

    public long readLong() {
        return this.parent.readLong();
    }

    public long readLongLE() {
        return this.parent.readLongLE();
    }

    public char readChar() {
        return this.parent.readChar();
    }

    public float readFloat() {
        return this.parent.readFloat();
    }

    public double readDouble() {
        return this.parent.readDouble();
    }

    public ByteBuf readBytes(int length) {
        return this.parent.readBytes(length);
    }

    public ByteBuf readSlice(int length) {
        return this.parent.readSlice(length);
    }

    public ByteBuf readRetainedSlice(int length) {
        return this.parent.readRetainedSlice(length);
    }

    public DataByteBuf readBytes(ByteBuf byteBuf) {
        this.parent.readBytes(byteBuf);
        return this;
    }

    public DataByteBuf readBytes(ByteBuf byteBuf, int i) {
        this.parent.readBytes(byteBuf, i);
        return this;
    }

    public DataByteBuf readBytes(ByteBuf byteBuf, int i, int j) {
        this.parent.readBytes(byteBuf, i, j);
        return this;
    }

    public DataByteBuf readBytes(byte[] bs) {
        this.parent.readBytes(bs);
        return this;
    }

    public DataByteBuf readBytes(byte[] bs, int i, int j) {
        this.parent.readBytes(bs, i, j);
        return this;
    }

    public DataByteBuf readBytes(ByteBuffer byteBuffer) {
        this.parent.readBytes(byteBuffer);
        return this;
    }

    public DataByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        this.parent.readBytes(outputStream, i);
        return this;
    }

    public int readBytes(GatheringByteChannel channel, int length) throws IOException {
        return this.parent.readBytes(channel, length);
    }

    public CharSequence readCharSequence(int length, Charset charset) {
        return this.parent.readCharSequence(length, charset);
    }

    public int readBytes(FileChannel channel, long pos, int length) throws IOException {
        return this.parent.readBytes(channel, pos, length);
    }

    public DataByteBuf skipBytes(int i) {
        this.parent.skipBytes(i);
        return this;
    }

    public DataByteBuf writeBoolean(boolean bl) {
        this.parent.writeBoolean(bl);
        return this;
    }

    public DataByteBuf writeByte(int i) {
        this.parent.writeByte(i);
        return this;
    }

    public DataByteBuf writeShort(int i) {
        this.parent.writeShort(i);
        return this;
    }

    public DataByteBuf writeShortLE(int i) {
        this.parent.writeShortLE(i);
        return this;
    }

    public DataByteBuf writeMedium(int i) {
        this.parent.writeMedium(i);
        return this;
    }

    public DataByteBuf writeMediumLE(int i) {
        this.parent.writeMediumLE(i);
        return this;
    }

    public DataByteBuf writeInt(int i) {
        this.parent.writeInt(i);
        return this;
    }

    public DataByteBuf writeIntLE(int i) {
        this.parent.writeIntLE(i);
        return this;
    }

    public DataByteBuf writeLong(long l) {
        this.parent.writeLong(l);
        return this;
    }

    public DataByteBuf writeLongLE(long l) {
        this.parent.writeLongLE(l);
        return this;
    }

    public DataByteBuf writeChar(int i) {
        this.parent.writeChar(i);
        return this;
    }

    public DataByteBuf writeFloat(float f) {
        this.parent.writeFloat(f);
        return this;
    }

    public DataByteBuf writeDouble(double d) {
        this.parent.writeDouble(d);
        return this;
    }

    public DataByteBuf writeBytes(ByteBuf byteBuf) {
        this.parent.writeBytes(byteBuf);
        return this;
    }

    public DataByteBuf writeBytes(ByteBuf byteBuf, int i) {
        this.parent.writeBytes(byteBuf, i);
        return this;
    }

    public DataByteBuf writeBytes(ByteBuf byteBuf, int i, int j) {
        this.parent.writeBytes(byteBuf, i, j);
        return this;
    }

    public DataByteBuf writeBytes(byte[] bs) {
        this.parent.writeBytes(bs);
        return this;
    }

    public DataByteBuf writeBytes(byte[] bs, int i, int j) {
        this.parent.writeBytes(bs, i, j);
        return this;
    }

    public DataByteBuf writeBytes(ByteBuffer byteBuffer) {
        this.parent.writeBytes(byteBuffer);
        return this;
    }

    public int writeBytes(InputStream stream, int length) throws IOException {
        return this.parent.writeBytes(stream, length);
    }

    public int writeBytes(ScatteringByteChannel channel, int length) throws IOException {
        return this.parent.writeBytes(channel, length);
    }

    public int writeBytes(FileChannel channel, long pos, int length) throws IOException {
        return this.parent.writeBytes(channel, pos, length);
    }

    public DataByteBuf writeZero(int i) {
        this.parent.writeZero(i);
        return this;
    }

    public int writeCharSequence(CharSequence sequence, Charset charset) {
        return this.parent.writeCharSequence(sequence, charset);
    }

    public int indexOf(int from, int to, byte value) {
        return this.parent.indexOf(from, to, value);
    }

    public int bytesBefore(byte value) {
        return this.parent.bytesBefore(value);
    }

    public int bytesBefore(int length, byte value) {
        return this.parent.bytesBefore(length, value);
    }

    public int bytesBefore(int index, int length, byte value) {
        return this.parent.bytesBefore(index, length, value);
    }

    public int forEachByte(ByteProcessor byteProcessor) {
        return this.parent.forEachByte(byteProcessor);
    }

    public int forEachByte(int index, int length, ByteProcessor byteProcessor) {
        return this.parent.forEachByte(index, length, byteProcessor);
    }

    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return this.parent.forEachByteDesc(byteProcessor);
    }

    public int forEachByteDesc(int index, int length, ByteProcessor byteProcessor) {
        return this.parent.forEachByteDesc(index, length, byteProcessor);
    }

    public ByteBuf copy() {
        return this.parent.copy();
    }

    public ByteBuf copy(int index, int length) {
        return this.parent.copy(index, length);
    }

    public ByteBuf slice() {
        return this.parent.slice();
    }

    public ByteBuf retainedSlice() {
        return this.parent.retainedSlice();
    }

    public ByteBuf slice(int index, int length) {
        return this.parent.slice(index, length);
    }

    public ByteBuf retainedSlice(int index, int length) {
        return this.parent.retainedSlice(index, length);
    }

    public ByteBuf duplicate() {
        return this.parent.duplicate();
    }

    public ByteBuf retainedDuplicate() {
        return this.parent.retainedDuplicate();
    }

    public int nioBufferCount() {
        return this.parent.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        return this.parent.nioBuffer();
    }

    public ByteBuffer nioBuffer(int index, int length) {
        return this.parent.nioBuffer(index, length);
    }

    public ByteBuffer internalNioBuffer(int index, int length) {
        return this.parent.internalNioBuffer(index, length);
    }

    public ByteBuffer[] nioBuffers() {
        return this.parent.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        return this.parent.nioBuffers(index, length);
    }

    public boolean hasArray() {
        return this.parent.hasArray();
    }

    public byte[] array() {
        return this.parent.array();
    }

    public int arrayOffset() {
        return this.parent.arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return this.parent.hasMemoryAddress();
    }

    public long memoryAddress() {
        return this.parent.memoryAddress();
    }

    public String toString(Charset charset) {
        return this.parent.toString(charset);
    }

    public String toString(int index, int length, Charset charset) {
        return this.parent.toString(index, length, charset);
    }

    public int hashCode() {
        return this.parent.hashCode();
    }

    public boolean equals(Object o) {
        return this.parent.equals(o);
    }

    public int compareTo(ByteBuf byteBuf) {
        return this.parent.compareTo(byteBuf);
    }

    public String toString() {
        return this.parent.toString();
    }

    public DataByteBuf retain(int i) {
        this.parent.retain(i);
        return this;
    }

    public DataByteBuf retain() {
        this.parent.retain();
        return this;
    }

    public DataByteBuf touch() {
        this.parent.touch();
        return this;
    }

    public DataByteBuf touch(Object object) {
        this.parent.touch(object);
        return this;
    }

    public int refCnt() {
        return this.parent.refCnt();
    }

    public boolean release() {
        return this.parent.release();
    }

    public boolean release(int decrement) {
        return this.parent.release(decrement);
    }
}
