package io.spigotrce.craftkernal.paper.command;

import io.spigotrce.craftkernal.common.holder.plugin.command.CommandHolder;
import io.spigotrce.craftkernal.paper.PaperHolder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class PaperCommand extends CommandHolder implements TabExecutor {
    /**
     * The {@link PaperHolder} holder.
     */
    private final PaperHolder holder;

    /**
     * Constructs a new CommandHolder with the specified commandName, permissionNode, and commandAliases.
     *
     * @param holder         The PaperHolder instance.
     * @param commandName    The name of the command.
     * @param permissionNode The top level permission node for the command.
     * @param commandAliases The aliases of the command.
     * @throws NoSuchFieldException   If the command map field cannot be accessed.
     * @throws IllegalAccessException If the command map field cannot be accessed.
     */
    public PaperCommand(PaperHolder holder, String commandName, String permissionNode, String... commandAliases) throws NoSuchFieldException, IllegalAccessException {
        super(commandName, permissionNode, commandAliases);
        this.holder = holder;

        Command command = new Command(getCommandName()) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
                return PaperCommand.this.onCommand(sender, this, label, args);
            }

            @Override
            public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
                List<String> completions = PaperCommand.this.onTabComplete(sender, this, alias, args);
                return Objects.requireNonNullElseGet(completions, List::of);
            }
        };

        Field bukkitCommandMap = this.holder.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        CommandMap commandMap = (CommandMap) bukkitCommandMap.get(this.holder.getServer());

        commandMap.register(this.holder.getPlugin().getName().toLowerCase() + ":" + getCommandName(), command);

    }

    /**
     * Gets the {@link PaperHolder} holder.
     *
     * @return The {@link PaperHolder} holder.
     */
    public PaperHolder getHolder() {
        return holder;
    }

    /**
     * Executes the command with the given sender and arguments.
     *
     * @param commandSender The command sender.
     * @param command       The command being executed.
     * @param label         The label of the command.
     * @param args          The arguments of the command.
     * @return true if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return false;
    }

    /**
     * Handles tab completion for the command.
     *
     * @param commandSender The command sender.
     * @param command       The command being executed.
     * @param label         The label of the command.
     * @param args          The arguments of the command.
     * @return A list of suggestions for tab completion, or null if no suggestions are available.
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
