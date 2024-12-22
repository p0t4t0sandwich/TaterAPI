/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package org.spongepowered.api;

import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.plugin.PluginContainer;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/** Fake Sponge API class. */
public final class Sponge {
    public static Server getServer() {
        return new Server() {};
    }

    public static Server server() {
        return new Server() {};
    }

    public static PluginManager pluginManager() {
        return new PluginManager() {
            @Override
            public Optional<PluginContainer> plugin(String id) {
                return Optional.empty();
            }

            @Override
            public Optional<PluginContainer> getPlugin(String id) {
                return Optional.empty();
            }

            @Override
            public Collection<PluginContainer> plugins() {
                return Collections.emptyList();
            }

            @Override
            public Collection<PluginContainer> getPlugins() {
                return Collections.emptyList();
            }
        };
    }

    public static PluginManager getPluginManager() {
        return pluginManager();
    }

    public static Platform platform() {
        return new Platform() {
            @Override
            public MinecraftVersion minecraftVersion() {
                return new MinecraftVersion() {
                    @Override
                    public int compareTo(MinecraftVersion o) {
                        return 0;
                    }

                    @Override
                    public String name() {
                        return "Unknown";
                    }

                    @Override
                    public String getName() {
                        return "";
                    }

                    @Override
                    public int protocolVersion() {
                        return 0;
                    }

                    @Override
                    public boolean isLegacy() {
                        return false;
                    }
                };
            }

            @Override
            public MinecraftVersion getMinecraftVersion() {
                return minecraftVersion();
            }
        };
    }

    public static Platform getPlatform() {
        return platform();
    }
}
