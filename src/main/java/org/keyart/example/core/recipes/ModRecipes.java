package org.keyart.example.core.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;

public class ModRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Example.MODID);

    public static final RegistryObject<RecipeSerializer<FragTranformingRecipe>> FRAG_TRANSFORMING_SERIALIZER =
            SERIALIZERS.register("frag_transforming", () -> FragTranformingRecipe.Serializer.INSTANCE);


    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}
