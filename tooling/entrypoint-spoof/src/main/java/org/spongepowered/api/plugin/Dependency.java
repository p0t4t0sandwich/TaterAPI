package org.spongepowered.api.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fake annotation class to keep dependency information. <br>
 * Still need to use the SpongeGradle tool/annotationProcessor to generate the
 * META-INF/sponge_plugins.json file. The rest of the information can just be generated with
 * gradle.properties.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Dependency {
    String id();

    String version() default "";

    boolean optional() default false;
}
