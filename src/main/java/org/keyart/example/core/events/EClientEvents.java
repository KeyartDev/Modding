package org.keyart.example.core.events;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.keyart.example.Example;
import org.keyart.example.common.key.KeyBinding;

@Mod.EventBusSubscriber(modid = Example.MODID, value = Dist.CLIENT)
public class EClientEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBinding.EXAMPLED_KEY.consumeClick()) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Key pressed!"));
        }
    }
}
