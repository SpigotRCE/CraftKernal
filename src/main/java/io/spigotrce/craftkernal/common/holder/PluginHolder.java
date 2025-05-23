package io.spigotrce.craftkernal.common.holder;

public class PluginHolder<S, L, P> extends ClassHolder {
    /**
     * The specified server instance.
     */
    private final S server;

    /**
     * The specified logger instance.
     */
    private final L logger;

    /**
     * The specified plugin instance.
     */
    private final P plugin;

    /**
     * Constructs a new PluginHolder with the specified proxy server, logger, and plugin.
     *
     * @param server The specified server instance.
     * @param logger The specified logger instance.
     * @param plugin The specified plugin instance.
     */
    public PluginHolder(S server, L logger, P plugin) {
        this.server = server;
        this.logger = logger;
        this.plugin = plugin;
    }

    /**
     * Gets the specified server instance.
     *
     * @return The specified server instance.
     */
    public S getServer() {
        return server;
    }

    /**
     * Gets the specified logger instance.
     *
     * @return The specified logger instance.
     */
    public L getLogger() {
        return logger;
    }

    /**
     * Gets the specified plugin instance.
     *
     * @return The specified plugin instance.
     */
    public P getPlugin() {
        return plugin;
    }
}
