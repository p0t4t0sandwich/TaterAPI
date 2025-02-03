/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.v1_7_10.forge.entity;

import dev.neuralnexus.taterapi.Wrapped;
import dev.neuralnexus.taterapi.entity.Entity;
import dev.neuralnexus.taterapi.exceptions.VersionFeatureNotSupportedException;
import dev.neuralnexus.taterapi.resource.ResourceKey;
import dev.neuralnexus.taterapi.server.Server;
import dev.neuralnexus.taterapi.world.Location;
import dev.neuralnexus.taterlib.v1_7_10.forge.world.ForgeLocation;
import dev.neuralnexus.taterlib.v1_7_10.forge.world.ForgeServerWorld;

import net.minecraft.world.WorldServer;

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
        // TODO: Find entity registry
        return ResourceKey.of(
                this.entity.getCommandSenderName().split("entity\\.")[1].replace(".", ":"));
    }

    @Override
    public Optional<String> customName() {
        if (this.entity.getFormattedCommandSenderName() == null) return Optional.empty();
        return Optional.of(this.entity.getFormattedCommandSenderName().getFormattedText());
    }

    @Override
    public void setCustomName(String name) {
        // TODO: Implement NAME TAGS SUPPORT
        throw new VersionFeatureNotSupportedException();
    }

    @Override
    public Location location() {
        return new ForgeLocation(this.entity);
    }

    @Override
    public ResourceKey biome() {
        // TODO: Find biome registry
        return ResourceKey.of(
                this.entity.worldObj.provider.getBiomeGenForCoords(
                                (int) this.entity.posX, (int) this.entity.posZ)
                        .biomeName);
    }

    @Override
    public void teleport(Location location) {
        if (!location.world().dimension().equals(dimension())) {
            Optional<WorldServer> serverLevel =
                    ((Server) ((WorldServer) this.entity.worldObj).func_73046_m())
                            .world(location.world().dimension())
                            .map(ForgeServerWorld.class::cast)
                            .map(ForgeServerWorld::unwrap);
            if (!serverLevel.isPresent()) return;
            this.entity.travelToDimension(serverLevel.get().provider.dimensionId);
        }
        this.entity.setPosition(location.x(), location.y(), location.z());
    }
}
