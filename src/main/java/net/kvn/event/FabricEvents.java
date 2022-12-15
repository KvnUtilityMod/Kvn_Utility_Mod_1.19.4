package net.kvn.event;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

import static net.kvn.KvnUtilityMod.customEventHandler;

public class FabricEvents {

    public FabricEvents () {
        this.initializeEvents();
    }

    public void initializeEvents() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> customEventHandler.renderBox(context, context.matrixStack()));
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> customEventHandler.onHudRender(matrices, tickDelta));
    }
}
