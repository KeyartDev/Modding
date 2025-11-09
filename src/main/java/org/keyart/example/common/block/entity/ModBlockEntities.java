package org.keyart.example.common.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.core.registry.BlockRegistry;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Example.MODID);

    public static final RegistryObject<BlockEntityType<SomeBlockPedistalBlockEntity>> SOME_PEDISTAL_BE =
            BLOCK_ENTITIES.register("some_pedistal_block_be", () ->
                    BlockEntityType.Builder.of(SomeBlockPedistalBlockEntity::new,
                            BlockRegistry.SOME_PEDISTAL_BLOCK.get()).build(null));


    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
