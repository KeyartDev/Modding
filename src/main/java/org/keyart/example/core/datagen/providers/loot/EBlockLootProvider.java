package org.keyart.example.core.datagen.providers.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.core.registry.BlockRegistry;
import org.keyart.example.core.registry.ItemRegistry;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EBlockLootProvider extends BlockLootSubProvider {
    public EBlockLootProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(BlockRegistry.EXAMPLE_BLOCK.get());
        dropSelf(BlockRegistry.FUNC_BLOCK.get());

        addOreDrop(BlockRegistry.SOME_BLOCK_ORE.get(), ItemRegistry.SOME_BLOCK_FRAG.get(), 1, 2);
    }

    private void addOreDrop(Block oreBlock, Item drop, int min, int max) {
        add(oreBlock, createSilkTouchDispatchTable(oreBlock,
                applyExplosionDecay(oreBlock,
                        LootItem.lootTableItem(drop)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Stream.of(
                BlockRegistry.BLOCKS.getEntries().stream()
        )
                .flatMap(Function.identity())
                .map(RegistryObject::get)
                .collect(Collectors.toList());
    }
}
