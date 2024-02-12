package dev.neuralnexus.taterlib.velocity.player;

import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import dev.neuralnexus.taterlib.player.ProxyPlayer;
import dev.neuralnexus.taterlib.server.Server;
import dev.neuralnexus.taterlib.velocity.VelocityTaterLibPlugin;
import dev.neuralnexus.taterlib.velocity.server.VelocityServer;

import net.kyori.adventure.text.Component;

import java.util.UUID;

/** Velocity implementation of {@link ProxyPlayer}. */
public class VelocityPlayer implements ProxyPlayer {
    private final com.velocitypowered.api.proxy.Player player;

    /**
     * Constructor.
     *
     * @param player The Velocity player.
     */
    public VelocityPlayer(com.velocitypowered.api.proxy.Player player) {
        this.player = player;
    }

    /**
     * Gets the Velocity player
     *
     * @return The Velocity player
     */
    public com.velocitypowered.api.proxy.Player player() {
        return player;
    }

    /**
     * Connect the player to a server.
     *
     * @param serverName The name of the server to connect to.
     */
    @Override
    public void connect(String serverName) {
        if (!VelocityTaterLibPlugin.getProxyServer().getServer(serverName).isPresent()) return;
        RegisteredServer server =
                VelocityTaterLibPlugin.getProxyServer().getServer(serverName).get();
        player.createConnectionRequest(server).fireAndForget();
    }

    /** {@inheritDoc} */
    @Override
    public UUID uuid() {
        return player.getUniqueId();
    }

    /** {@inheritDoc} */
    @Override
    public String ipAddress() {
        return player.getRemoteAddress().getAddress().getHostAddress();
    }

    /** {@inheritDoc} */
    @Override
    public String name() {
        return player.getUsername();
    }

    /** {@inheritDoc} */
    @Override
    public String displayName() {
        return player.getUsername();
    }

    /** {@inheritDoc} */
    @Override
    public Server server() {
        if (!player.getCurrentServer().isPresent()) return null;
        return new VelocityServer(player.getCurrentServer().get().getServer());
    }

    /** {@inheritDoc} */
    @Override
    public void sendMessage(String message) {
        player.sendMessage(Component.text(message));
    }

    /** {@inheritDoc} */
    @Override
    public void sendPluginMessage(String channel, byte[] data) {
        player.getCurrentServer()
                .ifPresent(
                        serverConnection ->
                                serverConnection.sendPluginMessage(
                                        MinecraftChannelIdentifier.from(channel), data));
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasPermission(int permissionLevel) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public int ping() {
        return (int) player.getPing();
    }

    /** {@inheritDoc} */
    @Override
    public void kick(String message) {
        player.disconnect(Component.text(message));
    }
}
