package io.spigotrce.craftkernal.messaging;

import com.velocitypowered.api.proxy.Player;
import io.spigotrce.craftkernal.common.messaging.PacketRegistry;

import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class TestMain {
    public static void main(String[] args) {
        AtomicReference<byte[]> buf = new AtomicReference<>();
        PacketRegistry<Player> registry = new PacketRegistry<>((player, bytes) -> {
            System.out.println("Sending packet...");
            System.out.println("Raw bytes to be sent: " + new String(bytes, Charset.defaultCharset()));
            buf.set(bytes);
        });

        registry.registerPacket(TestPacket::new, packet -> {
            System.out.println("Message: " + packet.getMessage());
            System.out.println("Data: " + packet.getData().getMessage());
        });

        registry.registerPacket(TestSignedPacket::new, packet -> {
            System.out.println("Message: " + packet.getMessage());
            System.out.println("UUID: " + packet.getUuid());
        });

        registry.encodeAndSend(new TestPacket("Hellow", new TestData("World!")), null);
        registry.decodeAndApply(buf.get());
        registry.encodeAndSend(new TestSignedPacket(UUID.randomUUID(), "Hellow signed packet"), null);
        registry.decodeAndApply(buf.get());
    }
}
