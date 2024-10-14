/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterapi.metrics.bstats;

import dev.neuralnexus.taterapi.MinecraftVersion;
import dev.neuralnexus.taterapi.Platform;
import dev.neuralnexus.taterapi.Side;
import dev.neuralnexus.taterapi.TaterAPIProvider;
import dev.neuralnexus.taterapi.metrics.bstats.bukkit.BukkitBetaMetricsAdapter;
import dev.neuralnexus.taterapi.metrics.bstats.bukkit.BukkitMetricsAdapter;
import dev.neuralnexus.taterapi.metrics.bstats.bungeecord.BungeeCordMetricsAdapter;
import dev.neuralnexus.taterapi.metrics.bstats.sponge.SpongeMetricsAdapter;
import dev.neuralnexus.taterapi.metrics.bstats.velocity.VelocityMetricsAdapter;

import org.bstats.charts.CustomChart;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/** Metrics adapter for BStats to allow for easy multi-platform support. */
public class MetricsAdapter {
    public static @Nullable BStatsMetrics setupMetrics(
            Object plugin,
            Object pluginServer,
            Object pluginLogger,
            Map<Platform, Integer> pluginIds,
            List<CustomChart> charts) {
        if (TaterAPIProvider.side().is(Side.CLIENT)) {
            return null;
        }
        Platform platform = Platform.get();
        Object metrics = null;
        if (platform.isBukkitBased()) {
            if (TaterAPIProvider.minecraftVersion().isOlderThan(MinecraftVersion.V1_0)) {
                metrics =
                        BukkitBetaMetricsAdapter.setupMetrics(
                                plugin, pluginIds.get(Platform.BUKKIT), Collections.emptyList());
            } else {
                metrics =
                        BukkitMetricsAdapter.setupMetrics(
                                plugin, pluginIds.get(Platform.BUKKIT), charts);
            }
        } else if (platform.isBungeeCordBased()) {
            metrics =
                    BungeeCordMetricsAdapter.setupMetrics(
                            plugin, pluginIds.get(Platform.BUNGEECORD), charts);
        } else if (platform.isSpongeBased()) {
            metrics =
                    SpongeMetricsAdapter.setupMetrics(
                            plugin, pluginLogger, pluginIds.get(Platform.SPONGE), charts);
        } else if (platform.isVelocityBased()) {
            metrics =
                    VelocityMetricsAdapter.setupMetrics(
                            plugin,
                            pluginServer,
                            pluginLogger,
                            pluginIds.get(Platform.VELOCITY),
                            charts);
        }
        return new BStatsMetrics(metrics);
    }

    public static BStatsMetrics setupMetrics(
            Object plugin,
            Object pluginServer,
            Object pluginLogger,
            Map<Platform, Integer> pluginIds) {
        return setupMetrics(plugin, pluginServer, pluginLogger, pluginIds, Collections.emptyList());
    }
}
