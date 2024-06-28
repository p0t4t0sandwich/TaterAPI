package dev.neuralnexus.taterlib.v1_17.sponge;

import dev.neuralnexus.taterlib.TaterLib;
import dev.neuralnexus.taterlib.TaterLibPlugin;
import dev.neuralnexus.taterlib.api.TaterAPI;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.api.Platform;
import dev.neuralnexus.taterlib.logger.impl.LoggerAdapter;
import dev.neuralnexus.taterlib.v1_17.sponge.hooks.permissions.SpongePermissionsHook;
import dev.neuralnexus.taterlib.v1_17.sponge.listeners.block.SpongeBlockListener;
import dev.neuralnexus.taterlib.v1_17.sponge.listeners.command.SpongeCommandListener;
import dev.neuralnexus.taterlib.v1_17.sponge.listeners.entity.SpongeEntityListener;
import dev.neuralnexus.taterlib.v1_17.sponge.listeners.player.SpongePlayerListener;
import dev.neuralnexus.taterlib.v1_17.sponge.listeners.server.SpongeServerListener;
import dev.neuralnexus.taterlib.v1_17.sponge.server.SpongeServer;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.plugin.PluginContainer;

public class SpongeTaterLibPlugin implements TaterLibPlugin {
    private PluginContainer container;

    @Override
    public void onInit(Object plugin, Object server, Object logger) {
        container = (PluginContainer) plugin;

        TaterAPIProvider.addHook(new SpongePermissionsHook());
        start(plugin, server, new LoggerAdapter(TaterLib.Constants.PROJECT_ID, logger));
        TaterAPIProvider.api(Platform.SPONGE)
                .ifPresent(api -> api.setServer(() -> new SpongeServer(Sponge.server())));
    }

    @Override
    public void onEnable() {
        if (TaterAPIProvider.isPrimaryPlatform(Platform.SPONGE)) {
            // Register listeners
            EventManager eventManager = Sponge.eventManager();
            eventManager.registerListeners(container, new SpongeBlockListener());
            eventManager.registerListeners(container, new SpongeCommandListener());
            eventManager.registerListeners(container, new SpongeEntityListener());
            eventManager.registerListeners(container, new SpongePlayerListener());
            eventManager.registerListeners(container, new SpongeServerListener());
        }
    }

    @Override
    public void onDisable() {
        stop();
    }
}
