package org.keyart.example.core.registry;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.common.block.FuncBlock;
import org.keyart.example.common.block.SomeBlock;
import org.keyart.example.common.block.crop.StrawberryCropBlock;
import org.keyart.example.common.block.custom.SomePedistalBlock;
import org.keyart.example.common.block.ore.SomeBlockOre;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Example.MODID);

    public static final RegistryObject<Block> SOME_BLOCK =
            registerBlock("some_block", SomeBlock::new);

    public static final RegistryObject<Block> SOME_STAIRS =
            registerBlock("some_stairs", () -> new StairBlock(
                    () -> BlockRegistry.SOME_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(SOME_BLOCK.get())
            ));

    public static final RegistryObject<Block> SOME_SLAB =
            registerBlock("some_slab", () ->
                    new SlabBlock(BlockBehaviour.Properties.copy(SOME_BLOCK.get())));

    public static final RegistryObject<Block> SOME_BUTTON =
            registerBlock("some_button", () ->
                    new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON),
                            BlockSetType.STONE, 30, true));

    public static final RegistryObject<Block> SOME_PRESSURE_PLATE =
            registerBlock("some_pressure_plate", () ->
                    new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                            BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE),
                            BlockSetType.STONE));

    public static final RegistryObject<Block> SOME_FENCE =
            registerBlock("some_fence", () ->
                    new FenceBlock(BlockBehaviour.Properties.copy(SOME_BLOCK.get())));

    public static final RegistryObject<Block> SOME_FENCE_GATE =
            registerBlock("some_fence_gate", () ->
                    new FenceGateBlock(BlockBehaviour.Properties.copy(SOME_BLOCK.get()),
                            WoodType.JUNGLE));

    public static final RegistryObject<Block> SOME_WALL =
            registerBlock("some_wall", () ->
                    new WallBlock(BlockBehaviour.Properties.copy(SOME_BLOCK.get())));



    public static final RegistryObject<Block> SOME_BLOCK_ORE =
            registerBlock("some_block_ore", SomeBlockOre::new);

    public static final RegistryObject<Block> FUNC_BLOCK =
            registerBlock("func_block", FuncBlock::new);


    public static final RegistryObject<Block> STRAWBERRY_CROP =
            BLOCKS.register("strawberry_crop", () ->
                    new StrawberryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));


    public static final RegistryObject<Block> SEVEN_COLOR =
            registerBlock("seven_color_flower", () ->
                    new FlowerBlock(() -> MobEffects.LUCK, 5,
                            BlockBehaviour.Properties.copy(Blocks.ALLIUM)
                                    .noCollission()
                                    .noOcclusion())
            );

    public static final RegistryObject<Block> SEVEN_COLOR_POTTED =
            BLOCKS.register("potted_seven_color_flower", () ->
                    new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), BlockRegistry.SEVEN_COLOR,
                            BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)
                                    .noOcclusion())
            );

    public static final RegistryObject<Block> SOME_PEDISTAL_BLOCK =
            registerBlock("some_pedistal_block", () ->
                    new SomePedistalBlock(BlockBehaviour.Properties.copy(SOME_BLOCK.get()).noOcclusion()));


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
