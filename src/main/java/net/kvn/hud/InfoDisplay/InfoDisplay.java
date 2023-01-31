package net.kvn.hud.InfoDisplay;

import net.kvn.event.events.HudRender;
import net.kvn.modules.Module;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.*;

public class InfoDisplay implements HudRender {

    private int rowHeight = 15;
    private ArrayList<InfoBox> info = new ArrayList<>();

    public InfoDisplay(){
        customEventHandler.addListener(this);
    }

    public void addInfo(ArrayList<String> info, Module module){
        this.info.add(new InfoBox(info, module));
    }

    public void addInfo(ArrayList<String> info){
        this.info.add(new InfoBox(info));
    }

    public void addInfoBox(InfoBox infoBox){
        info.add(infoBox);
    }

    public void clearInfo(){
        info.clear();
    }

    @Override
    public void onHudRender(MatrixStack matrices, float tickDelta) {
        if (info.size() > 0){
            int rows = info.stream().mapToInt(InfoBox::getRows).sum();
            int widest = info.stream().mapToInt(InfoBox::getWidestRow).max().getAsInt() + 17;
            int yStart = mc.getWindow().getScaledHeight() / 2 - (rows * rowHeight) / 3;

            DrawableHelper.fill(matrices, 0, yStart, widest, yStart + (rows * rowHeight), moduleManager.clickGui.backgroundGui.getColor());
            DrawableHelper.fill(matrices, 1, yStart, widest -1, yStart + 1, moduleManager.clickGui.textGui.getColor());
            for (InfoBox infoBox : info) {
                infoBox.render(matrices, 0, yStart, rowHeight, widest);
                yStart += infoBox.getRows() * rowHeight;
            }
            clearInfo();
        }
    }
}
