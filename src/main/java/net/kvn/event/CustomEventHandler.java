package net.kvn.event;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.*;
import net.kvn.settings.BooleanValue;
import net.kvn.settings.Setting;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public class CustomEventHandler {

    private ArrayList<Object> listeners = new ArrayList<>();

    public CustomEventHandler(){}

    public void addListener(Object listener){
        listeners.add(listener);
    }

    public void onMouseClick(int button, int x , int y){
        for (Object listener : listeners) {
            if (listener instanceof MouseClick) {
                ((MouseClick) listener).onMouseClick(button, x, y);
            }
        }
    }

    public void onMouseRelease(int button, int x , int y){
        for (Object listener : listeners) {
            if (listener instanceof MouseRelease) {
                ((MouseRelease) listener).onMouseRelease(button, x, y);
            }
        }
    }

    public void onMouseScroll(double y){
        for (Object listener : listeners) {
            if (listener instanceof MouseScroll) {
                ((MouseScroll) listener).onMouseScroll(y);
            }
        }
    }

    public void onCharInput(char c){
        for (Object listener : listeners) {
            if (listener instanceof CharInput) {
                ((CharInput) listener).onCharInput(c);
            }
        }
    }

    public void onKeyPress(int key){
        for (Object listener : listeners) {
            if (listener instanceof KeyPress) {
                ((KeyPress) listener).onKeyPressed(key);
            }
        }
    }

    public void renderBox(WorldRenderContext context, MatrixStack matrices) {
        for (Object listener : listeners) {
            if (listener instanceof RenderBox) {
                ((RenderBox) listener).renderBox(context, matrices);
            }
        }
    }

    public void onHudRender(MatrixStack matrices, float tickDelta) {
        //preparehudrender
        for (Object listener : listeners) {
            if (listener instanceof PrepareHudRender) {
                ((PrepareHudRender) listener).onPrepareHudRender(matrices, tickDelta);
            }
        }

        for (Object listener : listeners) {
            if (listener instanceof HudRender) {
                ((HudRender) listener).onHudRender(matrices, tickDelta);
            }
        }
    }

    public void onSettingUpdate(Setting setting) {
        for (Object listener : listeners) {
            if (listener instanceof SettingUpdate) {
                ((SettingUpdate) listener).onSettingUpdate(setting);
            }
        }
    }
}
