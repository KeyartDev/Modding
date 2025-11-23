package org.keyart.example.core.worldgen.biome;

import net.minecraft.resources.ResourceLocation;
import org.keyart.example.Example;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes() {
        //weight показыват как часто будет использоваться пользовательский регион
        Regions.register(new ModOverworldRegion(ResourceLocation.fromNamespaceAndPath(Example.MODID, "overworld"), 5));
    }
}
