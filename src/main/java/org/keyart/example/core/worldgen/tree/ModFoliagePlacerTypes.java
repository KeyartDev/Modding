package org.keyart.example.core.worldgen.tree;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.core.worldgen.tree.custom.VerusFoliagePlacer;

public class ModFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, Example.MODID);

    public static final RegistryObject<FoliagePlacerType<VerusFoliagePlacer>> VERUS_FOLIAGE_PLACER =
            FOLIAGE_PLACER.register("verus_foliage_placer", () -> new FoliagePlacerType<>(VerusFoliagePlacer.CODEC));

    public static void register(IEventBus bus) {
        FOLIAGE_PLACER.register(bus);
    }
}
