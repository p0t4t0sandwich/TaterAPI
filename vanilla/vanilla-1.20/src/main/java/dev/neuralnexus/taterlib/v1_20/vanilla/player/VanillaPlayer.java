package dev.neuralnexus.taterlib.v1_20.vanilla.player;

import dev.neuralnexus.taterlib.inventory.PlayerInventory;
import dev.neuralnexus.taterlib.player.GameMode;
import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.server.Server;
import dev.neuralnexus.taterlib.utils.Location;
import dev.neuralnexus.taterlib.v1_20.vanilla.entity.VanillaLivingEntity;
import dev.neuralnexus.taterlib.v1_20.vanilla.inventory.VanillaPlayerInventory;
import dev.neuralnexus.taterlib.v1_20.vanilla.server.VanillaServer;

import io.netty.buffer.Unpooled;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

import java.util.UUID;

/** Vanilla implementation of {@link Player}. */
public class VanillaPlayer extends VanillaLivingEntity implements Player {
    private final net.minecraft.world.entity.player.Player player;

    /**
     * Constructor.
     *
     * @param player The player.
     */
    public VanillaPlayer(net.minecraft.world.entity.player.Player player) {
        super(player);
        this.player = player;
    }

    /**
     * Gets the player
     *
     * @return The player
     */
    public net.minecraft.world.entity.player.Player player() {
        return player;
    }

    /** {@inheritDoc} */
    @Override
    public UUID uuid() {
        return player.getUUID();
    }

    /** {@inheritDoc} */
    @Override
    public String ipAddress() {
        return ((ServerPlayer) player).getIpAddress();
    }

    /** {@inheritDoc} */
    @Override
    public String name() {
        return player.getName().getString();
    }

    /** {@inheritDoc} */
    @Override
    public String displayName() {
        return player.getDisplayName().getString();
    }

    /** {@inheritDoc} */
    @Override
    public Server server() {
        return VanillaServer.instance();
    }

    /** {@inheritDoc} */
    @Override
    public void sendMessage(String message) {
        player.displayClientMessage(Component.nullToEmpty(message), false);
    }

    /** {@inheritDoc} */
    @Override
    public void sendPluginMessage(String channel, byte[] data) {
        String[] channelParts = channel.split(":");
        if (channelParts.length == 1) {
            channelParts = new String[] {"tl-user-forgot", channelParts[0]};
        }
        ResourceLocation id = new ResourceLocation(channelParts[0], channelParts[1]);
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        byteBuf.writeBytes(data);
        ((ServerPlayer) player).connection.send(new ClientboundCustomPayloadPacket(id, byteBuf));
    }

    /** {@inheritDoc} */
    @Override
    public PlayerInventory inventory() {
        return new VanillaPlayerInventory(player.getInventory());
    }

    /** {@inheritDoc} */
    @Override
    public int ping() {
        return ((ServerPlayer) player).latency;
    }

    /** {@inheritDoc} */
    @Override
    public void kick(String message) {
        ((ServerPlayer) player).connection.disconnect(Component.nullToEmpty(message));
    }

    /** {@inheritDoc} */
    @Override
    public void setSpawn(Location location, boolean forced) {
        // TODO: Abstract world information
        ResourceKey<Level> dimension =
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(location.world()));
        ((ServerPlayer) player)
                .setRespawnPosition(
                        dimension,
                        new BlockPos((int) location.x(), (int) location.y(), (int) location.z()),
                        0,
                        forced,
                        false);
    }

    /** {@inheritDoc} */
    @Override
    public GameMode gameMode() {
        return GameMode.fromName(((ServerPlayer) player).gameMode.getGameModeForPlayer().getName());
    }

    /** {@inheritDoc} */
    @Override
    public void setGameMode(GameMode gameMode) {
        ((ServerPlayer) player).setGameMode(GameType.byId(gameMode.id()));
    }

    /** {@inheritDoc} */
    @Override
    public void allowFlight(boolean allow) {
        player.getAbilities().mayfly = allow;
    }

    /** {@inheritDoc} */
    @Override
    public boolean canFly() {
        return player.getAbilities().mayfly;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isFlying() {
        return player.getAbilities().flying;
    }

    /** {@inheritDoc} */
    @Override
    public void setFlying(boolean flying) {
        player.getAbilities().flying = flying;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasPermission(int permissionLevel) {
        return player.hasPermissions(permissionLevel);
    }
}
