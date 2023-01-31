package net.kvn.event.events;

import net.minecraft.client.util.math.MatrixStack;

public interface PrepareHudRender {
        void onPrepareHudRender(MatrixStack matrices, float tickDelta);
}
