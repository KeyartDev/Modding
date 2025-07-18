package org.keyart.example.core.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.common.block.FuncBlock;
import org.keyart.example.common.block.SomeBlock;
import org.keyart.example.common.block.ore.SomeBlockOre;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Example.MODID);

    public static final RegistryObject<Block> EXAMPLE_BLOCK =
            registerBlock("some_block", SomeBlock::new);

    public static final RegistryObject<Block> SOME_BLOCK_ORE =
            registerBlock("some_block_ore", SomeBlockOre::new);

    public static final RegistryObject<Block> FUNC_BLOCK =
            registerBlock("func_block", FuncBlock::new);


    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
