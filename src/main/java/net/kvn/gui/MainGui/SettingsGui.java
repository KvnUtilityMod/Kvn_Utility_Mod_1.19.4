package net.kvn.gui.MainGui;

import net.kvn.gui.MainGui.settingbuttones.*;
import net.kvn.modules.Module;
import net.kvn.settings.*;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.mc;

public class SettingsGui {

    //Module x y width height
    private int x;
    private int y;
    private int width;
    private int settingHeight;
    private int totalHeight = 0;
    private Module module;
    private ArrayList<SettingGui> settingButtons = new ArrayList<>();

    public SettingsGui(Module module, int x, int y) {
        this.module = module;
        this.x = x;
        this.width = 165;
        this.settingHeight = 20;

        int settingY = y;
        for (Setting s : module.getSettingsNoSettingOf()){
            SettingGui gui = null;
            if (s instanceof Visible) gui = new VisibleGui(module, (Visible) s, x, settingY, width, settingHeight);
            else if (s instanceof KeyValue) gui = new KeyValueGui(module, (KeyValue) s, x, settingY, width, settingHeight);
            else if (s instanceof BooleanValue) gui = new BooleanGui(module, (BooleanValue) s, x, settingY, width, settingHeight);
            else if (s instanceof IntegerValue) gui = new IntegerValueGui(module, (IntegerValue) s, x, settingY, width, settingHeight);
            else if (s instanceof BlockPosValue) gui = new BlockPosGui(module, (BlockPosValue) s, x, settingY, width, settingHeight);
            else if (s instanceof ColorValue) gui = new ColorValueGui(module, (ColorValue) s, x, settingY, width, settingHeight);

            if (gui != null) {
                settingButtons.add(gui);
                settingY += gui.getTotalHeight();
            }
        }
        this.y = getCalculatedYPos(y);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        int yVar = y;
        for (SettingGui gui : settingButtons) {
            gui.render(matrices, mouseX, mouseY, x, yVar, delta);
            yVar += gui.getTotalHeight();
        }
        totalHeight = yVar - y;
    }

    public void onMouseClick(int button, int x, int y){
        for (SettingGui gui : settingButtons) {
            gui.onMouseClick(button, x, y);
        }
    }

    public void onMouseRelease(int button, int x, int y){
        for (SettingGui gui : settingButtons) {
            gui.onMouseRelease(button, x, y);
        }
    }

    public void onMouseScroll(double amount){
        for (SettingGui gui : settingButtons) {
            gui.onMouseScroll(x, y, amount);
        }
    }

    public void onCharInput(char c) {
        for (SettingGui gui : settingButtons) {
            gui.onCharInput(c);
        }
    }

    public void onKeyPressed(int key) {
        for (SettingGui gui : settingButtons) {
            gui.onKeyPressed(key);
        }
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    public int calculateTotalHeight() {
        int totalHeight = 0;
        for (SettingGui gui : settingButtons) {
            totalHeight += gui.getTotalHeight();
        }
        return totalHeight;
    }

    private int getCalculatedYPos(int y) {
        totalHeight = calculateTotalHeight();
        y = y - totalHeight / 3;
        if (y + totalHeight > mc.getWindow().getHeight()) y = mc.getWindow().getHeight() - totalHeight;
        if (y < 0) y = 0;
        return y;
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + totalHeight;
    }
}
