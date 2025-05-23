package io.spigotrce.craftkernal.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import io.spigotrce.craftkernal.common.holder.PluginHolder;
import org.slf4j.Logger;

/**
 * This class holds the Velocity server instance, logger, and plugin instance.
 * It is used to provide access to these components throughout the plugin.
 */
public abstract class VelocityHolder extends PluginHolder<ProxyServer, Logger, Object> {
    /**
     * Constructs a new VelocityHolder with the specified proxy server, logger, and plugin.
     *
     * @param proxyServer The Velocity proxy server instance.
     * @param logger      The logger instance for logging.
     * @param plugin      The plugin instance associated with this holder.
     */
    public VelocityHolder(ProxyServer proxyServer, Logger logger, Object plugin) {
        super(proxyServer, logger, plugin);
    }
}
