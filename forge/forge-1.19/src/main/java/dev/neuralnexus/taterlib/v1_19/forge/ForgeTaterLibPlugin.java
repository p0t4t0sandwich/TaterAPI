package dev.neuralnexus.taterlib.v1_19.forge;

import dev.neuralnexus.taterlib.TaterLib;
import dev.neuralnexus.taterlib.TaterLibPlugin;
import dev.neuralnexus.taterlib.api.TaterAPI;
import dev.neuralnexus.taterlib.api.TaterAPIProvider;
import dev.neuralnexus.taterlib.api.info.ModInfo;
import dev.neuralnexus.taterlib.api.info.ServerType;
import dev.neuralnexus.taterlib.event.api.NetworkEvents;
import dev.neuralnexus.taterlib.utils.forge.modern.FMLAdapters;
import dev.neuralnexus.taterlib.v1_19.forge.event.pluginmessage.ForgeRegisterPluginMessagesEvent;
import dev.neuralnexus.taterlib.v1_19.forge.hooks.permissions.ForgePermissionsHook;
import dev.neuralnexus.taterlib.v1_19.forge.listeners.block.ForgeBlockListener;
import dev.neuralnexus.taterlib.v1_19.forge.listeners.command.ForgeCommandsListener;
import dev.neuralnexus.taterlib.v1_19.forge.listeners.entity.ForgeEntityListener;
import dev.neuralnexus.taterlib.v1_19.forge.listeners.server.ForgeServerListener;
import dev.neuralnexus.taterlib.v1_19.forge.networking.ModMessages;
import dev.neuralnexus.taterlib.v1_19.forge.server.ForgeServer;
import dev.neuralnexus.taterlib.logger.LoggerAdapter;
import dev.neuralnexus.taterlib.v1_19.forge.listeners.player.ForgePlayerListener;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.stream.Collectors;

public class ForgeTaterLibPlugin implements TaterLibPlugin {
    @Override
    public void platformInit(Object plugin, Object server, Object logger) {
        TaterAPIProvider.setPrimaryServerType(ServerType.FORGE);
        TaterAPIProvider.addHook(new ForgePermissionsHook());
        pluginStart(plugin, server, logger, new LoggerAdapter(TaterLib.Constants.PROJECT_ID, logger));
        TaterAPI api = TaterAPIProvider.get(ServerType.FORGE);
        api.setModList(() -> FMLAdapters.adaptModList(ModList.get()));
        api.setServer(() -> new ForgeServer(ServerLifecycleHooks.getCurrentServer()));

        if (TaterAPIProvider.isPrimaryServerType(ServerType.FORGE)) {
            // Register listeners
            MinecraftForge.EVENT_BUS.register(this);
            MinecraftForge.EVENT_BUS.register(new ForgeBlockListener());
            MinecraftForge.EVENT_BUS.register(new ForgeCommandsListener());
            MinecraftForge.EVENT_BUS.register(new ForgeEntityListener());
            MinecraftForge.EVENT_BUS.register(new ForgePlayerListener());
            MinecraftForge.EVENT_BUS.register(new ForgeServerListener());

            // Register plugin channels
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
            modEventBus.addListener(this::commonSetup);
        }
    }

    /**
     * Called when CommonSetupEvent is fired.
     *
     * @param event The event.
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        NetworkEvents.REGISTER_PLUGIN_MESSAGES.invoke(new ForgeRegisterPluginMessagesEvent());
        ModMessages.register();
        ModMessages.clearQueue();
    }

    /**
     * Called when the server is stopping.
     *
     * @param event The event.
     */
    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        pluginStop();
    }
}
