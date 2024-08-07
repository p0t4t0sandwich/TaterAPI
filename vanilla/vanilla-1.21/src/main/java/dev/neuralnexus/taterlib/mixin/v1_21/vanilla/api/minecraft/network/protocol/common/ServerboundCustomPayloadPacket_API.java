/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.mixin.v1_21.vanilla.api.minecraft.network.protocol.common;

import dev.neuralnexus.conditionalmixins.annotations.ReqMCVersion;
import dev.neuralnexus.conditionalmixins.annotations.ReqMappings;
import dev.neuralnexus.taterapi.Mappings;
import dev.neuralnexus.taterapi.MinecraftVersion;
import dev.neuralnexus.taterapi.network.CustomPayloadPacket;
import dev.neuralnexus.taterapi.resource.ResourceKey;

import io.netty.buffer.Unpooled;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Interface.Remap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@ReqMappings(Mappings.MOJMAP)
@ReqMCVersion(min = MinecraftVersion.V1_21)
@Mixin(ServerboundCustomPayloadPacket.class)
@Implements(@Interface(iface = CustomPayloadPacket.class, prefix = "packet$", remap = Remap.NONE))
public abstract class ServerboundCustomPayloadPacket_API {
    @Shadow
    public abstract CustomPacketPayload shadow$payload();

    @SuppressWarnings("DataFlowIssue")
    public ResourceKey packet$channel() {
        return (ResourceKey) (Object) this.shadow$payload().type().id();
    }

    public byte[] packet$data() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        ServerboundCustomPayloadPacket.STREAM_CODEC.encode(
                buf, (ServerboundCustomPayloadPacket) (Object) this);
        return buf.array();
    }
}