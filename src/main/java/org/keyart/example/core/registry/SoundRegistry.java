package org.keyart.example.core.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Example.MODID);

    public static final RegistryObject<SoundEvent> PIP_SOUND = registerSoundEvents("pip_sound_ev");

    public static final RegistryObject<SoundEvent> KIND_NECRO = registerSoundEvents("kind_necro");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Example.MODID, name)));
    }


    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}
