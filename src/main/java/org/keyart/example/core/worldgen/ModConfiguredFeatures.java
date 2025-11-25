package org.keyart.example.core.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import org.keyart.example.Example;
import org.keyart.example.core.registry.ModBlocks;
import org.keyart.example.core.worldgen.tree.custom.VerusFoliagePlacer;
import org.keyart.example.core.worldgen.tree.custom.VerusTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_SOME_ORE_KEY = registerKey("some_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> VERUS_KEY = registerKey("verus");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SEVENCOLOR_KEY = registerKey("sevencolor");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldSomeOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.SOME_BLOCK_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_SOME_ORE_KEY, Feature.ORE, new OreConfiguration(overworldSomeOres, 9));

        register(context, VERUS_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.VERUS_LOG.get()),
                new VerusTrunkPlacer(5, 3, 1),
                BlockStateProvider.simple(ModBlocks.VERUS_LEAVES.get()),
                new VerusFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 2)).build());

        register(context, SEVENCOLOR_KEY, Feature.FLOWER, new RandomPatchConfiguration(64, 4, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.SEVEN_COLOR.get())))));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Example.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
