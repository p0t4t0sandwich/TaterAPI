/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.v1_13_2.forge;

import dev.neuralnexus.taterapi.Platform;
import dev.neuralnexus.taterapi.TaterAPIProvider;
import dev.neuralnexus.taterapi.event.api.NetworkEvents;
import dev.neuralnexus.taterapi.resource.ResourceKey;
import dev.neuralnexus.taterlib.TaterLibPlugin;
import dev.neuralnexus.taterlib.v1_13_2.forge.event.pluginmessage.ForgeRegisterPacketChannelsEvent;
import dev.neuralnexus.taterlib.v1_13_2.forge.hooks.permissions.ForgePermissionsHook;
import dev.neuralnexus.taterlib.v1_13_2.forge.listeners.block.ForgeBlockListener;
import dev.neuralnexus.taterlib.v1_13_2.forge.listeners.command.ForgeCommandsListener;
import dev.neuralnexus.taterlib.v1_13_2.forge.listeners.entity.ForgeEntityListener;
import dev.neuralnexus.taterlib.v1_13_2.forge.listeners.player.ForgePlayerListener;
import dev.neuralnexus.taterlib.v1_13_2.forge.listeners.server.ForgeServerListener;
import dev.neuralnexus.taterlib.v1_13_2.forge.networking.ModMessages;
import dev.neuralnexus.taterlib.v1_13_2.forge.resource.ForgeResourceKey;
import dev.neuralnexus.taterlib.v1_13_2.forge.server.ForgeServer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@SuppressWarnings("unused")
public class ForgeTaterLibPlugin implements TaterLibPlugin {
    @Override
    public void onInit() {
        TaterAPIProvider.registerBuilder(ResourceKey.Builder.class, ForgeResourceKey.Builder::new);
        TaterAPIProvider.registerFactory(ResourceKey.Factory.class, ForgeResourceKey.Factory::new);
        TaterAPIProvider.addHook(new ForgePermissionsHook());
        start();
        TaterAPIProvider.api(Platform.FORGE)
                .ifPresent(
                        api ->
                                api.setServer(
                                        () ->
                                                new ForgeServer(
                                                        ServerLifecycleHooks.getCurrentServer())));

        if (TaterAPIProvider.isPrimaryPlatform(Platform.FORGE)) {
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
        NetworkEvents.REGISTER_PLUGIN_MESSAGES.invoke(new ForgeRegisterPacketChannelsEvent());
        ModMessages.register();
        ModMessages.clearQueue();
    }

    /**
     * Called when the server is stopping.
     *
     * @param event The event.
     */
    @SubscribeEvent
    public void onServerStopped(FMLServerStoppedEvent event) {
        stop();
    }
}
