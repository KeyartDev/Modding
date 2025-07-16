package org.keyart.example.creative_tabs;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.block.BlockRegistry;
import org.keyart.example.item.ItemRegistry;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Example.MODID);


    public static final RegistryObject<CreativeModeTab> EXAMPLE_CREATIVE_TAB =
            CREATIVE_MODE_TABS.register("example_creative_tab", () ->
                    CreativeModeTab.builder()
                            .title(Component.keybind("creativeTab.example.example_creative_tab")) //Для перевода заголовка создаётся ключ
                            .icon(() ->
                                    new ItemStack(ItemRegistry.EXAMPLE_ITEM.get()))
                            .displayItems(((pParameters, pOutput) -> { //Далее идёт список отображаемых предметов
                                pOutput.accept(ItemRegistry.EXAMPLE_ITEM.get());
                                pOutput.accept(BlockRegistry.EXAMPLE_BLOCK.get());
                                pOutput.accept(BlockRegistry.SOME_BLOCK_ORE.get());
                                pOutput.accept(ItemRegistry.SOME_BLOCK_FRAG.get());
                            })).build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
