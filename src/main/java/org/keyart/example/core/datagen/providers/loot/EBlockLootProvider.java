package org.keyart.example.core.datagen.providers.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.common.block.crop.StrawberryCropBlock;
import org.keyart.example.core.registry.ModBlocks;
import org.keyart.example.core.registry.ModItems;

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
        dropSelf(ModBlocks.SOME_BLOCK.get());
        dropSelf(ModBlocks.FUNC_BLOCK.get());

        dropSelf(ModBlocks.SOME_FENCE.get());
        dropSelf(ModBlocks.SOME_FENCE_GATE.get());
        dropSelf(ModBlocks.SOME_WALL.get());
        dropSelf(ModBlocks.SOME_BUTTON.get());
        dropSelf(ModBlocks.SOME_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.SOME_STAIRS.get());
        dropSelf(ModBlocks.SOME_PEDISTAL_BLOCK.get());
        dropSelf(ModBlocks.VERUS_SAPLING.get());

        add(ModBlocks.SOME_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SOME_SLAB.get()));

        addOreDrop(ModBlocks.SOME_BLOCK_ORE.get(), ModItems.SOME_BLOCK_FRAG.get(), 1, 2);

        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.STRAWBERRY_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StrawberryCropBlock.AGE, 5));

        add(ModBlocks.STRAWBERRY_CROP.get(),
                createCropDrops(ModBlocks.STRAWBERRY_CROP.get(), ModItems.STRAWBERRY.get(), ModItems.STRAWBERRY_SEEDS.get(), lootitemcondition$builder));

        dropSelf(ModBlocks.SEVEN_COLOR.get());
        add(ModBlocks.SEVEN_COLOR_POTTED.get(), createPotFlowerItemTable(ModBlocks.SEVEN_COLOR.get()));


        dropSelf(ModBlocks.VERUS_LOG.get());
        dropSelf(ModBlocks.VERUS_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_VERUS_LOG.get());
        dropSelf(ModBlocks.STRIPPED_VERUS_WOOD.get());
        dropSelf(ModBlocks.VERUS_PLANKS.get());
        add(ModBlocks.VERUS_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.VERUS_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
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
                ModBlocks.BLOCKS.getEntries().stream()
        )
                .flatMap(Function.identity())
                .map(RegistryObject::get)
                .collect(Collectors.toList());
    }
}
