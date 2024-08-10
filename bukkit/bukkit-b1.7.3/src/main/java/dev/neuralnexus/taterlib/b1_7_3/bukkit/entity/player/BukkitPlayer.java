/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.b1_7_3.bukkit.entity.player;

import dev.neuralnexus.taterapi.entity.player.GameMode;
import dev.neuralnexus.taterapi.entity.player.Player;
import dev.neuralnexus.taterapi.entity.player.ServerPlayer;
import dev.neuralnexus.taterapi.exceptions.VersionFeatureNotSupportedException;
import dev.neuralnexus.taterapi.item.inventory.PlayerInventory;
import dev.neuralnexus.taterapi.resource.ResourceKey;
import dev.neuralnexus.taterapi.world.Location;
import dev.neuralnexus.taterlib.b1_7_3.bukkit.entity.BukkitLivingEntity;
import dev.neuralnexus.taterlib.b1_7_3.bukkit.item.inventory.BukkitPlayerInventory;

import org.bukkit.plugin.Plugin;

import java.util.UUID;

/** Bukkit implementation of {@link Player}. */
public class BukkitPlayer extends BukkitLivingEntity implements Player, ServerPlayer {
    private final org.bukkit.entity.Player player;

    /**
     * Constructor.
     *
     * @param player The Bukkit player.
     */
    public BukkitPlayer(org.bukkit.entity.Player player) {
        super(player);
        this.player = player;
    }

    /**
     * Gets the Bukkit player
     *
     * @return The Bukkit player
     */
    public org.bukkit.entity.Player player() {
        return player;
    }

    @Override
    public UUID uuid() {
        return player.getUniqueId();
    }

    @Override
    public String ipAddress() {
        return player.getAddress().getAddress().getHostAddress();
    }

    @Override
    public String name() {
        return player.getName();
    }

    @Override
    public String displayName() {
        return player.getDisplayName();
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendPacket(ResourceKey channel, byte[] data) {
        // TODO: Create some different way to send plugin messages
        throw new VersionFeatureNotSupportedException();
        //        player.sendPluginMessage((Plugin) Loader.instance().plugin(), channel.asString(),
        // data);
    }

    public void sendPluginMessage(Plugin plugin, String channel, byte[] data) {
        throw new VersionFeatureNotSupportedException();
        //        player.sendPluginMessage((Plugin) Loader.instance().plugin(), channel.asString(),
        // data);
    }

    @Override
    public PlayerInventory inventory() {
        return new BukkitPlayerInventory(player.getInventory());
    }

    @Override
    public int ping() {
        // TODO: Find the field that stores the ping
        //        ((CraftPlayer) player).getHandle().netServerHandler.networkManager.f;
        return -1;
    }

    @Override
    public void kick(String reason) {
        player.kickPlayer(reason);
    }

    @Override
    public void setSpawn(Location location, boolean forced) {
        // TODO: Write a module to set bed spawns/respawn points
        throw new VersionFeatureNotSupportedException();
        //        player.setBedSpawnLocation(BukkitConversions.locationFromPosition(position),
        // forced);
    }

    @Override
    public void allowFlight(boolean allow) {
        // TODO: Write a module to allow flight
        throw new VersionFeatureNotSupportedException();
    }

    @Override
    public boolean canFly() {
        throw new VersionFeatureNotSupportedException();
    }

    @Override
    public boolean isFlying() {
        throw new VersionFeatureNotSupportedException();
    }

    @Override
    public void setFlying(boolean flying) {
        throw new VersionFeatureNotSupportedException();
    }

    @Override
    public GameMode gameMode() {
        return GameMode.fromName(player.getGameMode().name());
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        player.setGameMode(org.bukkit.GameMode.valueOf(gameMode.name()));
    }
}
