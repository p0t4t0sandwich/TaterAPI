package dev.neuralnexus.taterlib.vanilla.event.entity;

import dev.neuralnexus.taterlib.event.entity.EntityDamageEvent;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Vanilla implementation of {@link EntityDamageEvent}. */
public class VanillaEntityDamageEvent extends VanillaEntityEvent implements EntityDamageEvent {
    private final DamageSource damageSource;
    private final float damage;
    private final CallbackInfo ci;

    public VanillaEntityDamageEvent(
            Entity entity, DamageSource damageSource, float damage, CallbackInfo ci) {
        super(entity);
        this.damageSource = damageSource;
        this.damage = damage;
        this.ci = ci;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCancelled() {
        return ci.isCancelled();
    }

    /** {@inheritDoc} */
    @Override
    public void setCancelled(boolean cancelled) {
        if (cancelled) {
            ci.cancel();
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getCause() {
        return damageSource.type().msgId();
    }

    /** {@inheritDoc} */
    @Override
    public double getDamage() {
        return damage;
    }
}
