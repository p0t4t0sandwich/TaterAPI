/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.taterlib.v1_8_9.forge.item.inventory;

import dev.neuralnexus.taterapi.exceptions.VersionFeatureNotSupportedException;
import dev.neuralnexus.taterapi.item.inventory.ItemStack;
import dev.neuralnexus.taterapi.resource.ResourceKey;

import java.util.List;
import java.util.Optional;

/** Forge implementation of {@link ItemStack}. */
public class ForgeItemStack implements ItemStack {
    private final net.minecraft.item.ItemStack itemStack;

    /**
     * Constructor.
     *
     * @param itemStack The Forge item stack.
     */
    public ForgeItemStack(net.minecraft.item.ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Getter for the Forge item stack.
     *
     * @return The Forge item stack.
     */
    public net.minecraft.item.ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public ResourceKey type() {
        // TODO: Find item registry
        return ResourceKey.of(itemStack.getItem().toString());
    }

    @Override
    public int count() {
        return itemStack.stackSize;
    }

    @Override
    public void setCount(int count) {
        itemStack.stackSize = count;
    }

    @Override
    public ItemStack clone() {
        return new ForgeItemStack(itemStack.copy());
    }

    @Override
    public boolean hasDisplayName() {
        return itemStack.hasDisplayName();
    }

    @Override
    public Optional<String> displayName() {
        return Optional.of(itemStack.getDisplayName());
    }

    @Override
    public void setDisplayName(String name) {
        itemStack.setStackDisplayName(name);
    }

    @Override
    public boolean hasLore() {
        // TODO: Implement
        throw new VersionFeatureNotSupportedException();
    }

    @Override
    public List<String> lore() {
        // TODO: Implement
        throw new VersionFeatureNotSupportedException();
    }

    @Override
    public void setLore(List<String> lore) {
        // TODO: Implement
        throw new VersionFeatureNotSupportedException();
    }

    @Override
    public boolean hasEnchants() {
        return itemStack.isItemEnchanted();
    }

    @Override
    public boolean unbreakable() {
        return itemStack.isItemStackDamageable();
    }

    @Override
    public void setUnbreakable(boolean unbreakable) {
        // TODO: Implement
        throw new VersionFeatureNotSupportedException();
    }
}