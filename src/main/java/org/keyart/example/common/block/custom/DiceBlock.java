package org.keyart.example.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.keyart.example.core.utils.ExecuteDelayed;

import javax.annotation.Nullable;

public class DiceBlock extends Block {
    public static DirectionProperty FACING = DirectionProperty.create("number",
            Direction.UP,
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
            Direction.DOWN);

    public DiceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        ExecuteDelayed.addTask(new ExecuteDelayed.DelayedTask(100) {
            @Override
            public void run() {
                pLevel.destroyBlock(pPos, false);

                super.run();
            }
        });

        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        return this.defaultBlockState().setValue(FACING, getRandomDirection());
    }

    public BlockState getRandomBlockState() {
        return this.defaultBlockState().setValue(FACING, getRandomDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    private Direction getRandomDirection() {
        Direction[] dirs = new Direction[] {
                Direction.UP,
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST,
                Direction.DOWN
        };

        return dirs[RandomSource.create().nextIntBetweenInclusive(0, dirs.length-1)];
    }
}
