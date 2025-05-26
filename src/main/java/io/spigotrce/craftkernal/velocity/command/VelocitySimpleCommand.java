package io.spigotrce.craftkernal.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import io.spigotrce.craftkernal.common.holder.plugin.command.CommandHolder;
import io.spigotrce.craftkernal.velocity.VelocityHolder;

public abstract class VelocitySimpleCommand extends CommandHolder implements SimpleCommand {
    /**
     * The {@link VelocityHolder} holder.
     */
    private final VelocityHolder holder;

    /**
     * Constructs a new VelocitySimpleCommand with the specified proxy server, logger, and plugin, command name, and command aliases.
     *
     * @param holder         The {@link VelocityHolder} holder.
     * @param commandName    The name of the command.
     * @param permissionNode The top level permission node for the command.
     * @param commandAliases The aliases of the command.
     */
    public VelocitySimpleCommand(VelocityHolder holder, String commandName, String permissionNode, String... commandAliases) {
        super(commandName, permissionNode, commandAliases);
        this.holder = holder;
        this.holder.getServer().getCommandManager().register(
                this.holder.getServer().getCommandManager().metaBuilder(getCommandName())
                        .aliases(getCommandAliases()).plugin(this.holder.getPlugin()).build(),
                this
        );
        this.holder.getLogger().info("Registered command {}", getCommandName());
    }

    /**
     * Gets the {@link VelocityHolder} holder.
     *
     * @return The {@link VelocityHolder} holder.
     */
    public VelocityHolder getHolder() {
        return holder;
    }
}
