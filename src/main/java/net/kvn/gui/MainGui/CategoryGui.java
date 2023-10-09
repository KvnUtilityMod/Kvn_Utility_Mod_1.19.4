package net.kvn.gui.MainGui;

import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.utils.file.FileKeyValue;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.*;

public class CategoryGui {

    Category category;
    int x;
    int y;
    int yVar;
    int width;
    int categoryHeight;
    int moduleHeight;
    MainGui mainGui;
    ArrayList<ModuleGui> moduleGuis = new ArrayList<>();

    public CategoryGui(MainGui mainGui, Category category, int x, int y, int categoryWidth, int categoryHeight, int moduleHeight) {
        this.mainGui = mainGui;
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = categoryWidth;
        this.categoryHeight = categoryHeight;
        this.moduleHeight = moduleHeight;
        this.yVar = y + categoryHeight;

        for (Module m : moduleManager.getModulesOfCategory(category)) {
            moduleGuis.add(new ModuleGui(this, m, x, yVar, categoryWidth, moduleHeight));
            yVar += moduleHeight;
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        //render category
        DrawableHelper.fill(matrices, x, y, x + width, y + categoryHeight, moduleManager.clickGui.backgroundGui.getColorObj().darker().getRGB());
        mc.textRenderer.draw(matrices, category.getName().toUpperCase(), x + (width - mc.textRenderer.getWidth(category.getName().toUpperCase())) / 2F, y + (categoryHeight - 9) / 2F, moduleManager.clickGui.textGui.getColor());
        yVar += categoryHeight;

        //render modules
        for (ModuleGui m : moduleGuis) {
            m.render(matrices, mouseX, mouseY, delta);
            yVar += moduleHeight;
        }
    }

    public void onMouseClick(int button, int x, int y) {
        for (ModuleGui m : moduleGuis) {
            m.onMouseClick(button, x, y);
        }
    }

    public void onMouseRelease(int button, int x, int y) {}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;

        //update modules position
        int yVar = y + categoryHeight;
        for (ModuleGui m : moduleGuis) {
            m.updatePosition(x, yVar);
            yVar += moduleHeight;
        }
    }

    public void savePosition() {
        FileKeyValue.saveValue("category_" + category.getName() + "_x", String.valueOf(x), categoryPositions);
        FileKeyValue.saveValue("category_" + category.getName() + "_y", String.valueOf(y), categoryPositions);
    }

    public MainGui getMainGui() {
        return mainGui;
    }

    public boolean isMouseOverHeader(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + categoryHeight;
    }
}
