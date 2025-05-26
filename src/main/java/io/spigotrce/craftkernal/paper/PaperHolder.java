package io.spigotrce.craftkernal.paper;

import io.spigotrce.craftkernal.common.holder.plugin.PluginHolder;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * This class holds the Bukkit server instance, logger, and plugin instance.
 * It is used to provide access to these components throughout the plugin.
 */
public class PaperHolder extends PluginHolder<Server, Logger, JavaPlugin> {
    /**
     * Constructs a new PaperHolder with the specified proxy server, logger, and plugin.
     *
     * @param server The Bukkit server instance.
     * @param logger The logger instance for logging.
     * @param plugin The plugin instance associated with this holder.
     */
    public PaperHolder(Server server, Logger logger, JavaPlugin plugin) {
        super(server, logger, plugin);
    }
}
