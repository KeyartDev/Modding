package org.keyart.example.core.datagen.providers.recipe;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.keyart.example.Example;
import org.keyart.example.core.registry.BlockRegistry;
import org.keyart.example.core.registry.ItemRegistry;

import java.util.function.Consumer;

public class ERecipeProvider extends RecipeProvider {
    public ERecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MAGIC_WAND.get(), 1)
                .pattern("gdg")
                .pattern(" g ")
                .pattern(" s ")
                .define('g', Items.GOLD_INGOT)
                .define('d', Items.DIAMOND)
                .define('s', Items.STICK)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "shaped/" + getItemName(ItemRegistry.MAGIC_WAND.get())));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.EXAMPLE_BLOCK.get(), 1)
                .pattern("fff")
                .pattern("fff")
                .pattern("fff")
                .define('f', ItemRegistry.SOME_BLOCK_FRAG.get())
                .unlockedBy(getHasName(ItemRegistry.SOME_BLOCK_FRAG.get()), has(ItemRegistry.SOME_BLOCK_FRAG.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "shaped/" + getItemName(BlockRegistry.EXAMPLE_BLOCK.get())));



        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SOME_BLOCK_FRAG.get(), 9)
                .requires(BlockRegistry.EXAMPLE_BLOCK.get())
                .unlockedBy("hasSomeBlock", has(BlockRegistry.EXAMPLE_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID,
                        "shapeless/" + getItemName(ItemRegistry.SOME_BLOCK_FRAG.get())));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.MYSTIC_CLOCK.get(), 1)
                .requires(Items.CLOCK)
                .requires(ItemRegistry.SOME_BLOCK_FRAG.get())
                .unlockedBy("hasSomeBlockFrag", has(ItemRegistry.SOME_BLOCK_FRAG.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "shapeless/" + getItemName(ItemRegistry.MYSTIC_CLOCK.get())));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.NETHER_BRUSH.get(), 1)
                .requires(Items.BRUSH)
                .requires(Items.NETHER_BRICKS)
                .unlockedBy("hasNetherBrick", has(Blocks.NETHER_BRICKS))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "shapeless/" + getItemName(ItemRegistry.NETHER_BRUSH.get())));


        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.SOME_BLOCK_ORE.get()),
                RecipeCategory.MISC, ItemRegistry.SOME_BLOCK_FRAG.get(), 2.5f, 200)
                .unlockedBy("hasSomeBlockOre", has(BlockRegistry.SOME_BLOCK_ORE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "smelted/" + getItemName(BlockRegistry.SOME_BLOCK_ORE.get())));
    }
}
