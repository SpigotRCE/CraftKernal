package io.github.spigotrce.craftkernal.common.messaging;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Registry for packets using int IDs and the new ByteBuf-based packet system,
 * but with old initializer-style handling.
 */
public class PacketRegistry {
    private final Map<Integer, PacketEntry<?>> idToEntry = new HashMap<>();
    private final Map<Class<? extends Packet>, Integer> classToId = new HashMap<>();
    private final Consumer<PacketSender> sender;
    private int nextId = 0;

    public PacketRegistry(Consumer<PacketSender> sender) {
        this.sender = sender;
    }

    /**
     * Registers a new packet with an automatically assigned integer ID.
     *
     * @param supplier    supplier to create the packet instance
     * @param initializer consumer to handle the packet
     * @param <T>         packet type
     *
     * @return Returns the integer ID of the packet.
     */
    public <T extends Packet> int registerPacket(Supplier<T> supplier, Consumer<T> initializer) {
        int id = nextId++;
        PacketEntry<T> entry = new PacketEntry<>(id, supplier, packet -> initializer.accept(safeCast(packet)));
        idToEntry.put(id, entry);
        classToId.put(supplier.get().getClass(), id);
        return id;
    }

    /**
     * Gets the registered ID for a packet instance.
     *
     * @param packet the packet
     * @return the packet's ID
     * @throws IllegalArgumentException if the packet class is not registered
     */
    public int getIdFor(Packet packet) {
        Integer id = classToId.get(packet.getClass());
        if (id == null) {
            throw new IllegalArgumentException("Unregistered packet class: " + packet.getClass().getName());
        }
        return id;
    }

    /**
     * Decodes and applies a packet from raw byte data.
     *
     * @param data the raw byte array
     */
    public void decodeAndApply(byte[] data) {
        ByteBuf buffer = Unpooled.wrappedBuffer(data);
        int id = buffer.readInt();

        PacketEntry<?> entry = idToEntry.get(id);
        if (entry == null) {
            throw new IllegalArgumentException("Unknown packet ID: " + id);
        }

        Packet packet = entry.supplier.get();
        packet.decode(buffer);
        entry.initializer.accept(packet);
    }

    /**
     * Encodes and sends a packet over a connection.
     *
     * @param packet     the packet
     * @param connection the target connection
     */
    public void encodeAndSend(Packet packet, Object connection) {
        Integer id = classToId.get(packet.getClass());
        if (id == null) {
            throw new IllegalArgumentException("Unregistered packet class: " + packet.getClass().getName());
        }

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(id);
        packet.encode(buffer);

        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        sender.accept(new PacketSender(connection, bytes));
    }

    @SuppressWarnings("unchecked")
    private <T extends Packet> T safeCast(Packet packet) {
        return (T) packet;
    }

    private record PacketEntry<T extends Packet>(int id, Supplier<T> supplier, Consumer<Packet> initializer) {
    }

    public record PacketSender(Object connection, byte[] data) {
    }
}
