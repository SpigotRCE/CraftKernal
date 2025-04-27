package io.github.spigotrce.craftkernal.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

/**
 * This class holds the Velocity server instance, logger, and plugin instance.
 * It is used to provide access to these components throughout the plugin.
 */
public abstract class VelocityHolder {
    private final ProxyServer proxyServer;
    private final Logger logger;
    private final Object plugin;

    public VelocityHolder(ProxyServer proxyServer, Logger logger, Object plugin) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.plugin = plugin;
    }

    public ProxyServer getProxyServer() {
        return proxyServer;
    }

    public Logger getLogger() {
        return logger;
    }

    public Object getPlugin() {
        return plugin;
    }
}
