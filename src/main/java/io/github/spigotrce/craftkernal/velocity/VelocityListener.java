package io.github.spigotrce.craftkernal.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

/**
 * This abstract class serves as a base for creating custom listeners in a Velocity plugin.
 * It automatically registers the listener with the proxy server.
 */
public class VelocityListener extends VelocityHolder {
    public final String name;

    public VelocityListener(ProxyServer proxyServer, Logger logger, Object plugin) {
        super(proxyServer, logger, plugin);
        this.name = this.getClass().getSimpleName();

        proxyServer.getEventManager().register(plugin, this);
        logger.info("Registered listener {}", name);
    }
}
