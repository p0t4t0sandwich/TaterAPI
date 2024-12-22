/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package net.neoforged.fml.loading.moddiscovery;

import net.neoforged.neoforgespi.language.IModInfo;

import org.apache.maven.artifact.versioning.ArtifactVersion;

/** Fake NeoForge mod info. */
public class ModInfo implements IModInfo {
    @Override
    public String getModId() {
        return "";
    }

    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public ArtifactVersion getVersion() {
        return new ArtifactVersion() {};
    }
}
