package org.keyart.example.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class CooldownObject {
    private UUID player;
    private Item item;
    private int cooldown;

    void tick() {
        this.cooldown--;
    }

    boolean isExpired() {
        return this.cooldown <= 0;
    }

    boolean isExists(Player player, Item item) {
        return this.player == player.getUUID() && this.item == item;
    }
}
