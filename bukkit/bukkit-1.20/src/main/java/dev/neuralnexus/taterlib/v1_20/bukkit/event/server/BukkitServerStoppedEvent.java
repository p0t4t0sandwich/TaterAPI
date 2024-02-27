package dev.neuralnexus.taterlib.v1_20.bukkit.event.server;

import dev.neuralnexus.taterlib.event.server.ServerStoppedEvent;
import dev.neuralnexus.taterlib.server.SimpleServer;
import dev.neuralnexus.taterlib.v1_20.bukkit.server.BukkitServer;

import org.bukkit.Bukkit;

/** Bukkit implementation of {@link ServerStoppedEvent}. */
public class BukkitServerStoppedEvent implements ServerStoppedEvent {
    /** {@inheritDoc} */
    @Override
    public SimpleServer server() {
        return new BukkitServer(Bukkit.getServer());
    }
}
