package dev.neuralnexus.taterlib.bukkit;

import dev.neuralnexus.taterlib.bukkit.abstractions.events.server.BukkitServerStartedEvent;
import dev.neuralnexus.taterlib.bukkit.abstractions.events.server.BukkitServerStartingEvent;
import dev.neuralnexus.taterlib.bukkit.abstractions.events.server.BukkitServerStoppedEvent;
import dev.neuralnexus.taterlib.bukkit.abstractions.events.server.BukkitServerStoppingEvent;
import dev.neuralnexus.taterlib.bukkit.commands.BukkitTaterLibCommand;
import dev.neuralnexus.taterlib.bukkit.listeners.entity.BukkitEntityListener;
import dev.neuralnexus.taterlib.bukkit.listeners.player.BukkitPlayerListener;
import dev.neuralnexus.taterlib.bukkit.listeners.pluginmessages.BukkitPluginMessageListener;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.TaterLibPlugin;
import dev.neuralnexus.taterlib.common.commands.TaterLibCommand;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import dev.neuralnexus.taterlib.common.listeners.server.ServerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.messaging.Messenger;

/**
 * The TaterLib Bukkit plugin.
 */
public class BukkitTaterLibPlugin extends TemplateBukkitPlugin implements TaterLibPlugin {
    private static BukkitTaterLibPlugin instance;

    /**
     * Gets the instance of the plugin
     * @return The instance of the plugin
     */
    public static BukkitTaterLibPlugin getInstance() {
        return instance;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerHooks() {
        // Register LuckPerms hook
        if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
            useLogger("LuckPerms detected, enabling LuckPerms hook.");
            TaterLib.addHook(new LuckPermsHook());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerEventListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        // Register player listeners
        pluginManager.registerEvents(new BukkitPlayerListener(), this);

        // Register entity listeners
        pluginManager.registerEvents(new BukkitEntityListener(), this);

        // Register server listeners
        ServerListener.onServerStarting(new BukkitServerStartingEvent());
        getServer().getScheduler().runTaskLater(this, () -> ServerListener.onServerStarted(new BukkitServerStartedEvent()), 5*20L);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void registerCommands() {
        getCommand(TaterLibCommand.getCommandName()).setExecutor(new BukkitTaterLibCommand());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onEnable() {
        instance = this;

        // Register plugin message channels
        Messenger messenger = getServer().getMessenger();
        TaterLib.setRegisterChannels((channels) -> channels.forEach((channel) -> {
            messenger.registerIncomingPluginChannel(this, channel, new BukkitPluginMessageListener());
            messenger.registerOutgoingPluginChannel(this, channel);
        }));

        pluginStart();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        // Run server stopping events
        ServerListener.onServerStopping(new BukkitServerStoppingEvent());
        ServerListener.onServerStopped(new BukkitServerStoppedEvent());
        pluginStop();
    }
}