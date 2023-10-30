package dev.neuralnexus.taterlib.neoforge.listeners.player;

import dev.neuralnexus.taterlib.common.event.player.PlayerEvents;
import dev.neuralnexus.taterlib.neoforge.abstractions.events.player.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Listens for player events.
 */
public class NeoForgePlayerListener {
    /**
     * Called when a player finishes an advancement.
     * @param event The advancement event
     */
    @SubscribeEvent
    public void onPlayerAdvancementFinished(AdvancementEvent.AdvancementEarnEvent event) {
        PlayerEvents.ADVANCEMENT_FINISHED.invoke(new NeoForgePlayerAdvancementEvent.NeoForgePlayerAdvancementFinishedEvent(event));
    }

    /**
     * Called when a player progresses in an advancement.
     * @param event The advancement progress event
     */
    @SubscribeEvent
    public void onPlayerAdvancementProgress(AdvancementEvent.AdvancementProgressEvent event) {
        PlayerEvents.ADVANCEMENT_PROGRESS.invoke(new NeoForgePlayerAdvancementEvent.NeoForgePlayerAdvancementProgressEvent(event));
    }

    /**
     * Called when a player dies.
     * @param event The player death event
     */
    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            PlayerEvents.DEATH.invoke(new NeoForgePlayerDeathEvent(event));
        }
    }

    /**
     * Called when a player logs in.
     * @param event The player login event
     */
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEvents.LOGIN.invoke(new NeoForgePlayerLoginEvent(event));
    }

    /**
     * Called when a player logs out.
     * @param event The player logout event
     */
    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        PlayerEvents.LOGOUT.invoke(new NeoForgePlayerLogoutEvent(event));
    }

    /**
     * Called when a player sends a message, and sends it to the message relay.
     * @param event The player message event
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    void onPlayerMessage(ServerChatEvent event) {
        PlayerEvents.MESSAGE.invoke(new NeoForgePlayerMessageEvent(event));
    }

    /**
     * Called when a player respawns.
     * @param event The player respawn event
     */
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        PlayerEvents.RESPAWN.invoke(new NeoForgePlayerRespawnEvent(event));
    }
}
