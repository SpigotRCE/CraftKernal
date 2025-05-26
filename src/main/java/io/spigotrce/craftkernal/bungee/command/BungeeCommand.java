package io.spigotrce.craftkernal.bungee.command;

import io.spigotrce.craftkernal.bungee.BungeeHolder;
import io.spigotrce.craftkernal.common.holder.command.CommandHolder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public abstract class BungeeCommand extends CommandHolder implements TabExecutor {
    /**
     * The {@link BungeeHolder} holder.
     */
    private final BungeeHolder holder;

    /**
     * Constructs a new BungeeCommandHolder with the specified commandName server, permissionNode, and commandAliases.
     *
     * @param holder         The BungeeHolder instance.
     * @param commandName    The name of the command.
     * @param permissionNode The top level permission node for the command.
     * @param commandAliases The aliases of the command.
     */
    public BungeeCommand(BungeeHolder holder, String commandName, String permissionNode, String... commandAliases) {
        super(commandName, permissionNode, commandAliases);
        this.holder = holder;

        this.holder.getServer().getPluginManager().registerCommand(
                this.holder.getPlugin(),
                new Command(getCommandName(), getPermissionNode(), getCommandAliases()) {
                    @Override
                    public void execute(CommandSender sender, String[] args) {
                        BungeeCommand.this.execute(sender, args);
                    }
                }
        );
    }

    /**
     * Gets the {@link BungeeHolder} holder.
     *
     * @return The {@link BungeeHolder} holder.
     */
    public BungeeHolder getHolder() {
        return holder;
    }

    /**
     * Executes the command with the given sender and arguments.
     *
     * @param sender The command sender.
     * @param args   The command arguments.
     */
    public abstract void execute(CommandSender sender, String[] args);
}
