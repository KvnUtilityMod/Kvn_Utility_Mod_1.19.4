package net.kvn.gui.MainGui;

import net.kvn.modules.Module;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;

public class ModuleGui {

    int x;
    int y;
    int width;
    int height;
    Module module;
    CategoryGui parent;

    public ModuleGui(CategoryGui categoryGui, Module module, int x, int y, int width, int height) {
        this.parent = categoryGui;
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        DrawableHelper.fill(matrices, x, y, x + width, y + height, moduleManager.clickGui.backgroundGui.getColor());

        if (module.isActive()) {
            mc.textRenderer.draw(matrices, module.getName(), x + (width - mc.textRenderer.getWidth(module.getName())) / 2F, y + (height - 9) / 2F, moduleManager.clickGui.textGui.getColor());
        } else {
            mc.textRenderer.draw(matrices, module.getName(), x + (width - mc.textRenderer.getWidth(module.getName())) / 2F, y + (height - 9) / 2F, 0xFFFFFFFF);
        }

    }

    public void onMouseClick(int button, int x, int y) {
        if (x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height) {
            if (button == 1) parent.getMainGui().setSettingGui(new SettingsGui(module, this.x + this.width, this.y));
            else module.toggle();
        }
    }
}
