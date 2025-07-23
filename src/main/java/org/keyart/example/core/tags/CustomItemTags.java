package org.keyart.example.core.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.keyart.example.Example;

public class CustomItemTags {



    private static TagKey<Item> tag(String name) {
        return ItemTags.create(
                ResourceLocation.fromNamespaceAndPath(Example.MODID, name)
        );
    }
}
