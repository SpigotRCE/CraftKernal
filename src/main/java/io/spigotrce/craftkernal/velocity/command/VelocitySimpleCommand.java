package io.spigotrce.craftkernal.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import io.spigotrce.craftkernal.velocity.VelocityHolder;

public abstract class VelocitySimpleCommand extends VelocityCommandHolder implements SimpleCommand {
    /*
     * The {@link VelocityHolder} holder.
     */
    private final VelocityHolder holder;

    /**
     * Constructs a new VelocitySimpleCommand with the specified proxy server, logger, and plugin, command name, and command aliases.
     *
     * @param holder         The {@link VelocityHolder} holder.
     * @param commandName    The name of the command.
     * @param commandAliases The aliases of the command.
     */
    public VelocitySimpleCommand(VelocityHolder holder, String commandName, String... commandAliases) {
        super(commandName, commandAliases);
        this.holder = holder;
    }

    /**
     * Registers the command with the proxy server.
     */
    public void register() {
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
