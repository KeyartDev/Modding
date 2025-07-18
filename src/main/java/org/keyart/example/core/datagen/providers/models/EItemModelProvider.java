package org.keyart.example.core.datagen.providers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.core.registry.ItemRegistry;

public class EItemModelProvider extends ItemModelProvider {
    public EItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Example.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ItemRegistry.EXAMPLE_ITEM.get());
        basicItem(ItemRegistry.MYSTIC_CLOCK.get());
        basicItem(ItemRegistry.SOME_BLOCK_FRAG.get());

        handheldItem(ItemRegistry.MAGIC_WAND);
        handheldItem(ItemRegistry.NETHER_BRUSH);
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld"))
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(Example.MODID, "item/" + item.getId().getPath()));
    }
}
