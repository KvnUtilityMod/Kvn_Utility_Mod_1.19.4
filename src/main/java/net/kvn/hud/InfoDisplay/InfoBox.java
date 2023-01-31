package net.kvn.hud.InfoDisplay;

import net.kvn.modules.Module;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;

public class InfoBox {

    private ArrayList<String> info = new ArrayList<>();
    private Boolean underline = false;

    public InfoBox(ArrayList<String> info){
        this.info = info;
    }

    public InfoBox(ArrayList<String> info, Module module){
        this.info.add(module.getName());
        this.info.addAll(info);
        this.underline = true;
    }

    public int getRows(){
        return info.size();
    }

    public int getWidestRow(){
        int widest = 0;
        for (String s : info) {
            int width = mc.textRenderer.getWidth(s);
            if (width > widest) widest = width;
        }
        return widest;
    }

    public void render(MatrixStack matrixStack, int x, int y, int rowHeight, int width){

        if (info.size() == 0) return;

        int yVar = y;
        for (String s : info) {
            DrawableHelper.fill(matrixStack, x, yVar, x + 1, yVar + rowHeight, moduleManager.clickGui.textGui.getColor());
            DrawableHelper.fill(matrixStack, x + width - 1, yVar, x + width, yVar + rowHeight, moduleManager.clickGui.textGui.getColor());
            mc.textRenderer.draw(matrixStack, s, 8, yVar + (rowHeight - 9) / 2F, moduleManager.clickGui.textGui.getColor());
            yVar += rowHeight;
        }
        DrawableHelper.fill(matrixStack, 1, y + (info.size() * rowHeight) - 1, width -1, y + (info.size() * rowHeight), moduleManager.clickGui.textGui.getColor());
        if (underline) DrawableHelper.fill(matrixStack,  8, (int) (y + (rowHeight / 2F ) + 5), 8 + mc.textRenderer.getWidth(info.get(0)), (int) (y + (rowHeight / 2F) + 6), moduleManager.clickGui.textGui.getColor());
    }
}
