package dev.neuralnexus.taterlib.server;

import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.server.metrics.TPSProvider;
import dev.neuralnexus.taterlib.world.ServerWorld;

import java.util.List;
import java.util.Optional;

/** Abstracts a Minecraft server. */
public interface Server extends SimpleServer {
    /**
     * Get the server's current TPS.
     *
     * @return The server's current TPS.
     */
    default double currentTPS() {
        Optional<TPSProvider> provider = TaterAPIProvider.getTPSProvider();
        return provider.map(TPSProvider::currentTPS).orElse(-1.0);
    }

    //    /**
    //     * Get the server's current tick.
    //     *
    //     * @return The server's current tick.
    //     */
    //    long currentTick();

    /**
     * Get the server's worlds.
     *
     * @return The server's worlds.
     */
    List<ServerWorld> worlds();

    /**
     * Get the server's world by dimension.
     *
     * @param dimension The dimension.
     * @return The server's world by dimension.
     */
    default Optional<ServerWorld> world(String dimension) {
        return worlds().stream().filter(world -> world.dimension().equals(dimension)).findFirst();
    }
}