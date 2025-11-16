package org.keyart.example.core.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.common.entity.AnkiEntity;
import org.keyart.example.common.entity.projectiles.DiceProjectileEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Example.MODID);

    public static final RegistryObject<EntityType<AnkiEntity>> ANKI =
            ENTITY_TYPES.register("anki", () ->
                    EntityType.Builder.of(AnkiEntity::new, MobCategory.CREATURE).sized(1, 1.5f).build("anki"));

    public static final RegistryObject<EntityType<DiceProjectileEntity>> DICE_PROJECTILE =
            ENTITY_TYPES.register("dice_projectile", () -> EntityType.Builder.<DiceProjectileEntity>of(DiceProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("dice_projectile"));

    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
