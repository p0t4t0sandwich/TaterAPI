/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.taterlib.v1_21.vanilla.entity.player;

import dev.neuralnexus.taterapi.entity.player.GameMode;
import dev.neuralnexus.taterapi.entity.player.Player;
import dev.neuralnexus.taterapi.item.inventory.PlayerInventory;
import dev.neuralnexus.taterapi.resource.ResourceKey;
import dev.neuralnexus.taterapi.server.Server;
import dev.neuralnexus.taterapi.world.Location;
import dev.neuralnexus.taterlib.v1_21.vanilla.entity.VanillaLivingEntity;
import dev.neuralnexus.taterlib.v1_21.vanilla.item.inventory.VanillaPlayerInventory;
import dev.neuralnexus.taterlib.v1_21.vanilla.network.VanillaCustomPacketPayload_1_20_6;
import dev.neuralnexus.taterlib.v1_21.vanilla.server.VanillaServer;
import dev.neuralnexus.taterlib.v1_21.vanilla.world.VanillaWorld;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

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

    @Override
    public UUID uuid() {
        return player.getUUID();
    }

    @Override
    public String ipAddress() {
        return ((ServerPlayer) player).getIpAddress();
    }

    @Override
    public String name() {
        return player.getName().getString();
    }

    @Override
    public String displayName() {
        return player.getDisplayName().getString();
    }

    @Override
    public Server server() {
        return VanillaServer.instance();
    }

    @Override
    public void sendMessage(String message) {
        player.displayClientMessage(Component.nullToEmpty(message), false);
    }

    @Override
    public void sendPluginMessage(ResourceKey channel, byte[] data) {
        ((ServerPlayer) player)
                .connection.send(
                        new ClientboundCustomPayloadPacket(
                                new VanillaCustomPacketPayload_1_20_6(channel, data)));
    }

    @Override
    public PlayerInventory inventory() {
        return new VanillaPlayerInventory(player.getInventory());
    }

    @Override
    public int ping() {
        return ((ServerPlayer) player).connection.latency();
    }

    @Override
    public void kick(String message) {
        ((ServerPlayer) player).connection.disconnect(Component.nullToEmpty(message));
    }

    @Override
    public void setSpawn(Location location, boolean forced) {
        ((ServerPlayer) player)
                .setRespawnPosition(
                        ((VanillaWorld) location.world()).world().dimension(),
                        new BlockPos((int) location.x(), (int) location.y(), (int) location.z()),
                        0,
                        forced,
                        false);
    }

    @Override
    public GameMode gameMode() {
        return GameMode.fromName(((ServerPlayer) player).gameMode.getGameModeForPlayer().getName());
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        ((ServerPlayer) player).setGameMode(GameType.byId(gameMode.id()));
    }

    @Override
    public void allowFlight(boolean allow) {
        player.getAbilities().mayfly = allow;
    }

    @Override
    public boolean canFly() {
        return player.getAbilities().mayfly;
    }

    @Override
    public boolean isFlying() {
        return player.getAbilities().flying;
    }

    @Override
    public void setFlying(boolean flying) {
        player.getAbilities().flying = flying;
    }

    @Override
    public boolean hasPermission(int permissionLevel) {
        return player.hasPermissions(permissionLevel);
    }
}
