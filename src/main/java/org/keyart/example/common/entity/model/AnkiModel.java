package org.keyart.example.common.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.keyart.example.Example;
import org.keyart.example.common.entity.AnkiEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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

    @Override
    public void setCustomAnimations(AnkiEntity animatable, long instanceId, AnimationState<AnkiEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX((360 - entityData.headPitch()) * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
