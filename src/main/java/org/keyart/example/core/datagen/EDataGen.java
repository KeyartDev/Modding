package org.keyart.example.core.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.keyart.example.Example;
import org.keyart.example.core.datagen.providers.loot.EBlockLootProvider;
import org.keyart.example.core.datagen.providers.models.EBlockStateProvider;
import org.keyart.example.core.datagen.providers.models.EItemModelProvider;
import org.keyart.example.core.datagen.providers.recipe.ERecipeProvider;
import org.keyart.example.core.datagen.providers.tags.EBlocksTagProvider;
import org.keyart.example.core.datagen.providers.tags.EItemsTagProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Example.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EDataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        if (event.includeClient())
            addClientProviders(generator, packOutput, fileHelper);
        
        if (event.includeServer())
            addServerProviders(generator, packOutput, fileHelper, lookupProvider);
    }

    private static void addClientProviders(DataGenerator generator, PackOutput packOutput,
                                           ExistingFileHelper fileHelper) {
        generator.addProvider(true, new EBlockStateProvider(packOutput, fileHelper));
        generator.addProvider(true, new EItemModelProvider(packOutput, fileHelper));
    }

    private static void addServerProviders(DataGenerator generator, PackOutput packOutput,
                                           ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        generator.addProvider(true, new ERecipeProvider(packOutput));

        EBlocksTagProvider blocksTagProvider = generator.addProvider(true, new EBlocksTagProvider(packOutput, lookupProvider, fileHelper));

        generator.addProvider(true, new EItemsTagProvider(packOutput, lookupProvider, blocksTagProvider.contentsGetter(), fileHelper));

        generator.addProvider(true, new LootTableProvider(packOutput, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(EBlockLootProvider::new, LootContextParamSets.BLOCK))));
    }
}






















