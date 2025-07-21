package org.keyart.example.core.utils;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Mod.EventBusSubscriber
public class CooldownUtils {
    private static HashMap<Item, Integer> list = new HashMap<>();

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            try {
                if (!list.isEmpty()) {
                    Iterator<Map.Entry<Item, Integer>> it = list.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<Item, Integer> item = it.next();
                        item.setValue(item.getValue()-1);
                        if (item.getValue() <= 0)
                            it.remove();
                    }
                }
            } catch (ConcurrentModificationException ex) {
                LogUtils.getLogger().atError().log("ConcurrentModificationException on \"CooldownUtils.tick()\"");
            }

        }
    }

    public static void addCooldown(Item key, int value) {
        list.put(key, value);
    }

    public static int getCooldown(Item key) {
        return list.getOrDefault(key, 0);
    }

    public static int getCooldownInSeconds(Item key) {
        return (int) Math.ceil((double) list.getOrDefault(key, 0) / 20);
    }

    public static void clearCooldownList() {
        list.clear();
    }

    public static boolean isOnCooldown(Item key) {
        return list.containsKey(key) && list.get(key) != 0;
    }
}
