package net.kvn.modules.shapes;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.RenderBox;
import net.kvn.event.events.SettingUpdate;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.*;
import net.kvn.utils.math.ArchUtil;
import net.kvn.utils.math.DoublePos;
import net.kvn.utils.render.BoxUtil;
import net.kvn.utils.render.RenderBoxUtil;
import net.kvn.utils.world.BlockPasteAction;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.blockPlacer;

public class Arch extends Module implements RenderBox, SettingUpdate {

    //3pos
    public BlockPosValue pos1 = addSetting(new BlockPosValue("Pos1", "pos1", this));
    public BlockPosValue pos2 = addSetting(new BlockPosValue("Pos2", "pos2", this));
    public BlockPosValue pos3 = addSetting(new BlockPosValue("Pos3", "pos3", this));
    public IntegerValue blockAmount = addSetting(new IntegerValue("Blocks", "blocks", this, 80, 1, 100000));
    public ModeValue blockMode = addSetting(new ModeValue("BlockMode", "mode", this, new String[]{"Precision", "On block"}, 0));
    public ColorValue color = addSetting(new ColorValue("Color", "color", this, null));
    public BlockTypeValue blockToPaste = addSetting(new BlockTypeValue("Block to Paste", "blockType", this, "stone"));
    public BooleanValue pasteCircle = addSetting(new BooleanValue("Paste Circle", "Paste Circle", this, false));

    public Arch() {
        super("Arch", "arch", Category.SHAPES);
    }

    @Override
    public void renderBox(WorldRenderContext context, MatrixStack matrices) {
        if (!this.isActive()) return;

        //render the 3 positions
        RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(pos1.getPos()), color.getDarkerColor(20));
        RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(pos2.getPos()), color.getDarkerColor(20));
        RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(pos3.getPos()), color.getDarkerColor(20));

        //render the arch
        for (DoublePos pos : ArchUtil.getArchBlocks(new DoublePos(pos1.getPos()), new DoublePos(pos2.getPos()), new DoublePos(pos3.getPos()), blockAmount.getValue())) {
            if (blockMode.getMode() == 0) {
                RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(pos), color.getColorObj());
            } else {
                RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(pos.toBlockPos()), color.getColorObj());
            }
        }
    }

    @Override
    public void onSettingUpdate(Setting setting) {
        if (setting == pasteCircle) {
            if (pasteCircle.isTrue()) {
                ArrayList<DoublePos> blocks = ArchUtil.getArchBlocks(new DoublePos(pos1.getPos()), new DoublePos(pos2.getPos()), new DoublePos(pos3.getPos()), blockAmount.getValue());
                blockPlacer.paste(new BlockPasteAction(blockToPaste.getBlockType2().getDefaultState(), blocks));
                pasteCircle.updateValue(false);
            }
        }
    }

    /*
    public ArrayList<DoublePos> getArchBlocks() {

        ArrayList<DoublePos> result = new ArrayList<>();

        // Get the right projection
        Projection projection = Projection.getProjection(pos1.getPos(), pos3.getPos(), pos2.getPos());
        DoublePos projPos1 = projection.project(new DoublePos(pos1.getPos()));
        DoublePos projPos2 = projection.project(new DoublePos(pos2.getPos()));
        DoublePos projPos3 = projection.project(new DoublePos(pos3.getPos()));

        double startAngle = 0;
        double endAngle = 359;
        double heightMultiplier = (projPos2.getY() + 0.000001) / projPos3.getX();
        double angleStep = (endAngle - startAngle) / blockAmount.getValue();

        for (double angle = startAngle; angle <= endAngle; angle += angleStep) {
            double x = angle / 360 * Math.abs(projPos3.getX() - projPos1.getX());
            double y = archHeight(Math.abs(projPos3.getX() - projPos1.getX()), Math.toRadians(angle)) * heightMultiplier;
            result.add(projection.projectBack(new DoublePos(x, y, 0)));
        }

        return result;
    }

     */

    public static double archHeight(double r, double a) {
        double x = r + r * Math.sin(a/2);
        return x - r;
    }
}
