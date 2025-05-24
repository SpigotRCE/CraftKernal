package io.spigotrce.craftkernal.bungee;

import io.spigotrce.craftkernal.common.holder.PluginHolder;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

/**
 * This class holds the BungeeCord server instance, logger, and plugin instance.
 * It is used to provide access to these components throughout the plugin.
 */
public class BungeeHolder extends PluginHolder<ProxyServer, Logger, Plugin> {
    /**
     * Constructs a new BungeeHolder with the specified proxy server, logger, and plugin.
     *
     * @param proxyServer The BungeeCord proxy server instance.
     * @param logger      The logger instance for logging.
     * @param plugin      The plugin instance associated with this holder.
     */
    public BungeeHolder(ProxyServer proxyServer, Logger logger, Plugin plugin) {
        super(proxyServer, logger, plugin);
    }
}
