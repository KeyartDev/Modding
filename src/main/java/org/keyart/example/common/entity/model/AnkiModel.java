package org.keyart.example.common.entity.model;

import net.minecraft.resources.ResourceLocation;
import org.keyart.example.Example;
import org.keyart.example.common.entity.AnkiEntity;
import software.bernie.geckolib.model.GeoModel;

public class AnkiModel extends GeoModel<AnkiEntity> {
    public static final ResourceLocation ANKI_MODEL = ResourceLocation.fromNamespaceAndPath(Example.MODID, "geo/anki.geo.json");
    public static final ResourceLocation ANKI_TEXTURE = ResourceLocation.fromNamespaceAndPath(Example.MODID, "textures/entity/anki/anki.png");
    public static final ResourceLocation ANKI_ANIMATIONS = ResourceLocation.fromNamespaceAndPath(Example.MODID, "animations/anki.animation.json");

    @Override
    public ResourceLocation getModelResource(AnkiEntity ankiEntity) {
        return ANKI_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(AnkiEntity ankiEntity) {
        return ANKI_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(AnkiEntity ankiEntity) {
        return ANKI_ANIMATIONS;
    }
}
