package org.keyart.example.common.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import org.keyart.example.Example;

public class EModelLayers {
    public static final ModelLayerLocation ANKI_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Example.MODID, "anki"), "main"
    );
}
