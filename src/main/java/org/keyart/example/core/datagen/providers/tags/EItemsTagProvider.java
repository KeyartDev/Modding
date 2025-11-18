package org.keyart.example.core.datagen.providers.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.keyart.example.Example;
import org.keyart.example.core.registry.ModBlocks;
import org.keyart.example.core.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class EItemsTagProvider extends ItemTagsProvider {


    public EItemsTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                             CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, Example.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ItemTags.MUSIC_DISCS)
                .add(ModItems.WELL_KNOWN_SONG_DISK.get());

        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.VERUS_LOG.get().asItem())
                .add(ModBlocks.VERUS_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_VERUS_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_VERUS_WOOD.get().asItem());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.VERUS_PLANKS.get().asItem());
    }
}
