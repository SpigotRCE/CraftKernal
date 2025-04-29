package io.spigotrce.craftkernal.messaging;

import com.velocitypowered.api.proxy.Player;
import io.spigotrce.craftkernal.common.messaging.PacketRegistry;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicReference;

public class TestMain {
    public static void main(String[] args) {
        AtomicReference<byte[]> buf = new AtomicReference<>();
        PacketRegistry<Player> registry = new PacketRegistry<>((player, bytes) -> {
            System.out.println("Sending packet...");
            System.out.println(new String(bytes, Charset.defaultCharset()));
            buf.set(bytes);
        });

        registry.registerPacket(TestPacket::new, packet -> {
            System.out.println("Message: " + packet.getMessage());
        });

        registry.encodeAndSend(new TestPacket("Hellow"), null);
        registry.decodeAndApply(buf.get());
    }
}
