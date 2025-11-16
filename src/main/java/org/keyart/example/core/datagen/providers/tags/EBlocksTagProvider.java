package org.keyart.example.core.datagen.providers.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.keyart.example.Example;
import org.keyart.example.core.registry.ModBlocks;
import org.keyart.example.core.tags.CustomBlockTags;

import java.util.concurrent.CompletableFuture;

public class EBlocksTagProvider extends BlockTagsProvider {
    public EBlocksTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Example.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        registerMineableTags();
        registerToolRequirementsTags();

        tag(CustomBlockTags.STONE_BRICKS)
                .add(Blocks.STONE)
                .add(Blocks.ANDESITE)
                .add(Blocks.DIORITE)
                .add(Blocks.GRANITE);

        tag(BlockTags.FENCES)
                .add(ModBlocks.SOME_FENCE.get());

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.SOME_FENCE_GATE.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.SOME_WALL.get());


        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.VERUS_LOG.get())
                .add(ModBlocks.VERUS_WOOD.get())
                .add(ModBlocks.STRIPPED_VERUS_LOG.get())
                .add(ModBlocks.STRIPPED_VERUS_WOOD.get());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.VERUS_PLANKS.get());
    }

    private void registerMineableTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SOME_BLOCK.get())
                .add(ModBlocks.SOME_BLOCK_ORE.get())
                .add(ModBlocks.FUNC_BLOCK.get());
    }

    private void registerToolRequirementsTags() {
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SOME_BLOCK_ORE.get())
                .add(ModBlocks.SOME_BLOCK.get());

        tag(CustomBlockTags.NEEDS_SOME_TOOL)
                .add(ModBlocks.FUNC_BLOCK.get());
    }

}
