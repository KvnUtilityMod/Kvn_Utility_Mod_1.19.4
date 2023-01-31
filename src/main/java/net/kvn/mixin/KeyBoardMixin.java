package net.kvn.mixin;

import com.sun.jna.platform.KeyboardUtils;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.event.KeyEvent;

import static net.kvn.KvnUtilityMod.customEventHandler;

@Mixin(Keyboard.class)
public class KeyBoardMixin {

    @Inject(method = "onKey", at = @At("HEAD"))
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo info) {

        if (action != 0)
            customEventHandler.onKeyPress(key);
    }

    @Inject(method = "onChar", at = @At("HEAD"))
    private void onChar(long window, int i, int j, CallbackInfo info) {
        customEventHandler.onCharInput((char) i );
    }
}

