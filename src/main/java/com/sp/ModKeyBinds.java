package com.sp;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {
    public static KeyBinding toggleFlashlight;
    public static KeyBinding toggleEvent;

    public static void initializeKeyBinds() {
        toggleFlashlight = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.spb-revamped.toggle_flashlight", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "spb-revamped.keybinds"));

        if (MinecraftClient.getInstance().getSession().getUsername().equals("SppacePotato") || MinecraftClient.getInstance().getSession().getUsername().equals("HerrChaotic") || FabricLoader.getInstance().isDevelopmentEnvironment()) {
            toggleEvent = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.spb-revamped.toggle_event", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_SEMICOLON, "spb-revamped.keybinds"));
        }
    }
}
