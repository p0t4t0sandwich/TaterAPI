/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.v1_19.sponge.event.entity;

import dev.neuralnexus.taterapi.entity.Entity;
import dev.neuralnexus.taterapi.event.entity.EntitySpawnEvent;
import dev.neuralnexus.taterapi.world.Location;
import dev.neuralnexus.taterlib.v1_19.sponge.world.SpongeLocation;

import org.spongepowered.api.event.entity.SpawnEntityEvent;

/** Sponge implementation of {@link EntitySpawnEvent}. */
public class SpongeEntitySpawnEvent implements EntitySpawnEvent {
    private final SpawnEntityEvent.Pre event;

    public SpongeEntitySpawnEvent(SpawnEntityEvent.Pre event) {
        this.event = event;
    }

    @Override
    public boolean cancelled() {
        return this.event.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        event.setCancelled(cancelled);
    }

    @Override
    public Entity entity() {
        return (Entity) event.entities().get(0);
    }

    @Override
    public Location location() {
        return new SpongeLocation(event.entities().get(0).serverLocation());
    }
}
