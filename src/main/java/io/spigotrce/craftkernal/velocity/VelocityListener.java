package io.spigotrce.craftkernal.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

/**
 * This abstract class serves as a base for creating custom listeners in a Velocity plugin.
 * It automatically registers the listener with the proxy server.
 */
public abstract class VelocityListener {
    /**
     * The {@link VelocityHolder} holder.
     */
    private final VelocityHolder holder;

    /**
     * Constructs a new VelocityListener and registers it with the proxy server's event manager.
     *
     * @param holder The {@link VelocityHolder} holder.
     */
    public VelocityListener(VelocityHolder holder) {
        this.holder = holder;
        this.holder.getServer().getEventManager().register(this.holder.getPlugin(), this);
        this.holder.getLogger().info("Registered listener {}", this.holder.getClassName());
    }

    /**
     * Gets the {@link VelocityHolder} holder.
     *
     * @return The {@link VelocityHolder} holder.
     */
    public VelocityHolder getHolder() {
        return holder;
    }
}
