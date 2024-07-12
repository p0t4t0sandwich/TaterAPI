/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/TaterLib/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.taterlib.v1_6_4.forge.block;

import dev.neuralnexus.taterapi.block.Block;
import dev.neuralnexus.taterapi.resource.ResourceKey;
import dev.neuralnexus.taterapi.world.BlockPos;

import net.minecraft.util.ChunkCoordinates;

/** Forge implementation of {@link Block}. */
public class ForgeBlock implements Block {
    private final ChunkCoordinates pos;
    private final net.minecraft.block.Block block;

    public ForgeBlock(ChunkCoordinates pos, net.minecraft.block.Block block) {
        this.pos = pos;
        this.block = block;
    }

    /** {@inheritDoc} */
    @Override
    public ResourceKey type() {
        // TODO: Find block registry
        return ResourceKey.of(block.getLocalizedName().split("block\\.")[1].replace(".", ":"));
    }

    /** {@inheritDoc} */
    @Override
    public BlockPos blockPos() {
        return new BlockPos(pos.posX, pos.posY, pos.posZ);
    }
}
