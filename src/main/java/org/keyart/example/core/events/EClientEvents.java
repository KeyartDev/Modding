package org.keyart.example.core.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.keyart.example.Example;
import org.keyart.example.common.client.ThirstHudOverlay;
import org.keyart.example.common.entity.client.AnkiModel;
import org.keyart.example.common.entity.client.EModelLayers;
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
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(EModelLayers.ANKI_LAYER, AnkiModel::createBodyLayer);
        }
    }
}
