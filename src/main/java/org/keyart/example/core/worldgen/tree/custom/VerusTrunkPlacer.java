package org.keyart.example.core.worldgen.tree.custom;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.keyart.example.core.worldgen.tree.ModTrunkPlacerTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class VerusTrunkPlacer extends TrunkPlacer {
    public static final Codec<VerusTrunkPlacer> CODEC = RecordCodecBuilder.create(verusTrunkPlacerInstance ->
            trunkPlacerParts(verusTrunkPlacerInstance).apply(verusTrunkPlacerInstance, VerusTrunkPlacer::new));

    public VerusTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.VERUS_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos,
            BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
        setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
        int height = pFreeTreeHeight + pRandom.nextInt(heightRandA, heightRandB + 3) + pRandom.nextInt(heightRandB - 1, heightRandB + 1);
        ArrayList<FoliagePlacer.FoliageAttachment> foliageAttachmentList = new ArrayList<>(List.of());

        for (int i = 0; i < height; i++) {
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i), pConfig);

            if (i % 3 == 0 && i != 0 && (i + 5) <= height) {
                if (pRandom.nextFloat() > 0.85f) {
                    for (int x = 0; x < 3; x++) {
                        pBlockSetter.accept(pPos.above(i).relative(Direction.NORTH, x),
                                ((BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))));
                        if (x == 2) {
                            pBlockSetter.accept(pPos.above(i+1).relative(Direction.NORTH, x),
                                    ((BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y))));
                            foliageAttachmentList.add(new FoliagePlacer.FoliageAttachment(pPos.above(i+1).relative(Direction.NORTH, x), 100, false));
                        }
                    }
                }
            }

            if (i % 3 == 0 && i != 0 && (i + 5) <= height) {
                if (pRandom.nextFloat() > 0.85f) {
                    for (int x = 0; x < 3; x++) {
                        pBlockSetter.accept(pPos.above(i).relative(Direction.EAST, x),
                                (BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)));
                        if (x == 2) {
                            pBlockSetter.accept(pPos.above(i + 1).relative(Direction.EAST, x),
                                    ((BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y))));
                            foliageAttachmentList.add(new FoliagePlacer.FoliageAttachment(pPos.above(i+1).relative(Direction.EAST, x), 100, false));
                        }
                    }

                }
            }

            if (i % 3 == 0 && i != 0 && (i + 5) <= height) {
                if (pRandom.nextFloat() > 0.85f) {
                    for (int x = 0; x < 3; x++) {
                        pBlockSetter.accept(pPos.above(i).relative(Direction.SOUTH, x),
                                ((BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))));
                        if (x == 2) {
                            pBlockSetter.accept(pPos.above(i+1).relative(Direction.SOUTH, x),
                                    ((BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y))));
                            foliageAttachmentList.add(new FoliagePlacer.FoliageAttachment(pPos.above(i+1).relative(Direction.SOUTH, x), 100, false));
                        }
                    }
                }
            }

            if (i % 3 == 0 && i != 0 && (i + 5) <= height) {
                if (pRandom.nextFloat() > 0.85f) {
                    for (int x = 0; x < 3; x++) {
                        pBlockSetter.accept(pPos.above(i).relative(Direction.WEST, x),
                                ((BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.X))));
                        if (x == 2) {
                            pBlockSetter.accept(pPos.above(i+1).relative(Direction.WEST, x),
                                    ((BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos).setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y))));
                            foliageAttachmentList.add(new FoliagePlacer.FoliageAttachment(pPos.above(i+1).relative(Direction.WEST, x), 100, false));
                        }
                    }
                }
            }
        }

        foliageAttachmentList.add(new FoliagePlacer.FoliageAttachment(pPos.above(height), 0, false));

        return ImmutableList.copyOf(foliageAttachmentList);
    }
}
