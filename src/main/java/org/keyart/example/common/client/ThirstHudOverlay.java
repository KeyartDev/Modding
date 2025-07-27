package org.keyart.example.common.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.keyart.example.Example;

public class ThirstHudOverlay {
    private static final ResourceLocation FILLED_THIRST =
            ResourceLocation.fromNamespaceAndPath(Example.MODID, "textures/thirst/thirst_filled.png");

    private static final ResourceLocation EMPTY_THIRST =
            ResourceLocation.fromNamespaceAndPath(Example.MODID, "textures/thirst/thirst_empty.png");

    public static final IGuiOverlay HUD_THIRST = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        if (!gui.getMinecraft().options.hideGui && gui.shouldDrawSurvivalElements()) {
            int x = screenWidth / 2;
            int y = screenHeight;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, EMPTY_THIRST);

            for (int i=0;i<10;i++) {
                guiGraphics.blit(EMPTY_THIRST, x - 91 + (i*8), y - 53,
                        9, 12,
                        0, 0,
                        9, 12,
                        16, 16);
            }

            RenderSystem.setShaderTexture(0, FILLED_THIRST);
            for (int i = 0; i < 10; i++) {
                if (ClientThirstData.getPlayerThirst() > i) {
                    guiGraphics.blit(FILLED_THIRST, x - 91 + (i*8), y - 53,
                            9, 12,
                            0, 0,
                            9, 12,
                            16, 16);
                } else {
                    break;
                }
            }
        }
    });
}
