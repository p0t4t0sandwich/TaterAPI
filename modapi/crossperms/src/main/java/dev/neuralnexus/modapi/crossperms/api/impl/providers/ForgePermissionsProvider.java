/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.modapi.crossperms.api.impl.providers;

import dev.neuralnexus.modapi.crossperms.api.HasPermission;
import dev.neuralnexus.modapi.crossperms.api.PermissionsProvider;
import dev.neuralnexus.modapi.crossperms.api.PermsAPI;

import net.minecraftforge.server.permission.PermissionAPI;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/** Forge permissions provider */
@SuppressWarnings({"Anonymous2MethodRef", "Convert2Lambda"})
public class ForgePermissionsProvider implements PermissionsProvider {
    @Override
    public @NotNull Map<Class<?>, List<HasPermission<?, ?>>> getProviders() {
        return Map.of(
                Object.class,
                List.of(
                        new HasPermission<String, Object>() {
                            @Override
                            public boolean hasPermission(Object subject, String permission) {
                                return profileHasPermission(subject, permission);
                            }
                        }));
    }

    private boolean profileHasPermission(Object subject, String permission) {
        return PermsAPI.instance()
                .getGameProfile(subject)
                .filter(
                        profile ->
                                PermissionAPI.getPermissionHandler()
                                        .hasPermission(profile, permission, null))
                .isPresent();
    }
}
