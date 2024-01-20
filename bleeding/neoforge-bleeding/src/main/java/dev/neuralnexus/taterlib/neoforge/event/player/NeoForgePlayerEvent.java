package dev.neuralnexus.taterlib.neoforge.event.player;

import dev.neuralnexus.taterlib.event.player.PlayerEvent;
import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.vanilla.player.VanillaPlayer;

/** NeoForge implementation of {@link PlayerEvent}. */
public class NeoForgePlayerEvent implements PlayerEvent {
    private final net.neoforged.neoforge.event.entity.player.PlayerEvent event;

    public NeoForgePlayerEvent(net.neoforged.neoforge.event.entity.player.PlayerEvent event) {
        this.event = event;
    }

    /** {@inheritDoc} */
    @Override
    public Player getPlayer() {
        return new VanillaPlayer(event.getEntity());
    }
}
