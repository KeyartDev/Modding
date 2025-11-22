package org.keyart.example.core.worldgen.tree;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.core.worldgen.tree.custom.VerusTrunkPlacer;

public class ModTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Example.MODID);

    public static final RegistryObject<TrunkPlacerType<VerusTrunkPlacer>> VERUS_TRUNK_PLACER =
            TRUNK_PLACER.register("verus_trunk_placer", () -> new TrunkPlacerType<>(VerusTrunkPlacer.CODEC));

    public static void register(IEventBus bus) {
        TRUNK_PLACER.register(bus);
    }
}
