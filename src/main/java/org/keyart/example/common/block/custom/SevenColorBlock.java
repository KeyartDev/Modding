package org.keyart.example.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.keyart.example.core.registry.ModBlocks;

public class SevenColorBlock extends FlowerBlock {
    public SevenColorBlock() {
        super(() -> MobEffects.LUCK, 5,
                BlockBehaviour.Properties.copy(Blocks.ALLIUM)
                        .noCollission()
                        .noOcclusion()
                        .instabreak());
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pLevel.getBlockState(pPos.below()) == ModBlocks.FUNC_BLOCK.get().defaultBlockState()) {
            return false;
        }

        return super.canSurvive(pState, pLevel, pPos);
    }
}
