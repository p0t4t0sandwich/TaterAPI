package dev.neuralnexus.taterlib.v1_18.forge.listeners.block;

import dev.neuralnexus.taterlib.event.api.BlockEvents;
import dev.neuralnexus.taterlib.forge.utils.modern.event.ForgeCancellableEventWrapper;
import dev.neuralnexus.taterlib.v1_18.vanilla.event.block.VanillaPlayerBlockBreakEvent;

import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/** Listens for entity events. */
public class ForgeBlockListener {
    /**
     * Called when an entity is damaged.
     *
     * @param event The entity damage event
     */
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockEvents.PLAYER_BLOCK_BREAK.invoke(
                new VanillaPlayerBlockBreakEvent(
                        event.getPlayer().getCommandSenderWorld(),
                        event.getPlayer(),
                        event.getPos(),
                        event.getState(),
                        new ForgeCancellableEventWrapper(event)));
    }
}
