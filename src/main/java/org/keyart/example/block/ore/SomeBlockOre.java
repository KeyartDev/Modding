package org.keyart.example.block.ore;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;

public class SomeBlockOre extends DropExperienceBlock {

    public SomeBlockOre() {
        super(Properties.copy(Blocks.IRON_ORE).requiresCorrectToolForDrops(), UniformInt.of(1, 3));
    }
}
