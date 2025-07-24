package org.keyart.example.core.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber
public class CooldownUtils {
    private static ArrayList<CooldownObject> list = new ArrayList<>();

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Iterator<CooldownObject> iterator = list.iterator();
            while (iterator.hasNext()) {
                CooldownObject object = iterator.next();
                object.tick();
                if (object.isExpired())
                    iterator.remove();
            }
        }
    }

    public static void addCooldown(Player player, Item item, int value) {
        if (list.stream().anyMatch(t -> t.isExists(player, item))) {
            System.out.println("THIS COOLDOWN IS ALREADY EXISTS!");
            System.out.println("- UUID: " + player.getStringUUID());
            System.out.println("- ITEM: " + item.getDescriptionId());
            System.out.println("- COOLDOWN: " + value);
            return;
        }


        UUID uuid = player.getUUID();
        list.add(new CooldownObject(uuid, item, value));
    }

    public static void addCooldown(CooldownObject cooldownObject) {
        if (list.contains(cooldownObject)) {
            System.out.println("THIS COOLDOWN IS ALREADY EXISTS!");
            System.out.println("- UUID: " + cooldownObject.getPlayer().toString());
            System.out.println("- ITEM: " + cooldownObject.getItem().getDescriptionId());
            System.out.println("- COOLDOWN: " + cooldownObject.getCooldown());
            return;
        }

        list.add(cooldownObject);
    }

    public static int getCooldown(Player player, Item item) {
        List<CooldownObject> listFiltered = list.stream().filter((t) -> {
            if (t.getPlayer() == player.getUUID() && t.getItem() == item)
                return true;

            return false;
        }).toList();

        if (listFiltered.isEmpty())
            return 0;

        return listFiltered.get(0).getCooldown();
    }

    public static int getCooldownInSeconds(Player player, Item item) {
        return (int) Math.ceil((double) getCooldown(player, item) / 20);
    }

    public static void clearCooldownList() {
        list.clear();
    }

    public static boolean isOnCooldown(Player player, Item item) {
        return list.stream().anyMatch(t -> t.isExists(player, item));
    }
}
