package org.keyart.example.core.datagen.providers.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
        dropSelf(BlockRegistry.SOME_BLOCK.get());
        dropSelf(BlockRegistry.FUNC_BLOCK.get());

        dropSelf(BlockRegistry.SOME_FENCE.get());
        dropSelf(BlockRegistry.SOME_FENCE_GATE.get());
        dropSelf(BlockRegistry.SOME_WALL.get());
        dropSelf(BlockRegistry.SOME_BUTTON.get());
        dropSelf(BlockRegistry.SOME_PRESSURE_PLATE.get());
        dropSelf(BlockRegistry.SOME_STAIRS.get());

        add(BlockRegistry.SOME_SLAB.get(),
                block -> createSlabItemTable(BlockRegistry.SOME_SLAB.get()));

        addOreDrop(BlockRegistry.SOME_BLOCK_ORE.get(), ItemRegistry.SOME_BLOCK_FRAG.get(), 1, 2);

        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(BlockRegistry.STRAWBERRY_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StrawberryCropBlock.AGE, 5));

        add(BlockRegistry.STRAWBERRY_CROP.get(),
                createCropDrops(BlockRegistry.STRAWBERRY_CROP.get(), ItemRegistry.STRAWBERRY.get(), ItemRegistry.STRAWBERRY_SEEDS.get(), lootitemcondition$builder));

        dropSelf(BlockRegistry.SEVEN_COLOR.get());
        add(BlockRegistry.SEVEN_COLOR_POTTED.get(), createPotFlowerItemTable(BlockRegistry.SEVEN_COLOR.get()));
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
