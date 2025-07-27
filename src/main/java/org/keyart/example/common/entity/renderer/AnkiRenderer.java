package org.keyart.example.common.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.keyart.example.common.entity.AnkiEntity;
import org.keyart.example.common.entity.layer.AnkiGlowLayer;
import org.keyart.example.common.entity.model.AnkiModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class AnkiRenderer extends GeoEntityRenderer<AnkiEntity> {
    public AnkiRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AnkiModel());
        addRenderLayer(new AnkiGlowLayer(this));
    }
}
