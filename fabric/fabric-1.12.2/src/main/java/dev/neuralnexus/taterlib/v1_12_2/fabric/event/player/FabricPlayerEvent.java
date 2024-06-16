package dev.neuralnexus.taterlib.v1_12_2.fabric.event.player;

import dev.neuralnexus.taterlib.event.player.PlayerEvent;
import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.v1_12_2.fabric.player.FabricPlayer;

import net.minecraft.entity.player.PlayerEntity;

/** Fabric implementation of {@link PlayerEvent}. */
public class FabricPlayerEvent implements PlayerEvent {
    private final PlayerEntity player;

    public FabricPlayerEvent(PlayerEntity player) {
        this.player = player;
    }

    /** {@inheritDoc} */
    @Override
    public Player player() {
        return new FabricPlayer(this.player);
    }
}