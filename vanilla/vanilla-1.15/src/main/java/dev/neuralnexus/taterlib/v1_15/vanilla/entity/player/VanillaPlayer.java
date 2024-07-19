/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.taterlib.v1_15.vanilla.entity.player;

import dev.neuralnexus.taterapi.entity.player.GameMode;
import dev.neuralnexus.taterapi.entity.player.Player;
import dev.neuralnexus.taterapi.entity.player.ServerPlayer;
import dev.neuralnexus.taterapi.item.inventory.PlayerInventory;
import dev.neuralnexus.taterapi.resource.ResourceKey;
import dev.neuralnexus.taterapi.server.Server;
import dev.neuralnexus.taterapi.world.Location;
import dev.neuralnexus.taterlib.v1_15.vanilla.entity.VanillaLivingEntity;
import dev.neuralnexus.taterlib.v1_15.vanilla.item.inventory.VanillaPlayerInventory;
import dev.neuralnexus.taterlib.v1_15.vanilla.server.VanillaServer;

import io.netty.buffer.Unpooled;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameType;

import java.util.UUID;

/** Vanilla implementation of {@link Player}. */
public class VanillaPlayer extends VanillaLivingEntity implements Player, ServerPlayer {
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
        return ((net.minecraft.server.level.ServerPlayer) player).getIpAddress();
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
        player.displayClientMessage(new TextComponent(message), false);
    }

    @Override
    public void sendPluginMessage(ResourceKey channel, byte[] data) {
        ResourceLocation id = (ResourceLocation) (Object) channel;
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        byteBuf.writeBytes(data);
        ((net.minecraft.server.level.ServerPlayer) player)
                .connection.send(new ClientboundCustomPayloadPacket(id, byteBuf));
    }

    @Override
    public PlayerInventory inventory() {
        return new VanillaPlayerInventory(player.inventory);
    }

    @Override
    public int ping() {
        return ((net.minecraft.server.level.ServerPlayer) player).latency;
    }

    @Override
    public void kick(String message) {
        ((net.minecraft.server.level.ServerPlayer) player)
                .connection.disconnect(new TextComponent(message));
    }

    @Override
    public void setSpawn(Location location, boolean forced) {
        player.setRespawnPosition(
                new BlockPos(location.x(), location.y(), location.z()), forced, false);
    }

    @Override
    public void allowFlight(boolean allow) {
        player.abilities.mayfly = allow;
    }

    @Override
    public boolean canFly() {
        return player.abilities.mayfly;
    }

    @Override
    public boolean isFlying() {
        return player.abilities.flying;
    }

    @Override
    public void setFlying(boolean flying) {
        player.abilities.flying = flying;
    }

    @Override
    public GameMode gameMode() {
        return GameMode.fromName(
                ((net.minecraft.server.level.ServerPlayer) player)
                        .gameMode
                        .getGameModeForPlayer()
                        .getName());
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        ((net.minecraft.server.level.ServerPlayer) player)
                .setGameMode(GameType.byId(gameMode.id()));
    }

    @Override
    public boolean hasPermission(int permissionLevel) {
        return player.hasPermissions(permissionLevel);
    }
}