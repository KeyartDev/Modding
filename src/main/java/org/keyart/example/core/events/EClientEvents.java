package org.keyart.example.core.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.keyart.example.Example;
import org.keyart.example.common.block.entity.ModBlockEntities;
import org.keyart.example.common.block.entity.render.SomePedistalBlockRenderer;
import org.keyart.example.common.client.IndicatorHudOverlay;
import org.keyart.example.common.client.ThirstHudOverlay;
import org.keyart.example.common.key.KeyBinding;
import org.keyart.example.core.network.ENetworks;
import org.keyart.example.core.network.packets.DrinkWaterC2SPacket;


public class EClientEvents {
    @Mod.EventBusSubscriber(modid = Example.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.EXAMPLED_KEY.consumeClick()) {
                ENetworks.sendToServer(new DrinkWaterC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Example.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.EXAMPLED_KEY);
        }

        @SubscribeEvent
        public static void registerHUD(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("thirst",
                    ThirstHudOverlay.HUD_THIRST);

            event.registerAboveAll("misc",
                    IndicatorHudOverlay.MISC_HUD);
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.SOME_PEDISTAL_BE.get(), SomePedistalBlockRenderer::new);
        }
    }
}
