/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.taterlib.v1_12_2.bungee;

import dev.neuralnexus.taterlib.TaterLibPlugin;
import dev.neuralnexus.taterlib.api.Platform;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.event.api.CommandEvents;
import dev.neuralnexus.taterlib.event.api.ServerEvents;
import dev.neuralnexus.taterlib.v1_12_2.bungee.event.command.BungeeCommandRegisterEvent;
import dev.neuralnexus.taterlib.v1_12_2.bungee.event.server.BungeeServerStartedEvent;
import dev.neuralnexus.taterlib.v1_12_2.bungee.event.server.BungeeServerStartingEvent;
import dev.neuralnexus.taterlib.v1_12_2.bungee.event.server.BungeeServerStoppedEvent;
import dev.neuralnexus.taterlib.v1_12_2.bungee.event.server.BungeeServerStoppingEvent;
import dev.neuralnexus.taterlib.v1_12_2.bungee.hooks.permissions.BungeePermissionsHook;
import dev.neuralnexus.taterlib.v1_12_2.bungee.listeners.network.BungeePluginMessageListener;
import dev.neuralnexus.taterlib.v1_12_2.bungee.listeners.player.BungeePlayerListener;
import dev.neuralnexus.taterlib.v1_12_2.bungee.server.BungeeProxyServer;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.concurrent.TimeUnit;

public class BungeeTaterLibPlugin implements TaterLibPlugin {
    private Plugin plugin;

    @Override
    public void onInit(Object plugin, Object server) {
        this.plugin = (Plugin) plugin;
        TaterAPIProvider.addHook(new BungeePermissionsHook());
        start(plugin, server);
        TaterAPIProvider.api(Platform.BUNGEECORD)
                .ifPresent(api -> api.setServer(BungeeProxyServer::instance));
    }

    @Override
    public void onEnable() {
        // Register listeners
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        ProxyServer.getInstance()
                .getScheduler()
                .schedule(
                        plugin,
                        () ->
                                CommandEvents.REGISTER_COMMAND.invoke(
                                        new BungeeCommandRegisterEvent()),
                        5L,
                        TimeUnit.SECONDS);
        pluginManager.registerListener(plugin, new BungeePlayerListener());
        pluginManager.registerListener(plugin, new BungeePluginMessageListener());
        ServerEvents.STARTING.invoke(new BungeeServerStartingEvent());
        ProxyServer.getInstance()
                .getScheduler()
                .schedule(
                        plugin,
                        () -> ServerEvents.STARTED.invoke(new BungeeServerStartedEvent()),
                        5L,
                        TimeUnit.SECONDS);
    }

    @Override
    public void onDisable() {
        // Run server stopping events
        ServerEvents.STOPPING.invoke(new BungeeServerStoppingEvent());
        ServerEvents.STOPPED.invoke(new BungeeServerStoppedEvent());
        stop();
    }
}
