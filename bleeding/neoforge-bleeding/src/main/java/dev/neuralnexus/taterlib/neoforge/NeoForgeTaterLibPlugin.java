package dev.neuralnexus.taterlib.neoforge;

import dev.neuralnexus.taterlib.TaterLib;
import dev.neuralnexus.taterlib.TaterLibPlugin;
import dev.neuralnexus.taterlib.api.TaterAPI;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.api.info.ServerType;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;
import dev.neuralnexus.taterlib.neoforge.hooks.permissions.NeoForgePermissionsHook;
import dev.neuralnexus.taterlib.neoforge.listeners.block.NeoForgeBlockListener;
import dev.neuralnexus.taterlib.neoforge.listeners.command.NeoForgeCommandsListener;
import dev.neuralnexus.taterlib.neoforge.listeners.entity.NeoForgeEntityListener;
import dev.neuralnexus.taterlib.neoforge.listeners.player.NeoForgePlayerListener;
import dev.neuralnexus.taterlib.neoforge.listeners.server.NeoForgeServerListener;
import dev.neuralnexus.taterlib.vanilla.server.VanillaServer;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class NeoForgeTaterLibPlugin implements TaterLibPlugin {
    @Override
    public void platformInit(Object plugin, Object logger) {
        TaterAPIProvider.register(FMLLoader.versionInfo().mcVersion());
        TaterAPIProvider.addHook(new NeoForgePermissionsHook());
        pluginStart(plugin, new LoggerAdapter(TaterLib.Constants.PROJECT_ID, logger));
        TaterAPI api = TaterAPIProvider.get(ServerType.NEOFORGE);
        api.setIsModLoaded(ModList.get()::isLoaded);
        api.setServer(() -> new VanillaServer(ServerLifecycleHooks.getCurrentServer()));

        // Register listeners
        NeoForge.EVENT_BUS.register(new NeoForgeBlockListener());
        NeoForge.EVENT_BUS.register(new NeoForgeCommandsListener());
        NeoForge.EVENT_BUS.register(new NeoForgeEntityListener());
        NeoForge.EVENT_BUS.register(new NeoForgePlayerListener());
        NeoForge.EVENT_BUS.register(new NeoForgeServerListener());
    }

    @Override
    public void platformDisable() {
        pluginStop();
    }
}