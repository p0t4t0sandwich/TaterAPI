/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.velocity.v3_3_0;

import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;

import dev.neuralnexus.taterapi.Platform;
import dev.neuralnexus.taterapi.TaterAPIProvider;
import dev.neuralnexus.taterapi.event.api.CommandEvents;
import dev.neuralnexus.taterapi.event.api.NetworkEvents;
import dev.neuralnexus.taterapi.event.api.ServerEvents;
import dev.neuralnexus.taterapi.event.server.impl.ServerStartedEventImpl;
import dev.neuralnexus.taterapi.event.server.impl.ServerStoppedEventImpl;
import dev.neuralnexus.taterlib.TaterLibPlugin;
import dev.neuralnexus.taterlib.velocity.v3_3_0.event.command.VelocityBrigadierCommandRegisterEvent;
import dev.neuralnexus.taterlib.velocity.v3_3_0.event.command.VelocityCommandRegisterEvent;
import dev.neuralnexus.taterlib.velocity.v3_3_0.event.network.VelocityRegisterPacketChannelsEvent;
import dev.neuralnexus.taterlib.velocity.v3_3_0.hooks.permissions.VelocityPermissionsHook;
import dev.neuralnexus.taterlib.velocity.v3_3_0.listeners.network.VelocityPluginMessageListener;
import dev.neuralnexus.taterlib.velocity.v3_3_0.listeners.player.VelocityPlayerListener;
import dev.neuralnexus.taterlib.velocity.v3_3_0.listeners.server.VelocityServerListener;
import dev.neuralnexus.taterlib.velocity.v3_3_0.server.VelocityProxyServer;
import dev.neuralnexus.taterloader.Loader;

import java.time.Duration;

public class VelocityTaterLibPlugin implements TaterLibPlugin {
    @Override
    public void onInit() {
        TaterAPIProvider.addHook(new VelocityPermissionsHook());
        start();
        TaterAPIProvider.api(Platform.VELOCITY)
                .ifPresent(api -> api.setServer(VelocityProxyServer::instance));
    }

    @Override
    public void onEnable() {
        // Register listeners
        PluginContainer plugin = (PluginContainer) Loader.instance().plugin();
        ProxyServer proxyServer = (ProxyServer) Loader.instance().server();
        EventManager eventManager = proxyServer.getEventManager();
        eventManager.register(plugin, new VelocityPlayerListener());
        eventManager.register(plugin, new VelocityPluginMessageListener());
        eventManager.register(plugin, new VelocityServerListener());

        proxyServer
                .getScheduler()
                .buildTask(
                        plugin,
                        () -> {
                            // Register commands
                            CommandEvents.REGISTER_COMMAND.invoke(
                                    new VelocityCommandRegisterEvent());
                            CommandEvents.REGISTER_BRIGADIER_COMMAND.invoke(
                                    new VelocityBrigadierCommandRegisterEvent());

                            // Register plugin messages
                            NetworkEvents.REGISTER_PLUGIN_MESSAGES.invoke(
                                    new VelocityRegisterPacketChannelsEvent());

                            // Fire server started event
                            ServerEvents.STARTED.invoke(new ServerStartedEventImpl());
                        })
                .delay(Duration.ofSeconds(5))
                .schedule();
    }

    @Override
    public void onDisable() {
        ServerEvents.STOPPED.invoke(new ServerStoppedEventImpl());
        stop();
    }
}
