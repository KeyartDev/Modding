package org.keyart.example.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Example.MODID);


    public static final RegistryObject<CreativeModeTab> EXAMPLE_CREATIVE_TAB =
            CREATIVE_MODE_TABS.register("example_creative_tab", () ->
                    CreativeModeTab.builder()
                            .title(Component.keybind("creativeTab.example.example_creative_tab")) //Для перевода заголовка создаётся ключ
                            .icon(() ->
                                    new ItemStack(ModItems.EXAMPLE_ITEM.get()))
                            .displayItems(((pParameters, pOutput) -> { //Далее идёт список отображаемых предметов
                                pOutput.accept(ModItems.EXAMPLE_ITEM.get());
                                pOutput.accept(ModBlocks.SOME_BLOCK.get());
                                pOutput.accept(ModBlocks.SOME_BLOCK_ORE.get());
                                pOutput.accept(ModItems.SOME_BLOCK_FRAG.get());
                                pOutput.accept(ModItems.MAGIC_WAND.get());
                                pOutput.accept(ModItems.NETHER_BRUSH.get());
                                pOutput.accept(ModItems.MYSTIC_CLOCK.get());
                                pOutput.accept(ModBlocks.FUNC_BLOCK.get());
                                pOutput.accept(ModItems.IT_ITEM.get());

                                pOutput.accept(ModBlocks.SOME_STAIRS.get());
                                pOutput.accept(ModBlocks.SOME_SLAB.get());
                                pOutput.accept(ModBlocks.SOME_FENCE.get());
                                pOutput.accept(ModBlocks.SOME_FENCE_GATE.get());
                                pOutput.accept(ModBlocks.SOME_BUTTON.get());
                                pOutput.accept(ModBlocks.SOME_WALL.get());
                                pOutput.accept(ModBlocks.SOME_PRESSURE_PLATE.get());

                                pOutput.accept(ModItems.SOME_SWORD.get());
                                pOutput.accept(ModItems.SOME_AXE.get());
                                pOutput.accept(ModItems.SOME_PICKAXE.get());
                                pOutput.accept(ModItems.SOME_SHOVEL.get());
                                pOutput.accept(ModItems.SOME_HOE.get());

                                pOutput.accept(ModItems.STRAWBERRY_SEEDS.get());
                                pOutput.accept(ModItems.STRAWBERRY.get());

                                pOutput.accept(ModBlocks.SEVEN_COLOR.get());

                                pOutput.accept(ModItems.KIND_NECRO_DISK.get());

                                pOutput.accept(ModBlocks.SOME_PEDISTAL_BLOCK.get());

                                pOutput.accept(ModBlocks.VERUS_LOG.get());
                                pOutput.accept(ModBlocks.STRIPPED_VERUS_LOG.get());
                                pOutput.accept(ModBlocks.VERUS_WOOD.get());
                                pOutput.accept(ModBlocks.STRIPPED_VERUS_WOOD.get());
                                pOutput.accept(ModBlocks.VERUS_PLANKS.get());
                                pOutput.accept(ModBlocks.VERUS_LEAVES.get());

                                pOutput.accept(ModItems.DICE_ITEM.get());
                            })).build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
