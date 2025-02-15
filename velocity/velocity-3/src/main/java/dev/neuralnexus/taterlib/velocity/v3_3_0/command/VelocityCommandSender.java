/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.velocity.v3_3_0.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;

import dev.neuralnexus.taterapi.Wrapped;
import dev.neuralnexus.taterapi.command.CommandSender;

import net.kyori.adventure.text.Component;

import java.util.UUID;

/** Velocity implementation of {@link CommandSender} */
public class VelocityCommandSender implements CommandSender, Wrapped<CommandSource> {
    private final CommandSource sender;

    public VelocityCommandSender(CommandSource sender) {
        this.sender = sender;
    }

    @Override
    public CommandSource unwrap() {
        return sender;
    }

    @Override
    public UUID uuid() {
        if (this.sender instanceof Player player) {
            return player.getUniqueId();
        }
        return new UUID(0, 0);
    }

    @Override
    public String name() {
        return "CONSOLE";
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(Component.text(message));
    }
}
