package io.spigotrce.craftkernal.paper;

import org.bukkit.event.Listener;

/**
 * This abstract class serves as a base for all listeners in the plugin.
 * It provides common functionality for registering listeners with the Bukkit server.
 */
public abstract class PaperListener implements Listener {
    /*
     * @param holder The {@link PaperHolder} holder
     */
    private final PaperHolder holder;

    /**
     * Constructs a new Listener instance.
     *
     * @param holder The {@link PaperHolder} holder
     */
    public PaperListener(PaperHolder holder) {
        this.holder = holder;
        this.holder.getServer().getPluginManager().registerEvents(this, this.holder.getPlugin());
        this.holder.getLogger().info("Registered listener " + this.holder.getClassName());
    }

    /**
     * Gets the {@link PaperHolder} holder.
     *
     * @return The {@link PaperHolder} holder
     */
    public PaperHolder getHolder() {
        return holder;
    }
}
