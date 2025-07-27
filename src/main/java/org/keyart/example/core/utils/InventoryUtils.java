package org.keyart.example.core.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class InventoryUtils {
    public static <T> boolean addTagToItemStack(ItemStack stack, String key, T value) {
        CompoundTag tag = stack.getTag();

        if (tag == null)
            return false;

        if (value instanceof String) {
            tag.putString(key, (String) value);
        } else if (value instanceof Integer) {
            tag.putInt(key, ((Integer) value));
        } else if (value instanceof Double) {
            tag.putDouble(key, ((Double) value));
        } else if (value instanceof Float) {
            tag.putFloat(key, ((Float) value));
        } else if (value instanceof LivingEntity entity) {
            tag.putUUID(key, entity.getUUID());
        }

        return true;
    }

}
