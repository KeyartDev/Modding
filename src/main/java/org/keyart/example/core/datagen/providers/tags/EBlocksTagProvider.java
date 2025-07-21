package org.keyart.example.core.datagen.providers.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.keyart.example.Example;
import org.keyart.example.core.registry.BlockRegistry;
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
                .add(BlockRegistry.SOME_FENCE.get());

        tag(BlockTags.FENCE_GATES)
                .add(BlockRegistry.SOME_FENCE_GATE.get());

        tag(BlockTags.WALLS)
                .add(BlockRegistry.SOME_WALL.get());
    }

    private void registerMineableTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockRegistry.SOME_BLOCK.get())
                .add(BlockRegistry.SOME_BLOCK_ORE.get());
    }

    private void registerToolRequirementsTags() {
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(BlockRegistry.SOME_BLOCK_ORE.get())
                .add(BlockRegistry.SOME_BLOCK.get());
    }

}
