package org.keyart.example.core.datagen.providers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.core.registry.BlockRegistry;

public class EBlockStateProvider extends BlockStateProvider {
    public EBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Example.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(BlockRegistry.SOME_BLOCK);
        blockWithItem(BlockRegistry.FUNC_BLOCK);
        blockWithItem(BlockRegistry.SOME_BLOCK_ORE);

        stairsBlock(((StairBlock) BlockRegistry.SOME_STAIRS.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));
        slabBlock(((SlabBlock) BlockRegistry.SOME_SLAB.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));

        buttonBlock(((ButtonBlock) BlockRegistry.SOME_BUTTON.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));
        pressurePlateBlock(((PressurePlateBlock) BlockRegistry.SOME_PRESSURE_PLATE.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));

        fenceBlock(((FenceBlock) BlockRegistry.SOME_FENCE.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));
        fenceGateBlock(((FenceGateBlock) BlockRegistry.SOME_FENCE_GATE.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));
        wallBlock(((WallBlock) BlockRegistry.SOME_WALL.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
