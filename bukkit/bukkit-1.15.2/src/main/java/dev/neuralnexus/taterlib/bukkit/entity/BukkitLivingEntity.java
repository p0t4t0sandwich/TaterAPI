package dev.neuralnexus.taterlib.bukkit.entity;

import dev.neuralnexus.taterlib.entity.Entity;
import dev.neuralnexus.taterlib.entity.LivingEntity;

/** Bukkit implementation of {@link LivingEntity}. */
public class BukkitLivingEntity extends BukkitEntity implements LivingEntity {
    private final org.bukkit.entity.LivingEntity entity;

    /**
     * Constructor.
     *
     * @param entity The Bukkit entity.
     */
    public BukkitLivingEntity(org.bukkit.entity.LivingEntity entity) {
        super(entity);
        this.entity = entity;
    }

    /** {@inheritDoc} */
    @Override
    public void damage(double amount) {
        entity.damage(amount);
    }

    /** {@inheritDoc} */
    @Override
    public void damage(double amount, Entity source) {
        entity.damage(amount, ((BukkitEntity) source).getEntity());
    }

    /** {@inheritDoc} */
    @Override
    public double getHealth() {
        // Reflect to get (double) entity.getHealth();
        try {
            return (double) entity.getClass().getMethod("getHealth").invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setHealth(double health) {
        entity.setHealth(health);
    }

    /** {@inheritDoc} */
    @Override
    public double getAbsorptionAmount() {
        // Reflect to get ((CraftLivingEntity) entity).getHandle().getAbsorptionHearts();
        try {
            Class<?> craftLivingEntity =
                    Class.forName("org.bukkit.craftbukkit.v1_15_R1.entity.CraftLivingEntity");
            Object handle = craftLivingEntity.getMethod("getHandle").invoke(entity);
            return (double) handle.getClass().getMethod("getAbsorptionHearts").invoke(handle);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setAbsorptionAmount(double amount) {
        // Reflect to set ((CraftLivingEntity) entity).getHandle().setAbsorptionHearts(amount);
        try {
            Class<?> craftLivingEntity =
                    Class.forName("org.bukkit.craftbukkit.v1_15_R1.entity.CraftLivingEntity");
            Object handle = craftLivingEntity.getMethod("getHandle").invoke(entity);
            handle.getClass().getMethod("setAbsorptionHearts", double.class).invoke(handle, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public double getMaxHealth() {
        // Reflect to get (double) entity.getMaxHealth();
        try {
            return (double) entity.getClass().getMethod("getMaxHealth").invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setMaxHealth(double health) {
        entity.setMaxHealth(health);
    }
}