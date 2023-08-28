package dev.neuralnexus.taterlib.fabric.abstractions.player;

import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayerInventory;
import dev.neuralnexus.taterlib.common.hooks.LuckPermsHook;
import me.lucko.fabric.api.permissions.v0.Options;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

/**
 * Abstracts a Fabric player to an AbstractPlayer.
 */
public class FabricPlayer implements AbstractPlayer {
    private final PlayerEntity player;
    private String serverName;

    /**
     * Constructor.
     * @param player The Fabric player.
     */
    public FabricPlayer(PlayerEntity player) {
        this.player = player;
        this.serverName = "local";
    }

    /**
     * Constructor.
     * @param player The Fabric player.
     * @param serverName The server name.
     */
    public FabricPlayer(PlayerEntity player, String serverName) {
        this.player = player;
        this.serverName = serverName;
    }

    /**
     * @inheritDoc
     */
    @Override
    public java.util.UUID getUUID() {
        return player.getUuid();
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
        player.sendMessage(Text.of(message), false);
    }

    /**
     * @inheritDoc
     */
    @Override
    public AbstractPlayerInventory getInventory() {
        return new FabricPlayerInventory(player.getInventory());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void kickPlayer(String message) {
        ((ServerPlayerEntity) player).networkHandler.disconnect(Text.of(message));
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getPrefix() {
        if (!LuckPermsHook.isHooked()) return "";
        return Options.get(player, "prefix", "");
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getSuffix() {
        if (!LuckPermsHook.isHooked()) return "";
        return Options.get(player, "suffix", "");
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasPermission(String permission) {
        if (!LuckPermsHook.isHooked()) return player.hasPermissionLevel(4);
        return Permissions.check(player, permission, 4);
    }
}