/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.taterlib.v1_15_2.forge.event.player;

import dev.neuralnexus.taterapi.event.player.PlayerLoginEvent;

import net.minecraftforge.event.entity.player.PlayerEvent;

/** Forge implementation of {@link PlayerLoginEvent}. */
public class ForgePlayerLoginEvent extends ForgePlayerEvent implements PlayerLoginEvent {
    private final PlayerEvent.PlayerLoggedInEvent event;
    private String loginMessage = "";

    public ForgePlayerLoginEvent(PlayerEvent.PlayerLoggedInEvent event) {
        super(event);
        this.event = event;
    }

    /** {@inheritDoc} */
    @Override
    public String loginMessage() {
        if (!this.loginMessage.isEmpty()) {
            return this.loginMessage;
        }
        return event.getEntity().getName().getString() + " joined the game";
    }

    /** {@inheritDoc} */
    @Override
    public void setLoginMessage(String message) {
        this.loginMessage = message;
    }
}