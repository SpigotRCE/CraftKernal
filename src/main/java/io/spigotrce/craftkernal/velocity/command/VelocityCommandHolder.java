package io.spigotrce.craftkernal.velocity.command;

public abstract class VelocityCommandHolder {
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
     * @param commandName    The name of the command.
     * @param commandAliases The aliases of the command.
     */
    public VelocityCommandHolder(String commandName, String... commandAliases) {
        this.commandName = commandName;
        this.commandAliases = commandAliases;
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
