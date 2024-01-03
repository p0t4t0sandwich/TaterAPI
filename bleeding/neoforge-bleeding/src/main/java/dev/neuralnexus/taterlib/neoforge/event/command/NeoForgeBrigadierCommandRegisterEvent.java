package dev.neuralnexus.taterlib.neoforge.event.command;

import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import dev.neuralnexus.taterlib.command.Sender;
import dev.neuralnexus.taterlib.event.command.BrigadierCommandRegisterEvent;
import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.vanilla.command.VanillaSender;
import dev.neuralnexus.taterlib.vanilla.player.VanillaPlayer;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/** NeoForge implementation of {@link BrigadierCommandRegisterEvent}. */
public class NeoForgeBrigadierCommandRegisterEvent
        implements BrigadierCommandRegisterEvent<CommandSourceStack> {
    private final RegisterCommandsEvent event;

    public NeoForgeBrigadierCommandRegisterEvent(RegisterCommandsEvent event) {
        this.event = event;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isDedicated() {
        return event.getCommandSelection() == Commands.CommandSelection.DEDICATED;
    }

    /** {@inheritDoc} */
    @Override
    public CommandDispatcher<CommandSourceStack> getDispatcher() {
        return event.getDispatcher();
    }

    /** {@inheritDoc} */
    @Override
    public void registerCommand(
            LiteralArgumentBuilder<CommandSourceStack> node,
            Object plugin,
            String commandName,
            String... aliases) {
        event.getDispatcher().register(node);
        for (String alias : aliases) {
            event.getDispatcher().register(literal(alias).redirect(node.build()));
        }
    }

    /** {@inheritDoc} */
    @Override
    public Sender getSender(CommandSourceStack source) {
        return new VanillaSender(source);
    }

    /** {@inheritDoc} */
    @Override
    public Player getPlayer(CommandSourceStack source) {
        return new VanillaPlayer(source.getPlayer());
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPlayer(CommandSourceStack source) {
        return source.getEntity() instanceof net.minecraft.world.entity.player.Player;
    }
}