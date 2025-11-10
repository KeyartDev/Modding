package org.keyart.example.core.datagen.providers.recipe.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import org.keyart.example.core.recipes.FragTranformingRecipe;

import java.util.List;
import java.util.function.Consumer;

public class FragTransformingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private @Nullable String group;

    public FragTransformingRecipeBuilder(RecipeCategory category, Item result, int count) {
        this.category = category;
        this.result = result;
        this.count = count;
    }

    public static FragTransformingRecipeBuilder create(RecipeCategory category, Item result, int count) {
        return new FragTransformingRecipeBuilder(category, result, count);
    }

    public static FragTransformingRecipeBuilder create(RecipeCategory category, Item result) {
        return new FragTransformingRecipeBuilder(category, result, 1);
    }

    public FragTransformingRecipeBuilder requires(Ingredient item, int quantity) {
        for(int i = 0; i < quantity; ++i) {
            this.ingredients.add(item);
        }

        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe",
                RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, pRecipeId.withPrefix("recipes/" + this.category.getFolderName() + "/"),
                this.advancement, this.ingredients, this.result, this.count, this.group == null ? "" : this.group));
    }

    public static class Result implements FinishedRecipe {

        private final ResourceLocation id;
        private final @Nullable ResourceLocation advancementId;
        private final Advancement.Builder advancement;
        private final List<Ingredient> ingredients;
        private final Item result;
        private final int count;
        private String group;

        public Result(ResourceLocation id, @Nullable ResourceLocation advancementId, Advancement.Builder advancement, List<Ingredient> ingredients, Item result, int count, String group) {
            this.id = id;
            this.advancementId = advancementId;
            this.advancement = advancement;
            this.ingredients = ingredients;
            this.result = result;
            this.count = count;
            this.group = group;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            JsonArray jsonarray = new JsonArray();

            for(Ingredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }

            pJson.add("ingredients", jsonarray);

            JsonObject object = new JsonObject();
            if (this.count > 1) {
                object.addProperty("count", this.count);
            }
            object.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            pJson.add("output", object);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return FragTranformingRecipe.Serializer.INSTANCE;
        }

        @Override
        public @Nullable JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
