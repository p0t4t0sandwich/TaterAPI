/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.v1_7_10.bukkit.event.network;

import dev.neuralnexus.taterapi.event.network.RegisterPacketChannelsEvent;
import dev.neuralnexus.taterlib.v1_7_10.bukkit.listeners.network.BukkitPluginMessageListener;
import dev.neuralnexus.taterloader.Loader;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;

/** Bukkit implementation of {@link RegisterPacketChannelsEvent}. */
public class BukkitRegisterPacketChannelsEvent implements RegisterPacketChannelsEvent {
    @Override
    public void register(String channel) {
        Plugin plugin = (Plugin) Loader.instance().plugin();
        Messenger messenger = Bukkit.getServer().getMessenger();
        messenger.registerIncomingPluginChannel(plugin, channel, new BukkitPluginMessageListener());
        messenger.registerOutgoingPluginChannel(plugin, channel);
    }
}
