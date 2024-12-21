/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.modapi.metadata;

import dev.neuralnexus.modapi.metadata.impl.platform.Bukkit;
import dev.neuralnexus.modapi.metadata.impl.platform.BungeeCord;
import dev.neuralnexus.modapi.metadata.impl.platform.Fabric;
import dev.neuralnexus.modapi.metadata.impl.platform.Forge;
import dev.neuralnexus.modapi.metadata.impl.platform.Hybrid;
import dev.neuralnexus.modapi.metadata.impl.platform.Sponge;
import dev.neuralnexus.modapi.metadata.impl.platform.Vanilla;
import dev.neuralnexus.modapi.metadata.impl.platform.Velocity;

import java.util.ArrayList;
import java.util.List;

public final class Platforms
        implements Bukkit, BungeeCord, Fabric, Forge, Hybrid, Sponge, Vanilla, Velocity {
    private static final List<Platform> platforms = new ArrayList<>();

    /**
     * Returns all platforms that are available.
     *
     * @return An array of all available platforms
     */
    public static List<Platform> get() {
        if (platforms.isEmpty()) {
            detectPlatforms(true);
        }
        return platforms;
    }

    /**
     * Detects all platforms that are available. Doesn't need to be called manually, unless somehow it was called before all platforms were loaded. In such a case, call this method with the force parameter set to true.
     *
     * @param force If true, the platforms will be detected again, even if they were already detected.
     */
    public static void detectPlatforms(boolean force) {
        if (!force && !platforms.isEmpty()) {
            return;
        }

        // Forge
        if (GOLDENFORGE.detect(force)) {
            platforms.addAll(List.of(GOLDENFORGE, FORGE));
            // Forge Hybrids
        } else if (MCPCPLUSPLUS.detect(force)) {
            platforms.addAll(List.of(MCPCPLUSPLUS, FORGE));
        } else if (CAULDRON.detect(force)) {
            platforms.addAll(List.of(CAULDRON, FORGE));
        } else if (KCAULDRON.detect(force)) {
            platforms.addAll(List.of(KCAULDRON, FORGE));
        } else if (THERMOS.detect(force)) {
            platforms.addAll(List.of(THERMOS, FORGE));
        } else if (CRUCIBLE.detect(force)) {
            platforms.addAll(List.of(CRUCIBLE, FORGE));
        } else if (MAGMA.detect(force)) {
            platforms.addAll(List.of(MAGMA, FORGE));
        } else if (KETTING.detect(force)) {
            platforms.addAll(List.of(KETTING, FORGE));
        } else if (FORGE.detect(force)) {
            platforms.add(FORGE);
        }

        if (NEOFORGE.detect(force)) {
            platforms.add(NEOFORGE);
        }

        // Fabric
        if (QUILT.detect(force)) {
            platforms.addAll(List.of(QUILT, FABRIC));
            // Fabric Hybrids
        } else if (CARDBOARD.detect(force)) {
            platforms.addAll(List.of(CARDBOARD, FABRIC));
        } else if (BANNER.detect(force)) {
            platforms.addAll(List.of(BANNER, FABRIC));
        } else if (FABRIC.detect(force)) {
            platforms.add(FABRIC);
        }

        // Bukkit
        if (PURPUR.detect(force)) {
            platforms.addAll(List.of(PURPUR, PUFFERFISH, PAPER, SPIGOT, BUKKIT));
        } else if (PUFFERFISH.detect(force)) {
            platforms.addAll(List.of(PUFFERFISH, PAPER, SPIGOT, BUKKIT));
        } else if (PAPER.detect(force)) {
            platforms.addAll(List.of(PAPER, SPIGOT, BUKKIT));
        } else if (SPIGOT.detect(force)) {
            platforms.addAll(List.of(SPIGOT, BUKKIT));
        } else if (POSEIDON.detect(force)) {
            platforms.addAll(List.of(POSEIDON, BUKKIT));
        } else if (BUKKIT.detect(force)) {
            platforms.add(BUKKIT);
        }
        if (FOLIA.detect(force)) {
            platforms.add(FOLIA);
        }

        // BungeeCord
        if (TRAVERTINE.detect(force)) {
            platforms.addAll(List.of(TRAVERTINE, WATERFALL, BUNGEECORD));
        } else if (LIGHTFALL.detect(force)) {
            platforms.addAll(List.of(LIGHTFALL, WATERFALL, BUNGEECORD));
        } else if (WATERFALL.detect(force)) {
            platforms.addAll(List.of(WATERFALL, BUNGEECORD));
        } else if (BUNGEECORD.detect(force)) {
            platforms.add(BUNGEECORD);
        }

        // Hybrid
        if (MOHIST.detect(force)) {
            platforms.add(MOHIST);
        } else if (ARCLIGHT.detect(force)) {
            platforms.add(ARCLIGHT);
        }

        // Sponge
        if (SPONGE.detect(force)) {
            platforms.add(SPONGE);
        }

        // Velocity
        if (VELOCITY.detect(force)) {
            platforms.add(VELOCITY);
        }
    }

    public static boolean isBukkit() {
        return get().contains(BUKKIT);
    }

    public static boolean isSpigot() {
        return get().contains(SPIGOT);
    }

    public static boolean isPaper() {
        return get().contains(PAPER);
    }

    public static boolean isBungeeCord() {
        return get().contains(BUNGEECORD);
    }

    public static boolean isWaterfall() {
        return get().contains(WATERFALL);
    }

    public static boolean isFabric() {
        return get().contains(FABRIC);
    }

    public static boolean isQuilt() {
        return get().contains(QUILT);
    }

    public static boolean isFabricHybrid() {
        return get().contains(BUKKIT) && get().contains(FABRIC);
    }

    public static boolean isForge() {
        return get().contains(FORGE);
    }

    public static boolean isForgeHybrid() {
        return get().contains(BUKKIT) && get().contains(FORGE);
    }

    public static boolean isNeoForge() {
        return get().contains(NEOFORGE);
    }

    public static boolean isHybrid() {
        return get().contains(MOHIST)
                || get().contains(ARCLIGHT)
                || isForgeHybrid()
                || isFabricHybrid();
    }

    public static boolean isSponge() {
        return get().contains(SPONGE);
    }

    public static boolean isSpongeForge() {
        return get().contains(SPONGE) && get().contains(FORGE);
    }

    public static boolean isSpongeFabric() {
        return get().contains(SPONGE) && get().contains(FABRIC);
    }

    public static boolean isVelocity() {
        return get().contains(VELOCITY);
    }

    public static boolean isProxy() {
        return isBungeeCord() || isVelocity();
    }

    public static boolean isMixedForgeFabric() {
        return get().contains(FORGE) && get().contains(FABRIC);
    }
}
