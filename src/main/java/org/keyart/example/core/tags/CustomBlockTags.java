package org.keyart.example.core.tags;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.keyart.example.Example;

public class CustomBlockTags {
    public static final TagKey<Block> STONE_BRICKS = tag("stone_rocks");

    private static TagKey<Block> tag(String name) {
        return BlockTags.create(
                ResourceLocation.fromNamespaceAndPath(Example.MODID, name)
        );
    }
}
