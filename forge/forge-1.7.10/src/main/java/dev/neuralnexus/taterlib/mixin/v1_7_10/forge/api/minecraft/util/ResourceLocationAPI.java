/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.mixin.v1_7_10.forge.api.minecraft.util;

import dev.neuralnexus.modapi.metadata.Mappings;
import dev.neuralnexus.modapi.metadata.enums.MinecraftVersion;
import dev.neuralnexus.modapi.muxins.annotations.ReqMCVersion;
import dev.neuralnexus.modapi.muxins.annotations.ReqMappings;
import dev.neuralnexus.taterapi.resource.ResourceKey;

import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Interface.Remap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@ReqMappings(Mappings.LEGACY_SEARGE)
@ReqMCVersion(min = MinecraftVersion.V11, max = MinecraftVersion.V11_2)
@Mixin(ResourceLocation.class)
@Implements(@Interface(iface = ResourceKey.class, prefix = "resourceKey$", remap = Remap.NONE))
public abstract class ResourceLocationAPI {
    @Shadow
    public abstract String shadow$getResourceDomain();

    @Shadow
    public abstract String shadow$getResourcePath();

    public String resourceKey$namespace() {
        return this.shadow$getResourceDomain();
    }

    public String resourceKey$value() {
        return this.shadow$getResourcePath();
    }
}
