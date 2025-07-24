package org.keyart.example.core.custom.thirst;

import net.minecraft.nbt.CompoundTag;

public class PlayerThirst {
    private int thirst;
    private final int MIN_THIRST = 0;
    private final int MAX_THIRST = 10;

    public int getThirst() {
        return thirst;
    }

    public void addThirst(int add) {
        thirst = Math.min(thirst + add, MAX_THIRST);
    }

    public void subThirst(int sub) {
        thirst = Math.max(thirst - sub, MIN_THIRST);
    }

    public void copyFrom(PlayerThirst source) {
        this.thirst = source.thirst;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("thirst", thirst);
    }

    public void loadNBTData(CompoundTag nbt) {
        thirst = nbt.getInt("thirst");
    }

    public void setThirst(int set) {
        if (set > MAX_THIRST)
            thirst = MAX_THIRST;
        else if (set < MIN_THIRST) {
            thirst = MIN_THIRST;
        } else {
            thirst = set;
        }
    }
}
