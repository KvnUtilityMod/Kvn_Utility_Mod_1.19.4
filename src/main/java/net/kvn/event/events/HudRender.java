package net.kvn.event.events;

import net.minecraft.client.util.math.MatrixStack;

public interface HudRender {
    void onHudRender(MatrixStack matrices, float tickDelta);
}
