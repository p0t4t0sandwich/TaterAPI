package dev.neuralnexus.taterlib.fabric.mixin.listeners.player;

import dev.neuralnexus.taterlib.fabric.event.api.FabricPlayerEvents;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Mixin for the player message listener. */
@Mixin(ServerPlayNetworkHandler.class)
public abstract class PlayerMessageMixin_1_10_2 {
    @Shadow public ServerPlayerEntity player;

    /**
     * Called when a player sends a message.
     *
     * @param packet The packet.
     * @param ci The callback info.
     */
    @Inject(method = "onChatMessage", at = @At("HEAD"), cancellable = true)
    public void onPlayerMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        if (packet.getChatMessage().startsWith("/")) return;
        FabricPlayerEvents.MESSAGE.invoker().onPlayerMessage(player, packet.getChatMessage(), ci);
    }
}
