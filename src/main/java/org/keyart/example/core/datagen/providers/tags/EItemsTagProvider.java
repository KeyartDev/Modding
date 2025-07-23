package org.keyart.example.core.datagen.providers.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.keyart.example.Example;
import org.keyart.example.common.item.tool.EToolTiers;
import org.keyart.example.core.registry.ItemRegistry;
import org.keyart.example.core.tags.CustomBlockTags;

import java.util.concurrent.CompletableFuture;

public class EItemsTagProvider extends ItemTagsProvider {


    public EItemsTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                             CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, Example.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ItemTags.MUSIC_DISCS)
                .add(ItemRegistry.KIND_NECRO_DISK.get());
    }
}
