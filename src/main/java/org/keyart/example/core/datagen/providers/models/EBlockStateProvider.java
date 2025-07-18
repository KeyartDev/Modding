package org.keyart.example.core.datagen.providers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
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
        blockWithItem(BlockRegistry.EXAMPLE_BLOCK);
        blockWithItem(BlockRegistry.FUNC_BLOCK);
        blockWithItem(BlockRegistry.SOME_BLOCK_ORE);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
