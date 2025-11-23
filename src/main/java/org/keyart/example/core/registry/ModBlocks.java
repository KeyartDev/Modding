package org.keyart.example.core.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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
import org.keyart.example.common.block.custom.DiceBlock;
import org.keyart.example.common.block.custom.ModFlammableRotatedPillarBlock;
import org.keyart.example.common.block.custom.ModPortalBlock;
import org.keyart.example.common.block.custom.SomePedistalBlock;
import org.keyart.example.common.block.ore.SomeBlockOre;
import org.keyart.example.core.worldgen.tree.VerusTreeGrower;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Example.MODID);

    public static final RegistryObject<Block> SOME_BLOCK =
            registerBlock("some_block", SomeBlock::new);

    public static final RegistryObject<Block> SOME_STAIRS =
            registerBlock("some_stairs", () -> new StairBlock(
                    () -> ModBlocks.SOME_BLOCK.get().defaultBlockState(),
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
                    new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.SEVEN_COLOR,
                            BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)
                                    .noOcclusion())
            );

    public static final RegistryObject<Block> SOME_PEDISTAL_BLOCK =
            registerBlock("some_pedistal_block", () ->
                    new SomePedistalBlock(BlockBehaviour.Properties.copy(SOME_BLOCK.get()).noOcclusion()));



    public static final RegistryObject<Block> VERUS_LOG =
            registerBlock("verus_log", () ->
                    new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));

    public static final RegistryObject<Block> VERUS_WOOD =
            registerBlock("verus_wood", () ->
                    new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f)));

    public static final RegistryObject<Block> STRIPPED_VERUS_LOG =
            registerBlock("stripped_verus_log", () ->
                    new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(3f)));

    public static final RegistryObject<Block> STRIPPED_VERUS_WOOD =
            registerBlock("stripped_verus_wood", () ->
                    new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(3f)));


    public static final RegistryObject<Block> VERUS_PLANKS =
            registerBlock("verus_planks", () ->
                    new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                        @Override
                        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return true;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return 20;
                        }

                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return 5;
                        }
                    });

    public static final RegistryObject<Block> VERUS_LEAVES =
            registerBlock("verus_leaves", () ->
                    new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
                        @Override
                        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return true;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return 60;
                        }

                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return 30;
                        }
                    });

    public static final RegistryObject<Block> DICE_BLOCK =
            BLOCKS.register("dice_block", () -> new DiceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noLootTable()));

    public static final RegistryObject<Block> VERUS_SAPLING =
            registerBlock("verus_sapling", () -> new SaplingBlock(new VerusTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));


    public static final RegistryObject<Block> MOD_PORTAL =
            registerBlock("mod_portal", () -> new ModPortalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noLootTable().noOcclusion().noCollission()));

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
