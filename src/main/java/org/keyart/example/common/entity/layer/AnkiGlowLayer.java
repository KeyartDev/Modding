package org.keyart.example.common.entity.layer;

import org.keyart.example.common.entity.AnkiEntity;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class AnkiGlowLayer extends AutoGlowingGeoLayer<AnkiEntity> {
    public AnkiGlowLayer(GeoRenderer<AnkiEntity> renderer) {
        super(renderer);
    }
}
