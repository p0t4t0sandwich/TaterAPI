/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.mixin.v1_18.vanilla.api.minecraft.commands;

import dev.neuralnexus.conditionalmixins.annotations.ReqMCVersion;
import dev.neuralnexus.conditionalmixins.annotations.ReqMappings;
import dev.neuralnexus.taterapi.Mappings;
import dev.neuralnexus.taterapi.MinecraftVersion;
import dev.neuralnexus.taterapi.command.CommandSender;
import dev.neuralnexus.taterapi.entity.Permissible;

import net.minecraft.Util;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Interface.Remap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@ReqMappings(Mappings.MOJMAP)
@ReqMCVersion(min = MinecraftVersion.V1_18, max = MinecraftVersion.V1_18_2)
@Mixin(CommandSourceStack.class)
@Implements({
    @Interface(iface = CommandSender.class, prefix = "cmdSender$", remap = Remap.NONE),
    @Interface(iface = Permissible.class, prefix = "permissible$", remap = Remap.NONE)
})
@SuppressWarnings({"unused", "UnusedMixin"})
public abstract class CommandSourceStack_API {
    // Note: CommandSourceStatck#hasPermission satisfies the Permissible interface
    @Shadow @Final private CommandSource source;

    @Shadow
    public abstract String shadow$getTextName();

    @Shadow
    @Nullable public abstract Entity shadow$getEntity();

    public String cmdSender$name() {
        return this.shadow$getTextName();
    }

    public void cmdSender$sendMessage(String message) {
        this.source.sendMessage(Component.nullToEmpty(message), Util.NIL_UUID);
    }

    @SuppressWarnings("DataFlowIssue")
    public UUID permissible$uuid() {
        if (this.shadow$getEntity() == null) {
            return new UUID(0, 0);
        }
        return this.shadow$getEntity().getUUID();
    }
}
