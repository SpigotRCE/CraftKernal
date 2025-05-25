# CraftKernal 
A Minecraft: Java Edition plugin development library with various utilities and features to enhance the development experience.

## Features
- **Static Free Instance Access**: Provides a static-free way to access plugin instances, allowing for cleaner and more maintainable code.
- **Configuration Management**: Simplified configuration file handling with support for YAML formats.
- **Command Handling**: Easy command registration and execution without needing to write boilerplate code.
- **Event Handling**: Simplified event listener registration and management with as well no need of writing boilerplate code.
- **Plugin Messaging**: Simplified inter-server communication via packets with a robust messaging system.


## Examples

### PluginHolder Example

* Example of a Paper PluginHolder(PaperHolder.java):
```java
private PaperHolder HOLDER;

public void onEnable() {
    HOLDER = new PaperHolder(getServer(), getLogger(), this);
}
```

* Example of a Velocity PluginHolder(VelocityHolder.java):
```java
private VelocityHolder HOLDER;
@Inject
public VelocityPlugin(Logger logger, @DataDirectory Path dataDirectory, ProxyServer proxyServer) {
    HOLDER = new VelocityHolder(proxyServer, logger, this);
}
```

* Example of a BungeeCord PluginHolder(BungeeHolder.java):
```java
private BungeeHolder HOLDER;

public void onEnable() {
    HOLDER = new BungeeHolder(getServer(), getLogger(), this);
}
```

### Configuration Example

* Example of a configuration file (`config.yml`):
```yaml
version: 1`
mysql:
  host: localhost
  port: 3306
  database: my_database
```

* Configuration class to read the above file:
```java
    public class Config extends ConfigProvider {
    public VelocityConfig(File dataDirectory) {
        super("config.yml", "version", dataDirectory);
    }

    public String getMySQLHost() {
        return getFileConfig().getString(Route.fromString("mysql.host"));
    }

    public int getMySQLPort() {
        return getFileConfig().getInt(Route.fromString("mysql.port"));
    }

    public String getMySQLDb() {
        return getFileConfig().getString(Route.fromString("mysql.database"));
    }
}
```

* Velocity Config Implementation:
```java
@Inject
public VelocityPlugin(Logger logger, @DataDirectory Path dataDirectory, ProxyServer proxyServer) {
    CONFIG = new Config(dataDirectory.toFile()); // Considering you already have a CONFIG field in your plugin main class
    try {
        CONFIG.load();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
```

* Paper Config Implementation:
```java
public void onEnable() {
    CONFIG = new Config(getDataFolder()); // Considering you already have a CONFIG field in your plugin main class
    try {
        CONFIG.load();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
```

* BungeeCord Config Implementation:
```java
public void onEnable() {
    CONFIG = new Config(getDataFolder()); // Considering you already have a CONFIG field in your plugin main class
    try {
        CONFIG.load();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
```

* To reload a configuration file just call the **reload** method on the config instance.

### Command Example
* Velocity Command Example: In velocity you can create two commands:
The first one is a simple command, follow Paper-like structure. 
The second one is a Brigadier command.

* Velocity Simple Command:

```java
public class AlertCommand extends VelocitySimpleCommand {
    public AlertCommand(VelocityHolder holder) {
        super(holder, "alert", "myplugin.alert", "alert", "a");
    }

    @Override
    public void execute(Invocation invocation) {
        String message = String.join(" ", invocation.arguments());
        invocation.source().sendMessage(Component.text("Alert: " + message));
        getHolder().getServer().getAllPlayers().forEach(player -> {
            player.sendMessage(Component.text(message));
        });
    }
}
```

* Velocity Brigadier Command:

```java
public class AlertCommand extends VelocityBrigadierCommand {
    public AlertCommand(VelocityHolder holder) {
        super(holder, "alert", "myplugin.alert", "alert", "a");
    }

    @Override
    public LiteralArgumentBuilder<CommandSource> build(LiteralArgumentBuilder<CommandSource> root) {
        return root.executes(commandContext -> {
                    commandContext.getSource().sendMessage(Component.text("Usage: /alert <message>"));
                    return SINGLE_SUCCESS;
                })
                .then(argument("message", StringArgumentType.greedyString())
                        .executes(commandContext -> {
                            String message = StringArgumentType.getString(commandContext, "message");
                            commandContext.getSource().sendMessage(Component.text("Alert: " + message));
                            getHolder().getServer().getAllPlayers().forEach(player -> player.sendMessage(Component.text(message)));
                            return SINGLE_SUCCESS;
                        }));
    }
}
```
