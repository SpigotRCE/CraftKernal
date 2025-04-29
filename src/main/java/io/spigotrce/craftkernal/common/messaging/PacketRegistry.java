package io.spigotrce.craftkernal.common.messaging;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A registry for managing packets using integer IDs and a ByteBuf-based system.
 * This class provides functionality for registering, encoding, decoding, and sending packets.
 *
 * @param <T> The type of the connection object used for sending packets.
 */
public class PacketRegistry<T> {
    /**
     * Map of packet IDs to their corresponding packet entries.
     */
    private final Map<Integer, PacketEntry<?>> idToEntry = new HashMap<>();

    /**
     * Map of packet classes to their corresponding IDs.
     */
    private final Map<Class<? extends AbstractPacket>, Integer> classToId = new HashMap<>();

    /**
     * A sender responsible for sending packets to a connection.
     */
    private final BiPacketSender<T> sender;

    /**
     * Counter for assigning unique IDs to packets.
     */
    private int nextId = 0;

    /**
     * Constructs a new PacketRegistry with the specified sender.
     *
     * @param sender A functional interface responsible for sending packets.
     */
    public PacketRegistry(BiPacketSender<T> sender) {
        this.sender = sender;
    }

    /**
     * Registers a new packet with an automatically assigned integer ID.
     *
     * @param supplier    A supplier to create the packet instance.
     * @param initializer A consumer to handle the packet.
     * @param <P>         The type of the packet.
     * @return The integer ID assigned to the packet.
     */
    public <P extends AbstractPacket> int registerPacket(Supplier<P> supplier, Consumer<P> initializer) {
        int id = nextId++;
        PacketEntry<P> entry = new PacketEntry<>(id, supplier, packet -> initializer.accept(safeCast(packet)));
        idToEntry.put(id, entry);
        classToId.put(supplier.get().getClass(), id);
        return id;
    }

    /**
     * Gets the registered ID for a given packet instance.
     *
     * @param packet The packet instance.
     * @return The ID of the packet.
     * @throws IllegalArgumentException If the packet class is not registered.
     */
    public int getIdFor(AbstractPacket packet) {
        Integer id = classToId.get(packet.getClass());
        if (id == null) {
            throw new IllegalArgumentException("Unregistered packet class: " + packet.getClass().getName());
        }
        return id;
    }

    /**
     * Decodes and applies a packet from raw byte data.
     *
     * @param data The raw byte array containing the packet data.
     * @throws IllegalArgumentException If the packet ID is unknown.
     */
    public void decodeAndApply(byte[] data) {
        decodeAndApply(Unpooled.wrappedBuffer(data));
    }

    /**
     * Decodes and applies a packet from a ByteBuf.
     *
     * @param buffer The ByteBuf containing the packet data.
     * @throws IllegalArgumentException If the packet ID is unknown.
     */
    public void decodeAndApply(ByteBuf buffer) {
        int id = buffer.readInt();

        PacketEntry<?> entry = idToEntry.get(id);
        if (entry == null) {
            throw new IllegalArgumentException("Unknown packet ID: " + id);
        }

        AbstractPacket packet = entry.supplier.get();
        packet.decode(buffer);
        entry.initializer.accept(packet);
    }

    /**
     * Encodes and sends a packet over a connection.
     *
     * @param packet     The packet to encode and send.
     * @param connection The target connection to send the packet to.
     * @throws IllegalArgumentException If the packet class is not registered.
     */
    public void encodeAndSend(AbstractPacket packet, T connection) {
        Integer id = classToId.get(packet.getClass());
        if (id == null) {
            throw new IllegalArgumentException("Unregistered packet class: " + packet.getClass().getName());
        }

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(id);
        packet.encode(buffer);

        byte[] data = new byte[buffer.readableBytes()];
        buffer.getBytes(0, data);
        sender.send(connection, data);
    }

    /**
     * Safely casts an AbstractPacket to a specific packet type.
     *
     * @param packet The packet to cast.
     * @param <P>    The target packet type.
     * @return The casted packet.
     */
    @SuppressWarnings("unchecked")
    private <P extends AbstractPacket> P safeCast(AbstractPacket packet) {
        return (P) packet;
    }

    /**
     * Represents an entry in the packet registry.
     *
     * @param <P> The type of the packet.
     */
    private record PacketEntry<P extends AbstractPacket>(
            int id,
            Supplier<P> supplier,
            Consumer<AbstractPacket> initializer
    ) {
    }

    /**
     * Functional interface for sending packets to a connection.
     *
     * @param <T> The type of the connection object.
     */
    @FunctionalInterface
    public interface BiPacketSender<T> {
        /**
         * Sends a packet to the specified connection.
         *
         * @param connection The target connection.
         * @param data       The packet data to send.
         */
        void send(T connection, byte[] data);
    }
}
