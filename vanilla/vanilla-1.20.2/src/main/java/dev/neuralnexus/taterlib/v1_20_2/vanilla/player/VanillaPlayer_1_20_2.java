package dev.neuralnexus.taterlib.v1_20_2.vanilla.player;

import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.v1_20.vanilla.player.VanillaPlayer;
import dev.neuralnexus.taterlib.v1_20_2.vanilla.network.VanillaCustomPacketPayload;

import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;

/** Vanilla implementation of {@link Player}. */
public class VanillaPlayer_1_20_2 extends VanillaPlayer {
    private final net.minecraft.world.entity.player.Player player;

    /**
     * Constructor.
     *
     * @param player The player.
     */
    public VanillaPlayer_1_20_2(net.minecraft.world.entity.player.Player player) {
        super(player);
        this.player = player;
    }

    /**
     * Constructor.
     *
     * @param player The player.
     * @param serverName The server name.
     */
    public VanillaPlayer_1_20_2(
            net.minecraft.world.entity.player.Player player, String serverName) {
        super(player, serverName);
        this.player = player;
    }

    /**
     * Gets the player
     *
     * @return The player
     */
    public net.minecraft.world.entity.player.Player getPlayer() {
        return player;
    }

    /** {@inheritDoc} */
    @Override
    public void sendPluginMessage(String channel, byte[] data) {
        ((ServerPlayer) player)
                .connection.send(
                        new ClientboundCustomPayloadPacket(
                                new VanillaCustomPacketPayload(channel, data)));
    }

    /** {@inheritDoc} */
    @Override
    public int getPing() {
        return ((ServerPlayer) player).connection.latency();
    }
}