package org.keyart.example.common.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String EXAMPLE_KEY_CATEGORY = "key.category.mod_id.example";
    public static final String EXAMPLE_KEY = "key.mod_id.example";

    public static final KeyMapping EXAMPLED_KEY = new KeyMapping(EXAMPLE_KEY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, EXAMPLE_KEY_CATEGORY);
}
