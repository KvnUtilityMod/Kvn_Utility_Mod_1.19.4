package net.kvn.modules.build;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.PrepareHudRender;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.BlockPosValue;
import net.kvn.settings.ColorValue;
import net.kvn.settings.IntegerValue;
import net.kvn.utils.render.BoxUtil;
import net.kvn.utils.render.RenderBox;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.infoDisplay;
import static net.kvn.utils.world.Distance.getDistance;

public class Ruler extends Module implements net.kvn.event.events.RenderBox, PrepareHudRender {

    public IntegerValue blocksBetween = addSetting(new IntegerValue("Blocks Between", "The amount of blocks which are rendered in between", this, 1, 1, 1000));
    public BlockPosValue blockPos1 = addSetting(new BlockPosValue("BlockPos1", "blockPos1", this, null));
    public BlockPosValue blockPos2 = addSetting(new BlockPosValue("BlockPos2", "blockPos2", this, null));
    public ColorValue color = addSetting(new ColorValue("Color", "color", this, null));

    public Ruler() {
        super("Ruler", "Measures the distance between blocks", Category.BUILD);
        color.setAlphaNoSave(80);
    }

    @Override
    public void renderBox(WorldRenderContext context, MatrixStack matrices) {
        if (!this.isActive()) return;
        RenderBox.draw3d(matrices, BoxUtil.createBox(blockPos1.getPos()), color.getColorObj());
        RenderBox.draw3d(matrices, BoxUtil.createBox(blockPos2.getPos()), color.getColorObj());

        for (int i = 0; i < blocksBetween.getValue(); i++) {
            double x = (blockPos1.getX() + (blockPos2.getX() - blockPos1.getX()) * ((i + 1)  / (double) (blocksBetween.getValue() + 1)));
            double y = (blockPos1.getY() + (blockPos2.getY() - blockPos1.getY()) * ((i + 1)  / (double) (blocksBetween.getValue() + 1)));
            double z = (blockPos1.getZ() + (blockPos2.getZ() - blockPos1.getZ()) * ((i + 1)  / (double) (blocksBetween.getValue() + 1)));
            RenderBox.draw3d(matrices, BoxUtil.createBox(x, y, z, x + 1, y + 1, z + 1), color.getColorObj());
        }
    }

    @Override
    public void onPrepareHudRender(MatrixStack matrices, float tickDelta) {
        if (!this.isActive()) return;
        ArrayList<String> info = new ArrayList<>();
        info.add("Distance: " + String.format("%.2f", getDistance(blockPos1.getPos(), blockPos2.getPos()) + 1));
        info.add("X: " + ((blockPos2.getX() - blockPos1.getX()) + 1));
        info.add("Y: " + ((blockPos2.getY() - blockPos1.getY()) + 1));
        info.add("Z: " + ((blockPos2.getZ() - blockPos1.getZ()) + 1));
        infoDisplay.addInfo(info, this);
    }

}
