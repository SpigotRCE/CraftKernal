package io.spigotrce.craftkernal.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

public abstract class VelocitySimpleCommand extends VelocityCommandHolder implements SimpleCommand {
    /**
     * Constructs a new VelocitySimpleCommand with the specified proxy server, logger, and plugin, command name, and command aliases.
     *
     * @param proxyServer    The Velocity proxy server instance.
     * @param logger         The logger instance for logging.
     * @param plugin         The plugin instance associated with this holder.
     * @param commandName    The name of the command.
     * @param commandAliases The aliases of the command.
     */
    public VelocitySimpleCommand(ProxyServer proxyServer, Logger logger, Object plugin, String commandName, String... commandAliases) {
        super(proxyServer, logger, plugin, commandName, commandAliases);
    }

    /**
     * Registers the command with the proxy server.
     */
    public void register() {
        getProxyServer().getCommandManager().register(
                getProxyServer().getCommandManager().metaBuilder(getCommandName())
                        .aliases(getCommandAliases()).plugin(getPlugin()).build(),
                this
        );
        getLogger().info("Registered command {}", getCommandName());
    }
}
