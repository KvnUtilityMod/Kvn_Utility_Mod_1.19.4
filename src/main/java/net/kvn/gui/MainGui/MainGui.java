package net.kvn.gui.MainGui;

import net.kvn.event.events.*;
import net.kvn.modules.Category;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.mc;

public class MainGui extends Screen implements CharInput, KeyPress, MouseClick, MouseRelease, MouseScroll {

    int mouseX = 0;
    int mouseY = 0;
    public SettingsGui settingsGui;
    public static MainGui INSTANCE = new MainGui();
    public ArrayList<CategoryGui> categories = new ArrayList<>();

    protected MainGui() {
        super(Text.literal(""));

        //create category guis for each category
        int x = 130;
        for (Category c : Category.values()) {
            categories.add(new CategoryGui(this, c, x, 40, 110, 20, 18));
            x += 125;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        renderLogo(matrices);

        categories.forEach(c -> c.render(matrices, mouseX, mouseY, delta));
        if (settingsGui != null) settingsGui.render(matrices, mouseX, mouseY, delta);

    }

    @Override
    public void onMouseClick(int button, int x, int y) {
        if (!(mc.currentScreen instanceof MainGui)) return;

        //x and y do are not inline with the render coordinates
        if (settingsGui != null) {
            if (settingsGui.isMouseOver(mouseX, mouseY)) {
                settingsGui.onMouseClick(button, mouseX, mouseY);
                return;
            }
            else{
                settingsGui = null;
            }
        }
        categories.forEach(c -> c.onMouseClick(button, mouseX, mouseY));
    }

    @Override
    public void onCharInput(char c) {
        if (!(mc.currentScreen instanceof MainGui)) return;
        if (settingsGui != null) settingsGui.onCharInput(c);
    }

    @Override
    public void onKeyPressed(int key) {
        if (!(mc.currentScreen instanceof MainGui)) return;
        if (settingsGui != null) settingsGui.onKeyPressed(key);
    }

    @Override
    public void onMouseRelease(int button, int x, int y) {
        if (!(mc.currentScreen instanceof MainGui)) return;
        if (settingsGui != null) settingsGui.onMouseRelease(button, mouseX, mouseY);
    }

    @Override
    public void onMouseScroll(double y) {
        if (!(mc.currentScreen instanceof MainGui)) return;
        if (settingsGui != null) settingsGui.onMouseScroll(y);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public void setSettingGui(SettingsGui settingGui) {
        this.settingsGui = settingGui;
    }

    public SettingsGui getSettingGui() {
        return this.settingsGui;
    }

    private void renderLogo(MatrixStack matrices) {
        mc.textRenderer.drawWithShadow(
                matrices,
                "Kvn Utility Mod",
                (mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth("Kvn Utility Mod")) / 2F,
                4,
                0xFF0000);
    }
}
