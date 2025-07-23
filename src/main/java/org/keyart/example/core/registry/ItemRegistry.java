package org.keyart.example.core.registry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import org.keyart.example.Example;
import org.keyart.example.common.item.*;
import org.keyart.example.common.item.tool.EToolTiers;

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


    public static final RegistryObject<Item> SOME_SWORD =
            ITEMS.register("some_sword", () ->
                    new SwordItem(EToolTiers.SOME, 4, -2.3f, new Item.Properties()));

    public static final RegistryObject<Item> SOME_SHOVEL =
            ITEMS.register("some_shovel", () ->
                    new ShovelItem(EToolTiers.SOME, 1, 1.1f, new Item.Properties()));

    public static final RegistryObject<Item> SOME_PICKAXE =
            ITEMS.register("some_pickaxe", () ->
                    new PickaxeItem(EToolTiers.SOME, 2, 1.4f, new Item.Properties()));

    public static final RegistryObject<Item> SOME_AXE =
            ITEMS.register("some_axe", () ->
                    new AxeItem(EToolTiers.SOME, 5, 0.8f, new Item.Properties()));

    public static final RegistryObject<Item> SOME_HOE =
            ITEMS.register("some_hoe", () ->
                    new HoeItem(EToolTiers.SOME, 1, 1.1f, new Item.Properties()));


    public static final RegistryObject<Item> STRAWBERRY_SEEDS =
            ITEMS.register("strawberry_seeds", () ->
                    new ItemNameBlockItem(BlockRegistry.STRAWBERRY_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> STRAWBERRY =
            ITEMS.register("strawberry", () ->
                    new Item(new Item.Properties()
                            .food(new FoodProperties.Builder()
                                    .alwaysEat()
                                    .nutrition(1)
                                    .saturationMod(0.5f).build())));

    public static final RegistryObject<Item> KIND_NECRO_DISK =
            ITEMS.register("kind_necro_disk", () ->
                    new RecordItem(6, SoundRegistry.KIND_NECRO, new Item.Properties().stacksTo(1), 3350));



    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
