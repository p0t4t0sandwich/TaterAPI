/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.mixin.v1_17.fabric.listeners.network;

import dev.neuralnexus.conditionalmixins.annotations.ReqMCVersion;
import dev.neuralnexus.conditionalmixins.annotations.ReqPlatform;
import dev.neuralnexus.taterapi.MinecraftVersion;
import dev.neuralnexus.taterapi.Platform;
import dev.neuralnexus.taterapi.event.api.NetworkEvents;
import dev.neuralnexus.taterapi.event.network.impl.PluginMessageEventImpl;
import dev.neuralnexus.taterapi.network.CustomPayload;
import dev.neuralnexus.taterlib.v1_17.vanilla.entity.player.VanillaPlayer;
import dev.neuralnexus.taterlib.v1_17.vanilla.network.CustomPayloadPacket;

import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Mixin for the plugin messages listener. */
@ReqPlatform(Platform.FABRIC)
@ReqMCVersion(min = MinecraftVersion.V1_17, max = MinecraftVersion.V1_17_1)
@Mixin(ServerGamePacketListenerImpl.class)
public abstract class CustomPayloadMixin {
    @Shadow
    public abstract ServerPlayer getPlayer();

    /**
     * Called when a custom payload packet is received. (often used for plugin messages)
     *
     * @param packet The packet.
     * @param ci The callback info.
     */
    @Inject(method = "handleCustomPayload", at = @At("HEAD"))
    public void onPluginMessage(ServerboundCustomPayloadPacket packet, CallbackInfo ci) {
        CustomPayload wrapper = new CustomPayloadPacket(packet);
        NetworkEvents.PLUGIN_MESSAGE.invoke(new PluginMessageEventImpl(wrapper));
        if (getPlayer() == null) return;
        NetworkEvents.PLAYER_PLUGIN_MESSAGE.invoke(
                new PluginMessageEventImpl.Player(wrapper, new VanillaPlayer(getPlayer())));
    }
}
