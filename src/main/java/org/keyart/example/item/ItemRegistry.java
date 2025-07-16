package org.keyart.example.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Example.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM =
            ITEMS.register("example_item", ExampleItem::new);
    public static final RegistryObject<Item> SOME_BLOCK_FRAG =
            ITEMS.register("some_block_frag", () ->
                    new Item(new Item.Properties()
                            .stacksTo(64)
                            .fireResistant()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
