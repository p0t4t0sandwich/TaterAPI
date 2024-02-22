package dev.neuralnexus.taterlib.forge.server;

import dev.neuralnexus.taterlib.forge.player.ForgePlayer;
import dev.neuralnexus.taterlib.player.SimplePlayer;
import dev.neuralnexus.taterlib.server.Server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.stream.Collectors;

/** Forge implementation of {@link Server}. */
public class ForgeServer implements Server {
    private final MinecraftServer server;

    public ForgeServer(MinecraftServer server) {
        this.server = server;
    }

    /** {@inheritDoc} */
    @Override
    public String brand() {
        return server.getServerModName();
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public List<SimplePlayer> onlinePlayers() {
        return ((List<EntityPlayer>) server.getConfigurationManager().playerEntityList)
                .stream().map(ForgePlayer::new).collect(Collectors.toList());
    }
}
