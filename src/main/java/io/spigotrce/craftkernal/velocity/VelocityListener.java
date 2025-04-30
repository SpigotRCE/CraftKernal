package io.spigotrce.craftkernal.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

/**
 * This abstract class serves as a base for creating custom listeners in a Velocity plugin.
 * It automatically registers the listener with the proxy server.
 */
public class VelocityListener extends VelocityHolder {
    /**
     * Constructs a new VelocityListener and registers it with the proxy server's event manager.
     *
     * @param proxyServer The Velocity proxy server instance.
     * @param logger      The logger instance for logging.
     * @param plugin      The plugin instance associated with this listener.
     */
    public VelocityListener(ProxyServer proxyServer, Logger logger, Object plugin) {
        super(proxyServer, logger, plugin);

        proxyServer.getEventManager().register(plugin, this);
        logger.info("Registered listener {}", getClassName());
    }
}
