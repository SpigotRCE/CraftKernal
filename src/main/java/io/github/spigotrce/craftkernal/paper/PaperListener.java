package io.github.spigotrce.craftkernal.paper;

import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * This abstract class serves as a base for all listeners in the plugin.
 * It provides common functionality for registering listeners with the Bukkit server.
 */
public abstract class PaperListener extends PaperHolder implements Listener {
    private final @NotNull String name;

    /**
     * Constructs a new Listener instance.
     *
     * @param plugin The main plugin instance.
     * @param server The Bukkit server instance.
     * @param logger The Logger instance for logging messages.
     */
    public PaperListener(JavaPlugin plugin, Server server, Logger logger) {
        super(server, logger, plugin);
        this.name = this.getClass().getSimpleName();

        getServer().getPluginManager().registerEvents(this, getPlugin());
        getLogger().info("Registered listener " + getName());
    }

    /**
     * Returns the simple name of the Listener class.
     *
     * @return The name of the Listener.
     */
    public @NotNull String getName() {
        return name;
    }
}
