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
import org.keyart.example.core.registry.ModBlocks;
import org.keyart.example.core.registry.ModItems;

public class EItemModelProvider extends ItemModelProvider {
    public EItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Example.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.EXAMPLE_ITEM.get());
        basicItem(ModItems.MYSTIC_CLOCK.get());
        basicItem(ModItems.SOME_BLOCK_FRAG.get());
        basicItem(ModItems.IT_ITEM.get());
        basicItem(ModItems.STRAWBERRY_SEEDS.get());
        basicItem(ModItems.STRAWBERRY.get());
        basicItem(ModItems.KIND_NECRO_DISK.get());

        handheldItem(ModItems.NETHER_BRUSH);

        evenSimpleBlockItem(ModBlocks.SOME_STAIRS);
        evenSimpleBlockItem(ModBlocks.SOME_SLAB);
        evenSimpleBlockItem(ModBlocks.SOME_FENCE_GATE);
        evenSimpleBlockItem(ModBlocks.SOME_PRESSURE_PLATE);

        fenceItem(ModBlocks.SOME_FENCE, ModBlocks.SOME_BLOCK);
        buttonItem(ModBlocks.SOME_BUTTON, ModBlocks.SOME_BLOCK);
        wallItem(ModBlocks.SOME_WALL, ModBlocks.SOME_BLOCK);

        handheldItem(ModItems.SOME_SWORD);
        handheldItem(ModItems.SOME_AXE);
        handheldItem(ModItems.SOME_PICKAXE);
        handheldItem(ModItems.SOME_SHOVEL);
        handheldItem(ModItems.SOME_HOE);

        basicItem(ModItems.DICE_ITEM.get());


        simpleBlockItemBlockTexture(ModBlocks.SEVEN_COLOR);

        withExistingParent(ModItems.ANKI_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
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
