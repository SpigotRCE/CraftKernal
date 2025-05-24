package io.spigotrce.craftkernal.bungee;

import net.md_5.bungee.api.plugin.Listener;

/**
 * This abstract class serves as a base for creating custom listeners in a Bungee plugin.
 * It automatically registers the listener with the proxy server.
 */
public abstract class BungeeListener implements Listener {
    /**
     * The {@link BungeeHolder} holder.
     */
    private final BungeeHolder holder;

    /**
     * Constructs a new BungeeListener and registers it with the proxy server's event manager.
     *
     * @param holder The {@link BungeeHolder} holder.
     */
    public BungeeListener(BungeeHolder holder) {
        this.holder = holder;
        this.holder.getServer().getPluginManager().registerListener(this.holder.getPlugin(), this);
        this.holder.getLogger().info("Registered listener " + this.holder.getClassName());
    }

    /**
     * Gets the {@link BungeeHolder} holder.
     *
     * @return The {@link BungeeHolder} holder.
     */
    public BungeeHolder getHolder() {
        return holder;
    }
}
