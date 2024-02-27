package dev.neuralnexus.taterlib.fabric.inventory;

import dev.neuralnexus.taterlib.exceptions.VersionFeatureNotSupportedException;
import dev.neuralnexus.taterlib.inventory.ItemStack;

import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;

/** Fabric implementation of {@link ItemStack}. */
public class FabricItemStack implements ItemStack {
    private final net.minecraft.item.ItemStack itemStack;

    /**
     * Constructor.
     *
     * @param itemStack The Fabric item stack.
     */
    public FabricItemStack(net.minecraft.item.ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Getter for the Fabric item stack.
     *
     * @return The Fabric item stack.
     */
    public net.minecraft.item.ItemStack itemStack() {
        return itemStack;
    }

    /** {@inheritDoc} */
    @Override
    public String type() {
        String itemName = itemStack.getItem().toString();
        if (!itemName.contains(":")) {
            return "minecraft:" + itemName;
        }
        return itemName;
    }

    /** {@inheritDoc} */
    @Override
    public int count() {
        return itemStack.getAmount();
    }

    /** {@inheritDoc} */
    @Override
    public void setCount(int count) {
        itemStack.setAmount(count);
    }

    /** {@inheritDoc} */
    @Override
    public ItemStack clone() {
        return new FabricItemStack(itemStack.copy());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasDisplayName() {
        return itemStack.hasDisplayName();
    }

    /** {@inheritDoc} */
    @Override
    public String displayName() {
        return itemStack.getDisplayName().getString();
    }

    /** {@inheritDoc} */
    @Override
    public void setDisplayName(String name) {
        itemStack.setDisplayName(new TranslatableComponent(name));
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasLore() {
        // TODO: Implement
        throw new VersionFeatureNotSupportedException();
    }

    /** {@inheritDoc} */
    @Override
    public List<String> lore() {
        // TODO: Implement
        throw new VersionFeatureNotSupportedException();
    }

    /** {@inheritDoc} */
    @Override
    public void setLore(List<String> lore) {
        // TODO: Implement
        throw new VersionFeatureNotSupportedException();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasEnchants() {
        return !itemStack.getEnchantmentList().isEmpty();
    }

    /** {@inheritDoc} */
    @Override
    public boolean unbreakable() {
        // TODO: Implement
        throw new VersionFeatureNotSupportedException();
    }

    /** {@inheritDoc} */
    @Override
    public void setUnbreakable(boolean unbreakable) {
        // TODO: Implement
        throw new VersionFeatureNotSupportedException();
    }
}
