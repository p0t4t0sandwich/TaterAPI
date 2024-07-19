/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.taterlib.v1_15.vanilla.network;

import dev.neuralnexus.taterapi.network.CustomPayload;
import dev.neuralnexus.taterapi.resource.ResourceKey;
import dev.neuralnexus.taterlib.v1_15.vanilla.mixin.bridge.network.protocol.game.ServerboundCustomPayloadPacketBridge;

import io.netty.buffer.Unpooled;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;

import java.io.IOException;

/**
 * A custom wrapper for {@link ServerboundCustomPayloadPacket} that implements {@link
 * CustomPayload}.
 */
public class CustomPayloadPacket implements CustomPayload, ServerboundCustomPayloadPacketBridge {
    private final ResourceKey channel;
    private final byte[] data;

    public CustomPayloadPacket(ServerboundCustomPayloadPacket packet) {
        this.channel = (ResourceKey) (Object) bridge$getIdentifier(packet);
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        try {
            packet.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.data = buf.array();
    }

    @Override
    public ResourceKey channel() {
        return channel;
    }

    @Override
    public byte[] data() {
        return data;
    }
}