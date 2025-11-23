package org.keyart.example.core.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.keyart.example.Example;
import org.keyart.example.core.registry.ModEntities;
import org.keyart.example.core.registry.ModSounds;
import org.keyart.example.core.worldgen.ModPlacedFeatures;

public class ModBiomes {
    public static final ResourceKey<Biome> TEST_BIOME =
            ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Example.MODID, "test_biome"));

    public static void bootstrap(BootstapContext<Biome> context) {
        context.register(TEST_BIOME, testBiome(context));
    }


    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) { //Дефолтные значения
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }


    /**
     * Единственное место, где можно посмотреть пример создания биомов - это класс {@link net.minecraft.data.worldgen.biome.OverworldBiomes}
     */
    public static Biome testBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder(); //Билдер для добавления спавна сущностей в биоме
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.ANKI.get(), 6, 1, 2));

        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));

        BiomeDefaultFeatures.farmAnimals(spawnBuilder); //Сельские мобы
        BiomeDefaultFeatures.commonSpawns(spawnBuilder); //Стандартные спавны

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        //НЕОБХОДИМО СЛЕДОВАТЬ ТОМУ ЖЕ ПОРЯДКУ, ЧТО И В ВАНИЛЬНЫХ БИОМАХ ДЛЯ BiomeDefaultFeatures
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addMossyStoneBlock(biomeBuilder);
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addExtraGold(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.SOME_ORE_PLACED_KEY);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.VERUS_PLACED_KEY); //Добавление своего дерева в генерацию

        return new Biome.BiomeBuilder() //Возвращаемый результат - билдер.
                .hasPrecipitation(true) //Наличие осадков
                .downfall(0.8f) //Обвалы(?)
                .temperature(0.7f) //Температура
                .generationSettings(biomeBuilder.build()) //Конфигурация генерации
                .mobSpawnSettings(spawnBuilder.build()) //Конфигурация спавна
                .specialEffects((new BiomeSpecialEffects.Builder()) //Особые эффекты:
                        .waterColor(0xe82e3b) //Цвет воды
                        .waterFogColor(0xbf1b26) //Цвет тумана в воде
                        .skyColor(0x30c918) //Цвет неба
                        .grassColorOverride(0x7f03fc) //Цвет растительности
                        .foliageColorOverride(0xd203fc) //Цвет листвы
                        .fogColor(0x22a1e6) //Цвет тумана
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS) //Звуки эмбиента
                        .backgroundMusic(Musics.createGameMusic(ModSounds.WELL_KNOWN_SONG.getHolder().get())).build()) //Фоновая музыка
                .build();
    }
}
