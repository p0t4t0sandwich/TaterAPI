package dev.neuralnexus.taterlib.sponge.command;

import dev.neuralnexus.taterlib.command.Sender;

import net.kyori.adventure.text.Component;

import org.spongepowered.api.command.CommandCause;

import java.util.UUID;

/** Sponge implementation of {@link Sender} */
public class SpongeSender implements Sender {
    private final CommandCause sender;

    public SpongeSender(CommandCause sender) {
        this.sender = sender;
    }

    /**
     * Get the sender
     *
     * @return The sender
     */
    public CommandCause getSender() {
        return sender;
    }

    /** {@inheritDoc} */
    @Override
    public UUID getUniqueId() {
        return new UUID(0, 0);
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return "CONSOLE";
    }

    /** {@inheritDoc} */
    @Override
    public void sendMessage(String message) {
        sender.sendMessage(Component.text(message));
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasPermission(int permissionLevel) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }
}
