/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.taterlib.config.sections;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/** A class for parsing hook configurations. */
@ConfigSerializable
public class HookConfig {
    @Setting private String name;
    @Setting private boolean enabled;

    /** Returns whether the hook is enabled. */
    public String name() {
        return name;
    }

    /** Returns whether the hook is enabled. */
    public boolean enabled() {
        return enabled;
    }
}
