package org.keyart.example.core.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, Example.MODID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Example.MODID);


    public static final RegistryObject<PoiType> FUNC_POI =
            POI_TYPES.register("func_poi", () ->
                    new PoiType(ImmutableSet.copyOf(ModBlocks.FUNC_BLOCK.get().getStateDefinition().getPossibleStates()),
                            1, 1));

    public static final RegistryObject<VillagerProfession> FUNC_EXPERT =
            VILLAGER_PROFESSIONS.register("func_expert", () -> new VillagerProfession("func_expert",
                    poiTypeHolder -> poiTypeHolder.get() == FUNC_POI.get(), poiTypeHolder -> poiTypeHolder.get() == FUNC_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));


    public static void register(IEventBus bus) {
        POI_TYPES.register(bus);
        VILLAGER_PROFESSIONS.register(bus);
    }
}
