package net.kvn.modules.shapes;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.SettingUpdate;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.*;
import net.kvn.utils.math.CirlceShape;
import net.kvn.utils.math.DoublePos;
import net.kvn.utils.render.BoxUtil;
import net.kvn.utils.render.ColorUtils;
import net.kvn.utils.render.RenderBoxUtil;
import net.kvn.utils.world.BlockPasteAction;
import net.minecraft.block.Block;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.blockPlacer;


public class Circle extends Module implements net.kvn.event.events.RenderBox, SettingUpdate {

    public ModeValue dimension = addSetting(new ModeValue("Dimension", "Dimension", this, new String[]{"3D", "2D"}, 0));
    //3d mode
    public BlockPosValue pos1 = addSetting(new BlockPosValue("Pos1", "pos1", this, dimension));
    public BlockPosValue pos2 = addSetting(new BlockPosValue("Pos2", "pos2", this, dimension));
    public BlockPosValue pos3 = addSetting(new BlockPosValue("Pos3", "pos3", this, dimension));
    //2d mode
    public ModeValue plane2D = addSetting(new ModeValue("Plane", "plane", this, dimension, new String[]{"XZ", "XY", "YZ"}, 0));
    public BlockPosValue middlePoint2D = addSetting(new BlockPosValue("MiddlePoint", "MiddlePoint", this, dimension));
    public ModeValue selectMode2D = addSetting(new ModeValue("SelectMode", "SelectMode", this, dimension, new String[]{"Radius", "Point on circle"}, 0));
    public IntegerValue radius2D = addSetting(new IntegerValue("Radius", "Radius", this, selectMode2D, 25, 0, 1000));
    public BlockPosValue posOnCircle2D = addSetting(new BlockPosValue("PointOnCircle", "PointOnCircle", this, selectMode2D));

    public IntegerValue blockAmount = addSetting(new IntegerValue("Blocks", "blocks", this, 40, 1, 1000));
    public ModeValue circleMode = addSetting(new ModeValue("Circle Mode", "mode", this, new String[]{"Full", "Short", "Long"}, 0));
    public ModeValue precisionMode = addSetting(new ModeValue("Render mode", "determine how the circle is rendered", this, new String[]{"Precision", "On Block"}, 0));
    //public BooleanValue middlePoint = addSetting(new BooleanValue("MiddlePoint", "middlePoint", this, true));
    public ColorValue color = addSetting(new ColorValue("Color", "color", this, null));
    public BlockTypeValue blockToPaste = addSetting(new BlockTypeValue("Block to Paste", "blockType", this, "stone"));
    public BooleanValue pasteCircle = addSetting(new BooleanValue("Paste Circle", "Paste Circle", this, false));
    public BooleanValue debugBlocks = addSetting(new BooleanValue("DebugBlocks", "debugBlocks", this, false));
    public ColorValue debugColor = addSetting(new ColorValue("DebugColor", "debugColor", this, null));

    private CirlceShape circleShape;

    public Circle() {
        super("Circle", "circle", Category.SHAPES);
        circleShape = new CirlceShape();
        pos1.setModeIndex(0);
        pos2.setModeIndex(0);
        pos3.setModeIndex(0);
        plane2D.setModeIndex(1);
        middlePoint2D.setModeIndex(1);
        selectMode2D.setModeIndex(1);
        radius2D.setModeIndex(0);
        posOnCircle2D.setModeIndex(1);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void renderBox(WorldRenderContext context, MatrixStack matrices) {
        if (!this.isActive()) return;

        //update the circleShape positions if needed
        if (circleShape.getPos1() == null || circleShape.getPos1().getX() != pos1.getX() || circleShape.getPos1().getY() != pos1.getY() || circleShape.getPos1().getZ() != pos1.getZ()) circleShape.setPos1(pos1.getPos());
        if (circleShape.getPos2() == null || circleShape.getPos2().getX() != pos2.getX() || circleShape.getPos2().getY() != pos2.getY() || circleShape.getPos2().getZ() != pos2.getZ()) circleShape.setPos2(pos2.getPos());
        if (circleShape.getPos3() == null || circleShape.getPos3().getX() != pos3.getX() || circleShape.getPos3().getY() != pos3.getY() || circleShape.getPos3().getZ() != pos3.getZ()) circleShape.setPos3(pos3.getPos());

        //render the positions
        if (dimension.getMode() == 0) {
            RenderBoxUtil.draw3d(matrices, new Box(pos1.getPos()), ColorUtils.getFullAlpha(color.getDarkerColor(85)));
            RenderBoxUtil.draw3d(matrices, new Box(pos2.getPos()), ColorUtils.getFullAlpha(color.getDarkerColor(85)));
            RenderBoxUtil.draw3d(matrices, new Box(pos3.getPos()), ColorUtils.getFullAlpha(color.getDarkerColor(85)));
            RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(circleShape.getMiddlePoint()), ColorUtils.getFullAlpha(color.getDarkerColor(30)));
        } else {
            RenderBoxUtil.draw3d(matrices, new Box(middlePoint2D.getPos()), ColorUtils.getFullAlpha(color.getDarkerColor(85)));
            if (selectMode2D.getMode() == 1) {
                RenderBoxUtil.draw3d(matrices, new Box(posOnCircle2D.getPos()), ColorUtils.getFullAlpha(color.getDarkerColor(85)));
            }
        }

        //render the circleShape
        if (circleShape.arePositionsSet()) {
            for (DoublePos doublePos : circleShape.getBlocksOnCircle(matrices, blockAmount.getValue())) {
                if (precisionMode.getMode() == 0) {
                    RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(doublePos), color.getColorObj());
                } else {
                    RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(doublePos.toBlockPos()), color.getColorObj());
                }
            }
        }
    }

    @Override
    public void onSettingUpdate(Setting setting) {
        if (setting == pasteCircle) {
            if (pasteCircle.isTrue()) {
                Block block = blockToPaste.getBlockType2();
                ArrayList<DoublePos> positions = circleShape.getBlocksOnCircle(null, blockAmount.getValue());
                blockPlacer.paste(new BlockPasteAction(block.getDefaultState(), positions));

                pasteCircle.updateValue(false);
            }
        }
    }

}



