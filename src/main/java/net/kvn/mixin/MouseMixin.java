package net.kvn.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.kvn.KvnUtilityMod.customEventHandler;
import static net.kvn.KvnUtilityMod.mc;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo info) {
        if (action == GLFW_PRESS)
            customEventHandler.onMouseClick(button,(int) mc.mouse.getX(), (int) mc.mouse.getY());
        else
            customEventHandler.onMouseRelease(button,(int) mc.mouse.getX(), (int) mc.mouse.getY());
    }

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo info) {
        customEventHandler.onMouseScroll(vertical);
    }
}
