package org.keyart.example.core.datagen.providers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.keyart.example.Example;
import org.keyart.example.core.registry.BlockRegistry;
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
        basicItem(ItemRegistry.IT_ITEM.get());
        basicItem(ItemRegistry.STRAWBERRY_SEEDS.get());
        basicItem(ItemRegistry.STRAWBERRY.get());
        basicItem(ItemRegistry.KIND_NECRO_DISK.get());

        handheldItem(ItemRegistry.NETHER_BRUSH);

        evenSimpleBlockItem(BlockRegistry.SOME_STAIRS);
        evenSimpleBlockItem(BlockRegistry.SOME_SLAB);
        evenSimpleBlockItem(BlockRegistry.SOME_FENCE_GATE);
        evenSimpleBlockItem(BlockRegistry.SOME_PRESSURE_PLATE);

        fenceItem(BlockRegistry.SOME_FENCE, BlockRegistry.SOME_BLOCK);
        buttonItem(BlockRegistry.SOME_BUTTON, BlockRegistry.SOME_BLOCK);
        wallItem(BlockRegistry.SOME_WALL, BlockRegistry.SOME_BLOCK);

        handheldItem(ItemRegistry.SOME_SWORD);
        handheldItem(ItemRegistry.SOME_AXE);
        handheldItem(ItemRegistry.SOME_PICKAXE);
        handheldItem(ItemRegistry.SOME_SHOVEL);
        handheldItem(ItemRegistry.SOME_HOE);

        simpleBlockItemBlockTexture(BlockRegistry.SEVEN_COLOR);

        withExistingParent(ItemRegistry.ANKI_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld"))
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(Example.MODID, "item/" + item.getId().getPath()));
    }

    public void evenSimpleBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(Example.MODID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    private void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(Example.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(Example.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.fromNamespaceAndPath(Example.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Example.MODID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Example.MODID, "block/" + item.getId().getPath()));
    }


}
