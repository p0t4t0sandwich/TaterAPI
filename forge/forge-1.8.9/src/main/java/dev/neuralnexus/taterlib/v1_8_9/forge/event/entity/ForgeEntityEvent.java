package dev.neuralnexus.taterlib.v1_8_9.forge.event.entity;

import dev.neuralnexus.taterlib.entity.Entity;
import dev.neuralnexus.taterlib.event.entity.EntityEvent;
import dev.neuralnexus.taterlib.v1_8_9.forge.entity.ForgeEntity;
import dev.neuralnexus.taterlib.v1_8_9.forge.player.ForgePlayer;
import net.minecraft.entity.player.EntityPlayer;

/** Forge implementation of {@link EntityEvent}. */
public class ForgeEntityEvent implements EntityEvent {
    private final net.minecraftforge.event.entity.EntityEvent event;

    public ForgeEntityEvent(net.minecraftforge.event.entity.EntityEvent event) {
        this.event = event;
    }

    /** {@inheritDoc} */
    @Override
    public Entity entity() {
        if (event.entity instanceof EntityPlayer) {
            return new ForgePlayer((EntityPlayer) event.entity);
        } else {
            return new ForgeEntity(event.entity);
        }
    }
}