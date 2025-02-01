/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.mixin.v1_16_2.forge.api.minecraft.world.entity;

import dev.neuralnexus.modapi.metadata.Mappings;
import dev.neuralnexus.modapi.metadata.enums.MinecraftVersion;
import dev.neuralnexus.modapi.muxins.annotations.ReqMCVersion;
import dev.neuralnexus.modapi.muxins.annotations.ReqMappings;
import dev.neuralnexus.taterapi.command.CommandSender;
import dev.neuralnexus.taterapi.entity.Entity;
import dev.neuralnexus.taterapi.entity.Identifiable;
import dev.neuralnexus.taterapi.entity.Nameable;
import dev.neuralnexus.taterapi.resource.ResourceKey;
import dev.neuralnexus.taterapi.server.Server;
import dev.neuralnexus.taterapi.world.Location;
import dev.neuralnexus.taterlib.v1_16.vanilla.world.VanillaLocation;
import dev.neuralnexus.taterlib.v1_16.vanilla.world.VanillaServerWorld;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Interface.Remap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;
import java.util.UUID;

@ReqMappings(Mappings.LEGACY_SEARGE)
@ReqMCVersion(min = MinecraftVersion.V16, max = MinecraftVersion.V16_5)
@Mixin(net.minecraft.world.entity.Entity.class)
@Implements({
    @Interface(iface = CommandSender.class, prefix = "cmdSender$", remap = Remap.NONE),
    @Interface(iface = Entity.class, prefix = "entity$", remap = Remap.NONE),
    @Interface(iface = Nameable.class, prefix = "nameable$", remap = Remap.NONE),
    @Interface(iface = Identifiable.class, prefix = "identifiable$", remap = Remap.NONE)
})
public abstract class Entity_API {
    @Shadow
    public abstract void shadow$sendMessage(Component message, UUID uuid);

    @Shadow
    public abstract int shadow$getId();

    @Shadow
    public abstract void shadow$remove();

    @Shadow
    public abstract EntityType<?> shadow$getType();

    @Shadow
    public abstract void shadow$teleportTo(double x, double y, double z);

    @Shadow
    @Nullable public abstract MinecraftServer shadow$getServer();

    @Shadow
    public abstract net.minecraft.world.entity.Entity shadow$changeDimension(
            ServerLevel serverLevel);

    @Shadow
    @Nullable public abstract Component shadow$getCustomName();

    @Shadow
    public abstract void shadow$setCustomName(@Nullable Component name);

    @Shadow
    public abstract UUID shadow$getUUID();

    public void cmdSender$sendMessage(String message) {
        this.shadow$sendMessage(Component.nullToEmpty(message), Util.NIL_UUID);
    }

    public int entity$entityId() {
        return this.shadow$getId();
    }

    public void entity$remove() {
        this.shadow$remove();
    }

    @SuppressWarnings("deprecation")
    public ResourceKey entity$type() {
        return (ResourceKey) Registry.ENTITY_TYPE.getKey(this.shadow$getType());
    }

    public Location entity$location() {
        return new VanillaLocation((net.minecraft.world.entity.Entity) (Object) this);
    }

    @SuppressWarnings({"DataFlowIssue", "resource"})
    public void entity$teleport(Location location) {
        if (!location.world().dimension().equals(((Entity) this).dimension())) {
            if (this.shadow$getServer() == null) return;
            Optional<ServerLevel> serverLevel =
                    ((Server) this.shadow$getServer())
                            .world(location.world().dimension())
                            .map(VanillaServerWorld.class::cast)
                            .map(VanillaServerWorld::world);
            if (!serverLevel.isPresent()) return;
            this.shadow$changeDimension(serverLevel.get());
        }
        this.shadow$teleportTo(location.x(), location.y(), location.z());
    }

    @SuppressWarnings("DataFlowIssue")
    public Optional<String> nameable$customName() {
        if (this.shadow$getCustomName() == null) return Optional.empty();
        return Optional.of(this.shadow$getCustomName().getString());
    }

    public void nameable$setCustomName(String name) {
        this.shadow$setCustomName(Component.nullToEmpty(name));
    }

    public UUID identifiable$uuid() {
        return this.shadow$getUUID();
    }
}
