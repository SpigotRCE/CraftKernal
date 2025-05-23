package io.spigotrce.craftkernal.velocity.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import io.spigotrce.craftkernal.velocity.VelocityHolder;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

/**
 * This abstract class serves as a base for creating custom commands in a Velocity plugin.
 * It provides functionality for registering commands with the proxy server using Brigadier
 * and includes helper methods for command execution and tab completion.
 */
public abstract class VelocityBrigadierCommand extends VelocityCommandHolder {
    /*
        * The {@link VelocityHolder} holder.
     */
    private final VelocityHolder holder;

    /**
     * A constant representing a successful command execution.
     */
    public final int SINGLE_SUCCESS = Command.SINGLE_SUCCESS;

    /**
     * Constructs a new VelocityBrigadierCommand with the specified proxy server, logger, and plugin, command name, and command aliases.
     *
     * @param holder    The {@link VelocityHolder} holder.
     * @param commandName    The name of the command.
     * @param commandAliases The aliases of the command.
     */
    public VelocityBrigadierCommand(VelocityHolder holder, String commandName, String... commandAliases) {
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
                new BrigadierCommand(
                        this.build(
                                literal(
                                        getCommandName()
                                )
                        )
                )
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

    /**
     * Builds the command using a Brigadier literal argument builder.
     *
     * @param root The root literal argument builder.
     * @return A Brigadier literal argument builder for the command.
     */
    public abstract LiteralArgumentBuilder<CommandSource> build(LiteralArgumentBuilder<CommandSource> root);

    /**
     * Suggests online players for tab completion.
     *
     * @param ctx          The command context.
     * @param builder      The suggestions builder.
     * @param argumentName The name of the argument to suggest players for.
     * @return A CompletableFuture containing the suggestions.
     */
    public CompletableFuture<Suggestions> suggestOnlinePlayers(CommandContext<?> ctx, SuggestionsBuilder builder, String argumentName) {
        String partialName;

        try {
            partialName = ctx.getArgument(argumentName, String.class).toLowerCase();
        } catch (IllegalArgumentException ignored) {
            partialName = "";
        }

        if (partialName.isEmpty()) {
            this.holder.getServer().getAllPlayers().stream().map(Player::getUsername).forEach(builder::suggest);
            return builder.buildFuture();
        }

        String finalPartialName = partialName;

        this.holder.getServer().getAllPlayers().stream().map(Player::getUsername).filter(name -> name.toLowerCase().startsWith(finalPartialName)).forEach(builder::suggest);

        return builder.buildFuture();
    }

    /**
     * Creates a literal argument builder for Brigadier.
     *
     * @param name The name of the literal argument.
     * @return A Brigadier literal argument builder.
     */
    public LiteralArgumentBuilder<CommandSource> literal(final String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    /**
     * Creates a required argument builder for Brigadier.
     *
     * @param name The name of the argument.
     * @param type The type of the argument.
     * @param <T>  The type parameter of the argument.
     * @return A Brigadier required argument builder.
     */
    public <T> RequiredArgumentBuilder<CommandSource, T> argument(final String name, final ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }
}
