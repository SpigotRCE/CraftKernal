package io.spigotrce.craftkernal.common.data;

/**
 * Data interface represents a data that can be encoded and decoded.
 * It provides methods to encode the packet into a buffer and decode it from a buffer.
 */
public interface Data {
    /**
     * Encodes the packet into the specified buffer.
     *
     * @param buffer The buffer to encode the packet into.
     */
    void encode(DataBuffer buffer);

    /**
     * Decodes the packet from the specified buffer.
     *
     * @param buffer The buffer to decode the packet from.
     */
    void decode(DataBuffer buffer);
}
