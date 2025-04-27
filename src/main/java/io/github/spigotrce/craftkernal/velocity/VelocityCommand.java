package io.github.spigotrce.craftkernal.velocity;

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
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

/**
 * This abstract class serves as a base for creating custom commands in a Velocity plugin.
 * It automatically registers the command with the proxy server using Brigadier and provides
 * common functionality for executing and tab-completing commands.
 */
public abstract class VelocityCommand extends VelocityHolder {
    public final String commandName;
    public final String[] commandAliases;
    public final int SINGLE_SUCCESS = Command.SINGLE_SUCCESS;
    /**
     * Constructs a new Command instance and registers the command with the server's CommandMap.
     *
     * @param commandName    The name of the command.
     * @param commandAliases The command aliases.
     */
    public VelocityCommand(String commandName, ProxyServer proxyServer, Logger logger, Object plugin, String... commandAliases) {
        super(proxyServer, logger, plugin);
        this.commandName = commandName;
        this.commandAliases = commandAliases;

        getProxyServer().getCommandManager().register(
                getProxyServer().getCommandManager().metaBuilder(commandName)
                        .aliases(commandAliases).plugin(plugin).build(),
                this.build()
        );
        getLogger().info("Registered command {}", commandName);
    }

    /**
     * Returns the BrigadierCommand implementation for this command.
     *
     * @return The BrigadierCommand implementation.
     */
    public abstract BrigadierCommand build();

    /**
     * Helper method to suggest online players.
     *
     * @param ctx     The command context.
     * @param builder The suggestions builder.
     * @return A CompletableFuture containing the suggestions.
     */
    public CompletableFuture<Suggestions> suggestOnlinePlayers(CommandContext<?> ctx, SuggestionsBuilder builder) {
        String partialName;

        try {
            partialName = ctx.getArgument("user", String.class).toLowerCase();
        } catch (IllegalArgumentException ignored) {
            partialName = "";
        }

        if (partialName.isEmpty()) {
            getProxyServer().getAllPlayers().stream().map(Player::getUsername).forEach(builder::suggest);
            return builder.buildFuture();
        }

        String finalPartialName = partialName;

        getProxyServer().getAllPlayers().stream().map(Player::getUsername).filter(name -> name.toLowerCase().startsWith(finalPartialName)).forEach(builder::suggest);

        return builder.buildFuture();
    }

    /**
     * Helper method to create a literal argument builder for Brigadier.
     *
     * @param name The name of the literal argument.
     * @return A Brigadier literal argument builder.
     */
    public LiteralArgumentBuilder<CommandSource> literal(final String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    /**
     * Helper method to create an argument builder for Brigadier.
     *
     * @param name The name of the argument.
     * @param type The type of the argument.
     * @return A Brigadier required argument builder.
     */
    public <T> RequiredArgumentBuilder<CommandSource, T> argument(final String name, final ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }
}
