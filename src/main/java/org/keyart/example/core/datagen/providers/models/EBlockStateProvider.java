package org.keyart.example.core.datagen.providers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.common.block.crop.StrawberryCropBlock;
import org.keyart.example.core.registry.ModBlocks;

import java.util.function.Function;

public class EBlockStateProvider extends BlockStateProvider {
    public EBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Example.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.SOME_BLOCK);
        blockWithItem(ModBlocks.FUNC_BLOCK);
        blockWithItem(ModBlocks.SOME_BLOCK_ORE);


        simpleBlockWithItem(ModBlocks.SOME_PEDISTAL_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/some_pedistal_block")));


        stairsBlock(((StairBlock) ModBlocks.SOME_STAIRS.get()), blockTexture(ModBlocks.SOME_BLOCK.get()));
        slabBlock(((SlabBlock) ModBlocks.SOME_SLAB.get()), blockTexture(ModBlocks.SOME_BLOCK.get()), blockTexture(ModBlocks.SOME_BLOCK.get()));

        buttonBlock(((ButtonBlock) ModBlocks.SOME_BUTTON.get()), blockTexture(ModBlocks.SOME_BLOCK.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.SOME_PRESSURE_PLATE.get()), blockTexture(ModBlocks.SOME_BLOCK.get()));

        fenceBlock(((FenceBlock) ModBlocks.SOME_FENCE.get()), blockTexture(ModBlocks.SOME_BLOCK.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.SOME_FENCE_GATE.get()), blockTexture(ModBlocks.SOME_BLOCK.get()));
        wallBlock(((WallBlock) ModBlocks.SOME_WALL.get()), blockTexture(ModBlocks.SOME_BLOCK.get()));

        makeStrawberryCrop(((CropBlock) ModBlocks.STRAWBERRY_CROP.get()), "strawberry_stage", "strawberry_stage");

        simpleBlockWithItem(ModBlocks.SEVEN_COLOR.get(), models().cross(blockTexture(ModBlocks.SEVEN_COLOR.get()).getPath(),
                blockTexture(ModBlocks.SEVEN_COLOR.get())).renderType("cutout"));
        simpleBlockWithItem(ModBlocks.SEVEN_COLOR_POTTED.get(), models().singleTexture("potted_seven_color_flower", ResourceLocation.parse("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.SEVEN_COLOR.get())).renderType("cutout"));


        logBlock(((RotatedPillarBlock) ModBlocks.VERUS_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.VERUS_WOOD.get()), blockTexture(ModBlocks.VERUS_LOG.get()), blockTexture(ModBlocks.VERUS_LOG.get()));

        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_VERUS_LOG.get()), blockTexture(ModBlocks.STRIPPED_VERUS_LOG.get()),
                ResourceLocation.fromNamespaceAndPath(Example.MODID, "block/stripped_verus_log_top"));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_VERUS_WOOD.get()), blockTexture(ModBlocks.STRIPPED_VERUS_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_VERUS_LOG.get()));

        blockItem(ModBlocks.VERUS_LOG);
        blockItem(ModBlocks.VERUS_WOOD);
        blockItem(ModBlocks.STRIPPED_VERUS_LOG);
        blockItem(ModBlocks.STRIPPED_VERUS_WOOD);

        blockWithItem(ModBlocks.VERUS_PLANKS);

        leavesBlock(ModBlocks.VERUS_LEAVES);
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(Example.MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
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
