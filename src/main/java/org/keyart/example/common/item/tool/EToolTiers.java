package org.keyart.example.common.item.tool;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import org.keyart.example.Example;
import org.keyart.example.core.registry.ItemRegistry;
import org.keyart.example.core.tags.CustomBlockTags;
import org.keyart.example.core.tags.CustomItemTags;

import java.util.List;

public class EToolTiers {
    public static final Tier SOME = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1500, 5f, 3f, 25,
                    CustomBlockTags.NEEDS_SOME_TOOL, () ->
                    Ingredient.of(ItemRegistry.SOME_BLOCK_FRAG.get())),
            ResourceLocation.fromNamespaceAndPath(Example.MODID, "some_frag"),
            List.of(Tiers.NETHERITE), List.of()
    );
}
