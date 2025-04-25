package io.github.spigotrce.craftkernal.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

/**
 * This abstract class serves as a base for creating custom listeners in a Velocity plugin.
 * It automatically registers the listener with the proxy server.
 */
public class VelocityListener {
    public final String name;
    public final ProxyServer proxyServer;
    public final Logger logger;
    public final Object plugin;

    public VelocityListener(ProxyServer proxyServer, Logger logger, Object plugin) {
        this.name = this.getClass().getSimpleName();
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.plugin = plugin;

        proxyServer.getEventManager().register(plugin, this);
        logger.info("Registered listener {}", name);
    }
}
