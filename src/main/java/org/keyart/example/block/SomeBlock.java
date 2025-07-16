package org.keyart.example.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SomeBlock extends Block {
    public SomeBlock() {
        super(Properties.copy(Blocks.IRON_BLOCK).noOcclusion().requiresCorrectToolForDrops().);
    }
}
