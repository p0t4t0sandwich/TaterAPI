/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.modapi.metadata.impl.version;

import dev.neuralnexus.modapi.metadata.MinecraftVersion;

// 3
@SuppressWarnings("unused")
public interface Indev {
    MinecraftVersion Indev_0_31 = MinecraftVersionImpl.of("Indev 0.31");
    MinecraftVersion Indev = MinecraftVersionImpl.of("Indev");
}
