/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.v1_7_10.fabric.server;

import dev.neuralnexus.taterapi.entity.player.SimplePlayer;
import dev.neuralnexus.taterapi.server.Server;
import dev.neuralnexus.taterapi.world.ServerWorld;
import dev.neuralnexus.taterlib.v1_7_10.fabric.entity.player.FabricPlayer;
import dev.neuralnexus.taterlib.v1_7_10.fabric.world.FabricServerWorld;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/** Fabric implementation of {@link Server}. */
public class FabricServer implements Server {
    private final MinecraftServer server;

    public FabricServer(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public String brand() {
        return server.getServerModName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SimplePlayer> onlinePlayers() {
        return ((List<PlayerEntity>) server.getPlayerManager().players)
                .stream().map(FabricPlayer::new).collect(Collectors.toList());
    }

    @Override
    public List<ServerWorld> worlds() {
        return Arrays.stream(server.worlds)
                .map(FabricServerWorld::new)
                .collect(Collectors.toList());
    }
}
