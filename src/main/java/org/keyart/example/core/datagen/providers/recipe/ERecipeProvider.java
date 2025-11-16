package org.keyart.example.core.datagen.providers.recipe;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import org.keyart.example.Example;
import org.keyart.example.core.datagen.providers.recipe.builder.FragTransformingRecipeBuilder;
import org.keyart.example.core.registry.ModBlocks;
import org.keyart.example.core.registry.ModItems;

import java.util.function.Consumer;

public class ERecipeProvider extends RecipeProvider {
    private static final ResourceLocation FRAG_TRANSFORMING_DIR =
            ResourceLocation.fromNamespaceAndPath(Example.MODID, "custom_builder/" + getItemName(ModItems.IT_ITEM.get()));


    public ERecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MAGIC_WAND.get(), 1)
                .pattern("gdg")
                .pattern(" g ")
                .pattern(" s ")
                .define('g', Items.GOLD_INGOT)
                .define('d', Items.DIAMOND)
                .define('s', Items.STICK)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "shaped/" + getItemName(ModItems.MAGIC_WAND.get())));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOME_BLOCK.get(), 1)
                .pattern("fff")
                .pattern("fff")
                .pattern("fff")
                .define('f', ModItems.SOME_BLOCK_FRAG.get())
                .unlockedBy(getHasName(ModItems.SOME_BLOCK_FRAG.get()), has(ModItems.SOME_BLOCK_FRAG.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "shaped/" + getItemName(ModBlocks.SOME_BLOCK.get())));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOME_PEDISTAL_BLOCK.get(), 1)
                .pattern(" e ")
                .pattern(" s ")
                .pattern(" s ")
                .define('e', ModItems.EXAMPLE_ITEM.get())
                .define('s', ModBlocks.SOME_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SOME_BLOCK.get()), has(ModBlocks.SOME_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "shaped/" + getItemName(ModBlocks.SOME_PEDISTAL_BLOCK.get())));



        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SOME_BLOCK_FRAG.get(), 9)
                .requires(ModBlocks.SOME_BLOCK.get())
                .unlockedBy("hasSomeBlock", has(ModBlocks.SOME_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID,
                        "shapeless/" + getItemName(ModItems.SOME_BLOCK_FRAG.get())));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MYSTIC_CLOCK.get(), 1)
                .requires(Items.CLOCK)
                .requires(ModItems.SOME_BLOCK_FRAG.get())
                .unlockedBy("hasSomeBlockFrag", has(ModItems.SOME_BLOCK_FRAG.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "shapeless/" + getItemName(ModItems.MYSTIC_CLOCK.get())));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NETHER_BRUSH.get(), 1)
                .requires(Items.BRUSH)
                .requires(Items.NETHER_BRICKS)
                .unlockedBy("hasNetherBrick", has(Blocks.NETHER_BRICKS))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "shapeless/" + getItemName(ModItems.NETHER_BRUSH.get())));


        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.SOME_BLOCK_ORE.get()),
                RecipeCategory.MISC, ModItems.SOME_BLOCK_FRAG.get(), 2.5f, 200)
                .unlockedBy("hasSomeBlockOre", has(ModBlocks.SOME_BLOCK_ORE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "smelted/" + getItemName(ModBlocks.SOME_BLOCK_ORE.get())));



        //------------------------------------------------------------------------CUSTOM RECIPE BUILDER------------------------------------------------------------------------
        FragTransformingRecipeBuilder.create(RecipeCategory.MISC, ModItems.IT_ITEM.get())
                .requires(Ingredient.of(ModItems.MYSTIC_CLOCK.get()), 1)
                .unlockedBy("hasMysticClock", has(ModItems.MYSTIC_CLOCK.get()))
                .save(pWriter, FRAG_TRANSFORMING_DIR);

        FragTransformingRecipeBuilder.create(RecipeCategory.MISC, Items.EMERALD, 2)
                .requires(Ingredient.of(ModItems.SOME_BLOCK_FRAG.get()), 1)
                .unlockedBy("hasSomeBlockFrag", has(ModItems.SOME_BLOCK_FRAG.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(Example.MODID, "custom_builder/" + getItemName(Items.EMERALD)));
    }
}
