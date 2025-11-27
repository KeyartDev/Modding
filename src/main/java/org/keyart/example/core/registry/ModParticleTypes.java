package org.keyart.example.core.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.common.client.particle.ModParticle;

@Mod.EventBusSubscriber(modid = Example.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Example.MODID);

    public static final RegistryObject<ParticleType<ModParticle.Options>> MOD_PARTICLE =
            PARTICLES.register("mod_particle", ModParticle.Provider.Type::new);

    public static void register(IEventBus bus) {
        PARTICLES.register(bus);
    }

    @SubscribeEvent
    public static void onParticleRegistry(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(MOD_PARTICLE.get(), ModParticle.Provider::new);
    }
}
