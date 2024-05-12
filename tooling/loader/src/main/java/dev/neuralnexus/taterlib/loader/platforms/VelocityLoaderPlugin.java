package dev.neuralnexus.taterlib.loader.platforms;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;

import dev.neuralnexus.taterlib.loader.TaterLibLoader;
import dev.neuralnexus.taterlib.plugin.Loader;

import org.slf4j.Logger;

/** Velocity entry point. */
//@Plugin(
//        id = TaterLib.Constants.PROJECT_ID,
//        name = TaterLib.Constants.PROJECT_NAME,
//        version = TaterLib.Constants.PROJECT_VERSION,
//        authors = TaterLib.Constants.PROJECT_AUTHORS,
//        description = TaterLib.Constants.PROJECT_DESCRIPTION,
//        url = TaterLib.Constants.PROJECT_URL)
public class VelocityLoaderPlugin {
    private static Loader loader;

    @Inject
    public VelocityLoaderPlugin(PluginContainer plugin, ProxyServer server, Logger logger) {
        loader = new TaterLibLoader(plugin, server, logger);
        loader.registerPlugin(plugin());
        loader.onInit();
    }

    public static dev.neuralnexus.taterlib.plugin.Plugin plugin() {
        String pluginClassName = "dev.neuralnexus.taterlib.velocity.v3_3_0.VelocityTaterLibPlugin";
        try {
            Class<?> pluginClass = Class.forName(pluginClassName);
            return (dev.neuralnexus.taterlib.plugin.Plugin)
                    pluginClass.getConstructor().newInstance();
        } catch (Exception e) {
            System.err.println("Failed to load plugin class: " + pluginClassName);
            e.printStackTrace();
        }
        return null;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        loader.onEnable();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        loader.onDisable();
    }
}
