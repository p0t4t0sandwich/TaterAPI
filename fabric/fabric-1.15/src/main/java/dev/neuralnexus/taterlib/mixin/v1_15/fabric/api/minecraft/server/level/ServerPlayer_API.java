/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.mixin.v1_15.fabric.api.minecraft.server.level;

import dev.neuralnexus.conditionalmixins.annotations.ReqMCVersion;
import dev.neuralnexus.conditionalmixins.annotations.ReqMappings;
import dev.neuralnexus.taterapi.Mappings;
import dev.neuralnexus.taterapi.MinecraftVersion;
import dev.neuralnexus.taterapi.entity.player.Connection;
import dev.neuralnexus.taterapi.entity.player.ServerPlayer;
import dev.neuralnexus.taterapi.resource.ResourceKey;
import dev.neuralnexus.taterapi.world.Location;

import io.netty.buffer.Unpooled;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Interface.Remap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@ReqMappings(Mappings.INTERMEDIARY)
@ReqMCVersion(min = MinecraftVersion.V1_15, max = MinecraftVersion.V1_15_2)
@Mixin(net.minecraft.server.level.ServerPlayer.class)
@Implements({
    @Interface(iface = Connection.class, prefix = "connection$", remap = Remap.NONE),
    @Interface(iface = ServerPlayer.class, prefix = "serverPlayer$", remap = Remap.NONE)
})
public abstract class ServerPlayer_API {
    @Shadow public int latency;

    @Shadow public ServerGamePacketListenerImpl connection;

    @Shadow
    public abstract String shadow$getIpAddress();

    @Unique public void taterapi$setRespawnPosition(@Nullable BlockPos position, boolean forced) {
        ((Player) (Object) this).setRespawnPosition(position, forced, false);
    }

    public String connection$ipAddress() {
        return this.shadow$getIpAddress();
    }

    public int connection$ping() {
        return this.latency;
    }

    public void connection$kick(String message) {
        this.connection.disconnect(new TextComponent(message));
    }

    @SuppressWarnings("VulnerableCodeUsages")
    public void connection$sendPacket(ResourceKey channel, byte[] data) {
        ResourceLocation id = (ResourceLocation) channel;
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        byteBuf.writeBytes(data);
        connection.send(new ClientboundCustomPayloadPacket(id, byteBuf));
    }

    public void serverPlayer$setSpawn(Location location, boolean forced) {
        this.taterapi$setRespawnPosition(
                new BlockPos(location.x(), location.y(), location.z()), forced);
    }
}
