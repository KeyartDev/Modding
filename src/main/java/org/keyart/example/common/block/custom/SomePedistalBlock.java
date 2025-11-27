package org.keyart.example.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import org.keyart.example.common.block.entity.ModBlockEntities;
import org.keyart.example.common.block.entity.SomeBlockPedistalBlockEntity;
import org.keyart.example.common.client.particle.ModParticle;

import java.awt.*;

public class SomePedistalBlock extends BaseEntityBlock {
    public static final BooleanProperty CRAFTING = BooleanProperty.create("crafting");
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15,  12, 15);

    public SomePedistalBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CRAFTING, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SomeBlockPedistalBlockEntity) {
                ((SomeBlockPedistalBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);

        if (pState.getValue(CRAFTING)) {
            for (int i = 0; i < 5; i++) {
                double x = pPos.getX()+0.5+pRandom.nextInt(-50, 50)/100d;
                double y = pPos.getY()+1+pRandom.nextInt(0, 50)/100d;
                double z = pPos.getZ()+0.5+pRandom.nextInt(-50, 50)/100d;

                double vX = (pPos.getX()+0.5)-x;
                double vY = (pPos.getY()+1)-y;
                double vZ = (pPos.getZ()+0.5)-z;

                double pathLength = Math.sqrt(Math.pow(vX, 2) + Math.pow(vY, 2) + Math.pow(vZ, 2));
                double speed = Math.sqrt(Math.pow(vX/10, 2) + Math.pow(vY/10, 2) + Math.pow(vZ/10, 2));
                double lifeTime = pathLength / speed;

                pLevel.addParticle(new ModParticle.Options((new Color(151, 11, 221)).getRGB(), 0.1F, (int) lifeTime, 0.5F), x, y, z, vX/10, vY/10, vZ/10);
            }

        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof SomeBlockPedistalBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), ((SomeBlockPedistalBlockEntity) entity), pPos);
            } else {
                throw new IllegalStateException("Our container provider is missing!");
            }
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.SOME_PEDISTAL_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SomeBlockPedistalBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CRAFTING);
    }
}
