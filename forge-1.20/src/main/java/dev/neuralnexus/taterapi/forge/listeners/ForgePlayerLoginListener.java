package dev.neuralnexus.taterapi.forge.listeners;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static dev.neuralnexus.taterapi.common.Utils.runTaskAsync;

public class ForgePlayerLoginListener {
    @SubscribeEvent
    public void onPlayerConnect(PlayerEvent.PlayerLoggedInEvent event) {
        runTaskAsync(() -> {
            try {
                // Do stuff
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
