package io.github.spigotrce.craftkernal.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

/**
 * This class holds the Velocity server instance, logger, and plugin instance.
 * It is used to provide access to these components throughout the plugin.
 */
public abstract class VelocityHolder {
    /**
     * The instance of the Velocity proxy server.
     */
    private final ProxyServer proxyServer;

    /**
     * The logger instance for logging messages.
     */
    private final Logger logger;

    /**
     * The plugin instance associated with this holder.
     */
    private final Object plugin;

    /**
     * Constructs a new VelocityHolder with the specified proxy server, logger, and plugin.
     *
     * @param proxyServer The Velocity proxy server instance.
     * @param logger      The logger instance for logging.
     * @param plugin      The plugin instance associated with this holder.
     */
    public VelocityHolder(ProxyServer proxyServer, Logger logger, Object plugin) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.plugin = plugin;
    }

    /**
     * Gets the Velocity proxy server instance.
     *
     * @return The proxy server instance.
     */
    public ProxyServer getProxyServer() {
        return proxyServer;
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
    public Object getPlugin() {
        return plugin;
    }
}
