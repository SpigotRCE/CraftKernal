package io.spigotrce.craftkernal.paper;

import io.spigotrce.craftkernal.common.holder.ClassHolder;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * This class holds the Bukkit server instance, logger, and plugin instance.
 * It is used to provide access to these components throughout the plugin.
 */
public abstract class PaperHolder extends ClassHolder {
    /**
     * The instance of the Velocity proxy server.
     */
    private final Server server;

    /**
     * The logger instance for logging messages.
     */
    private final Logger logger;


    /**
     * The plugin instance associated with this holder.
     */
    private final JavaPlugin plugin;

    /**
     * Constructs a new PaperHolder with the specified proxy server, logger, and plugin.
     *
     * @param server The Bukkit server instance.
     * @param logger The logger instance for logging.
     * @param plugin The plugin instance associated with this holder.
     */
    public PaperHolder(Server server, Logger logger, JavaPlugin plugin) {
        this.server = server;
        this.logger = logger;
        this.plugin = plugin;
    }

    /**
     * Gets the Bukkit server instance.
     *
     * @return The server instance.
     */
    public Server getServer() {
        return server;
    }

    /**
     * Gets the logger instance.
     *
     * @return The logger instance.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Gets the plugin instance associated with this holder.
     *
     * @return The plugin instance.
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }
}
