/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.v1_15_2.bukkit.event.server;

import dev.neuralnexus.taterapi.event.server.ServerStartingEvent;
import dev.neuralnexus.taterapi.server.SimpleServer;
import dev.neuralnexus.taterlib.v1_15_2.bukkit.server.BukkitServer;

import org.bukkit.Bukkit;

/** Bukkit implementation of {@link ServerStartingEvent}. */
public class BukkitServerStartingEvent implements ServerStartingEvent {
    @Override
    public SimpleServer server() {
        return new BukkitServer(Bukkit.getServer());
    }
}
