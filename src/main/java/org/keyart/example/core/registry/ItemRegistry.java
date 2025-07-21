package org.keyart.example.core.registry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import org.keyart.example.Example;
import org.keyart.example.common.item.*;

import java.util.List;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Example.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM =
            ITEMS.register("example_item", ExampleItem::new);
    public static final RegistryObject<Item> SOME_BLOCK_FRAG =
            ITEMS.register("some_block_frag", () ->
                    new Item(new Item.Properties()
                            .stacksTo(64)
                            .fireResistant()) {
                        @Override
                        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                            pTooltipComponents.add(Component.literal("Предмет для тестирования рецепта крафта.").withStyle(ChatFormatting.GRAY));
                        }
                    });

    public static final RegistryObject<Item> MAGIC_WAND =
            ITEMS.register("magic_wand", MagicWandItem::new);

    public static final RegistryObject<Item> NETHER_BRUSH =
            ITEMS.register("nether_brush", NetherBrushItem::new);

    public static final RegistryObject<Item> MYSTIC_CLOCK =
            ITEMS.register("mystic_clock", MysticClockItem::new);

    public static final RegistryObject<Item> IT_ITEM =
            ITEMS.register("it_item", InteractTestsItem::new);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
