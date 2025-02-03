/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.v1_11_2.forge.entity;

import dev.neuralnexus.taterapi.Wrapped;
import dev.neuralnexus.taterapi.entity.Entity;
import dev.neuralnexus.taterapi.resource.ResourceKey;
import dev.neuralnexus.taterapi.server.Server;
import dev.neuralnexus.taterapi.world.Location;
import dev.neuralnexus.taterlib.v1_11_2.forge.world.ForgeLocation;
import dev.neuralnexus.taterlib.v1_11_2.forge.world.ForgeServerWorld;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.*;

import java.util.Optional;
import java.util.UUID;

/** Forge implementation of {@link Entity}. */
public class ForgeEntity implements Entity, Wrapped<net.minecraft.entity.Entity> {
    private final net.minecraft.entity.Entity entity;

    /**
     * Constructor.
     *
     * @param entity The Forge entity.
     */
    public ForgeEntity(net.minecraft.entity.Entity entity) {
        this.entity = entity;
    }

    @Override
    public net.minecraft.entity.Entity unwrap() {
        return this.entity;
    }

    @Override
    public UUID uuid() {
        return this.entity.getUniqueID();
    }

    @Override
    public int entityId() {
        return this.entity.getEntityId();
    }

    @Override
    public void remove() {
        this.entity.setDead();
    }

    @Override
    public ResourceKey type() {
        return (ResourceKey)
                GameRegistry.findRegistry(EntityEntry.class)
                        .getKey(EntityRegistry.getEntry(this.entity.getClass()));
    }

    @Override
    public Optional<String> customName() {
        if (!this.entity.hasCustomName()) return Optional.empty();
        return Optional.of(this.entity.getCustomNameTag());
    }

    @Override
    public void setCustomName(String name) {
        this.entity.setCustomNameTag(name);
    }

    @Override
    public Location location() {
        return new ForgeLocation(this.entity);
    }

    @Override
    public ResourceKey biome() {
        return (ResourceKey)
                GameRegistry.findRegistry(Biome.class)
                        .getKey(this.entity.world.getBiome(this.entity.getPosition()));
    }

    @Override
    public void teleport(Location location) {
        if (!location.world().dimension().equals(dimension())) {
            if (this.entity.getServer() != null) return;
            Optional<WorldServer> serverLevel =
                    ((Server) this.entity.getServer())
                            .world(location.world().dimension())
                            .map(ForgeServerWorld.class::cast)
                            .map(ForgeServerWorld::unwrap);
            if (!serverLevel.isPresent()) return;
            this.entity.changeDimension(serverLevel.get().provider.getDimensionType().getId());
        }
        ((EntityLiving) this.entity).attemptTeleport(location.x(), location.y(), location.z());
    }

    @Override
    public void sendMessage(String message) {
        this.entity.sendMessage(new TextComponentString(message));
    }
}
