package net.kvn.event.events;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.util.math.MatrixStack;

public interface RenderBox {
    void renderBox(WorldRenderContext context, MatrixStack matrices);
}
