/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */
package dev.neuralnexus.taterlib.v1_14_4.vanilla.entity;

import dev.neuralnexus.taterapi.entity.Entity;
import dev.neuralnexus.taterapi.entity.LivingEntity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.SharedMonsterAttributes;

/** Vanilla implementation of {@link LivingEntity}. */
public class VanillaLivingEntity extends VanillaEntity implements LivingEntity {
    private final net.minecraft.world.entity.LivingEntity entity;

    /**
     * Constructor.
     *
     * @param entity The entity.
     */
    public VanillaLivingEntity(net.minecraft.world.entity.LivingEntity entity) {
        super(entity);
        this.entity = entity;
    }

    /**
     * Gets the entity.
     *
     * @return The entity.
     */
    public net.minecraft.world.entity.LivingEntity entity() {
        return entity;
    }

    @Override
    public void damage(double amount) {
        entity.hurt(DamageSource.GENERIC, (float) amount);
    }

    @Override
    public void damage(double amount, Entity source) {
        entity.hurt(
                DamageSource.mobAttack(((VanillaLivingEntity) source).entity()), (float) amount);
    }

    @Override
    public double health() {
        return entity.getHealth();
    }

    @Override
    public void setHealth(double health) {
        entity.setHealth((float) health);
    }

    @Override
    public double absorptionAmount() {
        return entity.getAbsorptionAmount();
    }

    @Override
    public void setAbsorptionAmount(double amount) {
        entity.setAbsorptionAmount((float) amount);
    }

    @Override
    public double maxHealth() {
        return entity.getMaxHealth();
    }

    @Override
    public void setMaxHealth(double health) {
        entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(health);
    }
}
