package io.spigotrce.craftkernal.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import io.spigotrce.craftkernal.velocity.VelocityHolder;
import org.slf4j.Logger;

public abstract class VelocitySimpleCommand extends VelocityHolder implements SimpleCommand {
    /**
     * The name of the command.
     */
    private final String commandName;

    /**
     * The aliases of the command.
     */
    private final String[] commandAliases;

    /**
     * Constructs a new VelocityHolder with the specified proxy server, logger, and plugin.
     *
     * @param name        The name of the command.
     * @param proxyServer The Velocity proxy server instance.
     * @param logger      The logger instance for logging.
     * @param plugin      The plugin instance associated with this holder.
     * @param aliases     The aliases of the command.
     */
    public VelocitySimpleCommand(String name, ProxyServer proxyServer, Logger logger, Object plugin, String... aliases) {
        super(proxyServer, logger, plugin);
        this.commandName = name;
        this.commandAliases = aliases;
    }

    /**
     * Gets the name of the command.
     *
     * @return The name of the command.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Gets the aliases of the command.
     *
     * @return The aliases of the command.
     */
    public String[] getCommandAliases() {
        return commandAliases;
    }
}
