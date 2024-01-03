package dev.neuralnexus.taterlib.fabric.event.command;

import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import dev.neuralnexus.taterlib.command.Sender;
import dev.neuralnexus.taterlib.event.command.BrigadierCommandRegisterEvent;
import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.vanilla.command.VanillaSender;
import dev.neuralnexus.taterlib.vanilla.player.VanillaPlayer;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

/** Fabric implementation of {@link BrigadierCommandRegisterEvent}. */
public class FabricBrigadierCommandRegisterEvent
        implements BrigadierCommandRegisterEvent<CommandSourceStack> {
    private final CommandDispatcher<CommandSourceStack> dispatcher;
    private final CommandBuildContext registryAccess;
    private final Commands.CommandSelection environment;

    public FabricBrigadierCommandRegisterEvent(
            CommandDispatcher<CommandSourceStack> dispatcher,
            CommandBuildContext registryAccess,
            Commands.CommandSelection environment) {
        this.dispatcher = dispatcher;
        this.registryAccess = registryAccess;
        this.environment = environment;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isDedicated() {
        return environment == Commands.CommandSelection.DEDICATED;
    }

    /** {@inheritDoc} */
    @Override
    public CommandDispatcher<CommandSourceStack> getDispatcher() {
        return dispatcher;
    }

    /** {@inheritDoc} */
    @Override
    public void registerCommand(
            LiteralArgumentBuilder<CommandSourceStack> node,
            Object plugin,
            String commandName,
            String... aliases) {
        dispatcher.register(node);
        for (String alias : aliases) {
            dispatcher.register(literal(alias).redirect(node.build()));
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