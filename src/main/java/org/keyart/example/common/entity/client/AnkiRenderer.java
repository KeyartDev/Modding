package org.keyart.example.common.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.keyart.example.Example;
import org.keyart.example.common.entity.AnkiEntity;

public class AnkiRenderer extends MobRenderer<AnkiEntity, AnkiModel<AnkiEntity>> {
    public AnkiRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AnkiModel<>(pContext.bakeLayer(EModelLayers.ANKI_LAYER)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(AnkiEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Example.MODID, "textures/entity/anki/anki.png");
    }

    @Override
    public void render(AnkiEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {


        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
