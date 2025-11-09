package org.keyart.example.core.datagen.providers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.common.block.crop.StrawberryCropBlock;
import org.keyart.example.core.registry.BlockRegistry;

import java.util.function.Function;

public class EBlockStateProvider extends BlockStateProvider {
    public EBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Example.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(BlockRegistry.SOME_BLOCK);
        blockWithItem(BlockRegistry.FUNC_BLOCK);
        blockWithItem(BlockRegistry.SOME_BLOCK_ORE);


        simpleBlockWithItem(BlockRegistry.SOME_PEDISTAL_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/some_pedistal_block")));


        stairsBlock(((StairBlock) BlockRegistry.SOME_STAIRS.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));
        slabBlock(((SlabBlock) BlockRegistry.SOME_SLAB.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));

        buttonBlock(((ButtonBlock) BlockRegistry.SOME_BUTTON.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));
        pressurePlateBlock(((PressurePlateBlock) BlockRegistry.SOME_PRESSURE_PLATE.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));

        fenceBlock(((FenceBlock) BlockRegistry.SOME_FENCE.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));
        fenceGateBlock(((FenceGateBlock) BlockRegistry.SOME_FENCE_GATE.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));
        wallBlock(((WallBlock) BlockRegistry.SOME_WALL.get()), blockTexture(BlockRegistry.SOME_BLOCK.get()));

        makeStrawberryCrop(((CropBlock) BlockRegistry.STRAWBERRY_CROP.get()), "strawberry_stage", "strawberry_stage");

        simpleBlockWithItem(BlockRegistry.SEVEN_COLOR.get(), models().cross(blockTexture(BlockRegistry.SEVEN_COLOR.get()).getPath(),
                blockTexture(BlockRegistry.SEVEN_COLOR.get())).renderType("cutout"));
        simpleBlockWithItem(BlockRegistry.SEVEN_COLOR_POTTED.get(), models().singleTexture("potted_seven_color_flower", ResourceLocation.parse("flower_pot_cross"), "plant",
                blockTexture(BlockRegistry.SEVEN_COLOR.get())).renderType("cutout"));
    }

    private void makeStrawberryCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> strawberryStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] strawberryStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(Example.MODID, "block/" + textureName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
