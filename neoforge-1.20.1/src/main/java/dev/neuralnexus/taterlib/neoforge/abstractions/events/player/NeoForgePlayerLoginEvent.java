package dev.neuralnexus.taterlib.neoforge.abstractions.events.player;

import dev.neuralnexus.taterlib.common.abstractions.events.player.AbstractPlayerLoginEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Forge implementation of {@link AbstractPlayerLoginEvent}.
 */
public class NeoForgePlayerLoginEvent extends NeoForgePlayerEvent implements AbstractPlayerLoginEvent {
    private final PlayerEvent.PlayerLoggedInEvent event;
    private String loginMessage = "";

    public NeoForgePlayerLoginEvent(PlayerEvent.PlayerLoggedInEvent event) {
        super(event);
        this.event = event;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getLoginMessage() {
        if (!this.loginMessage.isEmpty()) {
            return this.loginMessage;
        }
        return event.getEntity().getName().getString() + " joined the game";
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setLoginMessage(String message) {
        this.loginMessage = message;
    }
}
