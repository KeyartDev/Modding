package org.keyart.example.common.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.keyart.example.Example;

public class IndicatorHudOverlay {
    public static final ResourceLocation HUD = ResourceLocation.fromNamespaceAndPath(Example.MODID, "textures/misc/indicator.png");

    public static IGuiOverlay MISC_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, HUD);

        int x = screenWidth;
        int y = screenHeight;


        guiGraphics.setColor(1, 0, 0, 1);

        guiGraphics.blit(HUD, x - 26, y - 26,
                0, 0,
                16, 16,
                16, 16);
    };
}
