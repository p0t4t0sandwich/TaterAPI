package dev.neuralnexus.taterlib.forge.player;

import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.player.Player;
import dev.neuralnexus.taterlib.common.inventory.PlayerInventory;
import dev.neuralnexus.taterlib.common.utils.Position;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import dev.neuralnexus.taterlib.forge.inventory.ForgePlayerInventory;
import dev.neuralnexus.taterlib.forge.util.ForgeConversions;
import dev.neuralnexus.taterlib.forge.networking.ModMessages;
import dev.neuralnexus.taterlib.forge.networking.packet.ForgeMessagePacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.UUID;

/**
 * Abstracts a Forge player to a Player.
 */
public class ForgePlayer implements Player {
    private final PlayerEntity player;
    private String serverName;

    /**
     * Constructor.
     * @param player The Forge player.
     */
    public ForgePlayer(PlayerEntity player) {
        this.player = player;
        this.serverName = "local";
    }

    /**
     * Constructor.
     * @param player The Forge player.
     * @param serverName The server name.
     */
    public ForgePlayer(PlayerEntity player, String serverName) {
        this.player = player;
        this.serverName = serverName;
    }

    /**
     * Gets the Forge player
     * @return The Forge player
     */
    public PlayerEntity getPlayer() {
        return player;
    }

    /**
     * @inheritDoc
     */
    @Override
    public UUID getUUID() {
        return player.getUniqueID();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName() {
        return player.getName().getString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getDisplayName() {
        return player.getDisplayName().getString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Position getPosition() {
        return ForgeConversions.positionFromVector(player.getPositionVector());
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getServerName() {
        return serverName;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setServerName(String server) {
        this.serverName = server;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sendMessage(String message) {
        player.sendMessage(new StringTextComponent(message));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sendPluginMessage(String channel, byte[] data) {
        ModMessages.sendPluginMessage(new ForgeMessagePacket(new String(data)), channel, (ServerPlayerEntity) player);
    }

    /**
     * @inheritDoc
     */
    @Override
    public PlayerInventory getInventory() {
        return new ForgePlayerInventory(player.inventory);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void kickPlayer(String message) {
        ((ServerPlayerEntity) player).connection.disconnect(new StringTextComponent(message));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setSpawn(Position position) {
        player.setBedPosition(ForgeConversions.locationFromPosition(position));
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasPermission(String permission) {
        if (!TaterLib.isHooked("luckperms")) return player.hasPermissionLevel(4);
        LuckPermsHook luckPermsHook = LuckPermsHook.getInstance();
        return luckPermsHook.playerHasPermission(getUUID(), permission);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasPermission(int permissionLevel) {
        return player.hasPermissionLevel(permissionLevel);
    }
}