package org.keyart.example.core.datagen.providers.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import org.keyart.example.Example;
import org.keyart.example.common.loot.AddItemModifier;
import org.keyart.example.core.registry.ModItems;

public class EGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public EGlobalLootModifiersProvider(PackOutput output) {
        super(output, Example.MODID);
    }

    @Override
    protected void start() {
        add("nether_brush_from_fortress",
                new AddItemModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition.builder(
                                        ResourceLocation.parse("minecraft:chests/nether_bridge")
                                ).build(),
                                LootItemRandomChanceCondition.randomChance(0.3f).build()
                        },
                        ModItems.NETHER_BRUSH.get()
                )
        );

        add("mystic_clock_form_shipwreck",
                new AddItemModifier(
                        new LootItemCondition[]{
                                LootTableIdCondition.builder(
                                        ResourceLocation.parse("minecraft:chests/shipwreck_supply")
                                ).build(),
                                LootItemRandomChanceCondition.randomChance(0.2f).build()
                        },
                        ModItems.MYSTIC_CLOCK.get()
                )
        );

        add("diamonds_from_zombie",
                new AddItemModifier(
                        new LootItemCondition[]{
                                LootTableIdCondition.builder(
                                        ResourceLocation.parse("minecraft:entities/zombie")
                                ).build()
                        },
                        Items.DIAMOND,
                        3
                ));
    }
}
