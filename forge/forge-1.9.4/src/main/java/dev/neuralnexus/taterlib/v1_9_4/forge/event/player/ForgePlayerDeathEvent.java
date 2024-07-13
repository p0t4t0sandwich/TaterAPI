/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.taterlib.v1_9_4.forge.event.player;

import dev.neuralnexus.taterapi.entity.Entity;
import dev.neuralnexus.taterapi.entity.player.Player;
import dev.neuralnexus.taterapi.event.player.PlayerDeathEvent;
import dev.neuralnexus.taterapi.item.inventory.ItemStack;
import dev.neuralnexus.taterlib.v1_9_4.forge.entity.ForgeEntity;
import dev.neuralnexus.taterlib.v1_9_4.forge.item.inventory.ForgeItemStack;
import dev.neuralnexus.taterlib.v1_9_4.forge.player.ForgePlayer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Forge implementation of {@link PlayerDeathEvent}. */
public class ForgePlayerDeathEvent implements PlayerDeathEvent {
    private final LivingDeathEvent event;
    private List<ItemStack> drops = new ArrayList<>();
    private int droppedExp = 0;
    private String deathMessage = "";

    public ForgePlayerDeathEvent(LivingDeathEvent event) {
        this.event = event;
    }

    @Override
    public List<ItemStack> drops() {
        if (!drops.isEmpty()) {
            return drops;
        }
        if (event.getEntity().capturedDrops == null) {
            return new ArrayList<>();
        }
        return event.getEntity().capturedDrops.stream()
                .map(itemEntity -> new ForgeItemStack(itemEntity.getEntityItem()))
                .collect(Collectors.toList());
    }

    @Override
    public void setDrops(List<ItemStack> drops) {
        this.drops = drops;
    }

    @Override
    public void clearDrops() {
        drops.clear();
    }

    @Override
    public int droppedExp() {
        if (droppedExp != 0) {
            return droppedExp;
        }
        return 0;
    }

    @Override
    public void setDroppedExp(int exp) {
        this.droppedExp = exp;
    }

    @Override
    public Entity entity() {
        return new ForgeEntity(event.getEntity());
    }

    @Override
    public Player player() {
        return new ForgePlayer((EntityPlayer) event.getEntity());
    }

    @Override
    public String deathMessage() {
        if (!deathMessage.isEmpty()) {
            return deathMessage;
        }
        return event.getSource().getDeathMessage(event.getEntityLiving()).getFormattedText();
    }

    @Override
    public void setDeathMessage(String deathMessage) {
        this.deathMessage = deathMessage;
    }

    @Override
    public boolean keepInventory() {
        return false;
    }

    @Override
    public void setKeepInventory(boolean keepInventory) {}
}
