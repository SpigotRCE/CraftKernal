package io.spigotrce.craftkernal.common.data;

/**
 * Data interface represents a data that can be encoded and decoded.
 * It provides methods to encode the packet into a buffer and decode it from a buffer.
 */
public interface Data {
    /**
     * Encodes the data into the specified buffer.
     *
     * @param buffer The buffer to encode the data into.
     */
    void encode(DataByteBuf buffer);

    /**
     * Decodes the data from the specified buffer.
     *
     * @param buffer The buffer to decode the data from.
     */
    void decode(DataByteBuf buffer);
}
