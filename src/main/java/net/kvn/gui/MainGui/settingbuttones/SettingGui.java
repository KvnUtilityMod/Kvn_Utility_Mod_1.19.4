package net.kvn.gui.MainGui.settingbuttones;

import net.kvn.gui.MainGui.SettingsGui;
import net.kvn.modules.Module;
import net.kvn.settings.*;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;
import static net.kvn.utils.render.GuiUtil.getLevelOfSetting;

public abstract class SettingGui {

    private int x;
    private int y;
    private int width;
    private int height;
    private Module module;
    private Setting setting;
    private SettingsGui settingGui;
    private ArrayList<SettingGui> settingButtons = new ArrayList<>();

    public SettingGui(Module module, Setting setting, int x, int y, int width, int height) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setting = setting;

        int settingY = y + height;
        for (Setting s : module.getSettingsOf(setting)) {
            SettingGui gui = null;
            if (s instanceof Visible) gui = new VisibleGui(module, (Visible) s, x, settingY, width, height);
            else if (s instanceof KeyValue) gui = new KeyValueGui(module, (KeyValue) s, x, settingY, width, height);
            else if (s instanceof BooleanValue) gui = new BooleanGui(module, (BooleanValue) s, x, settingY, width, height);
            else if (s instanceof IntegerValue) gui = new IntegerValueGui(module, (IntegerValue) s, x, settingY, width, height);
            else if (s instanceof BlockPosValue) gui = new BlockPosGui(module, (BlockPosValue) s, x, settingY, width, height);
            else if (s instanceof ColorValue) gui = new ColorValueGui(module, (ColorValue) s, x, settingY, width, height);

            if (gui != null) {
                settingButtons.add(gui);
                settingY += gui.getTotalHeight();
            }
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, int x, int y, float delta) {
        this.x = x;
        this.y = y;
        //draws the background of the button
        DrawableHelper.fill(matrices, x, y, x + width, y + height, moduleManager.clickGui.backgroundSettings.getColor());

        //draws the hierarchy of the setting
        for (int i = getLevelOfSetting(setting); i > 0; i--) {
            DrawableHelper.fill(matrices, x + (i * 3), y, x + (i * 3) + 1, y + height, moduleManager.clickGui.textSetting.getColor());
        }

        //shows if the setting has subsettings
        if (settingButtons.size() > 0 || hasSubPartsOpened()) mc.textRenderer.draw(matrices, setting.isSettingsOpened() ? "-" : "+", x + getLevelOfSetting(setting) * 3 + 2, y + (height - 9) / 2F, moduleManager.clickGui.textSetting.getColor());

        //renders the subsettings if they're opened
        int yVar = y + getHeight();
        if (setting.isSettingsOpened()){
            for (SettingGui gui : settingButtons) {
                gui.render(matrices, mouseX, mouseY, x, yVar, delta);
                yVar += gui.getTotalHeight();
            }
        }
    }

    public void onMouseClick(int button, int x, int y){
        if (setting.isSettingsOpened()){
            for (SettingGui gui : settingButtons) {
                gui.onMouseClick(button, x, y);
            }
        }
        if (button == 1 && x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height){
            setting.toggleSettingsOpened();
        }
    }
    public void onMouseRelease(int button, int x, int y){
        if (setting.isSettingsOpened()){
            for (SettingGui gui : settingButtons) {
                gui.onMouseRelease(button, x, y);
            }
        }
    }
    public void onMouseScroll(int x, int y, double amount){
        if (setting.isSettingsOpened()){
            for (SettingGui gui : settingButtons) {
                gui.onMouseScroll(x, y, amount);
            }
        }
    }
    public void onCharInput(char c) {
        if (setting.isSettingsOpened()){
            for (SettingGui gui : settingButtons) {
                gui.onCharInput(c);
            }
        }
    }
    public void onKeyPressed(int key) {
        if (setting.isSettingsOpened()){
            for (SettingGui gui : settingButtons) {
                gui.onKeyPressed(key);
            }
        }
    }

    //getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Module getModule() {
        return module;
    }

    public boolean hasSubPartsOpened(){
        return false;
    }

    public int getTotalHeight() {
        int height = getHeight();
        if (setting.isSettingsOpened()){
            for (SettingGui gui : settingButtons) {
                height += gui.getTotalHeight();
            }
        }
        return height;
    }
}
