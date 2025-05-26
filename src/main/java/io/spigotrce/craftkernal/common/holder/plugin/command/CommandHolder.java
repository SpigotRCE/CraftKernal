package io.spigotrce.craftkernal.common.holder.plugin.command;

public abstract class CommandHolder {
    /**
     * The name of the command.
     */
    private final String commandName;

    /**
     * The top level permission node for the command.
     */
    private final String permissionNode;

    /**
     * The aliases of the command.
     */
    private final String[] commandAliases;

    /**
     * Constructs a new CommandHolder with the specified commandName, permissionNode, and commandAliases.
     *
     * @param commandName    The name of the command.
     * @param permissionNode The top level permission node for the command.
     * @param commandAliases The aliases of the command.
     */
    public CommandHolder(String commandName, String permissionNode, String... commandAliases) {
        this.commandName = commandName;
        this.permissionNode = permissionNode;
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
     * Gets the top level permission node for the command.
     *
     * @return The top level permission node for the command.
     */
    public String getPermissionNode() {
        return permissionNode;
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
