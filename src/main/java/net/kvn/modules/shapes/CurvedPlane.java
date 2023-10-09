package net.kvn.modules.shapes;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.RenderBox;
import net.kvn.event.events.SettingUpdate;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.*;
import net.kvn.utils.math.ArchUtil;
import net.kvn.utils.math.CircleUtil;
import net.kvn.utils.math.DoublePos;
import net.kvn.utils.math.LineUtil;
import net.kvn.utils.render.BoxUtil;
import net.kvn.utils.render.RenderBoxUtil;
import net.kvn.utils.world.BlockArrayUtil;
import net.kvn.utils.world.BlockPasteAction;
import net.kvn.utils.world.WorldUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.blockPlacer;

public class CurvedPlane extends Module implements RenderBox, SettingUpdate {

    //all settings
    public SettingPlaceHolder line1 = addSetting(new SettingPlaceHolder("Line 1", "", this));
    public ModeValue lineMode1 = addSetting(new ModeValue("lineMode", "Circle or Arch", this, line1, new String[]{"Circle", "Arch", "Line"}, 0));
    //settings of circle
    public ModeValue dimensionCircle1 = addSetting(new ModeValue("Dimension", "Dimension", this, lineMode1, new String[]{"3D", "2D"}, 0));
    public ModeValue circleModeCircle1 = addSetting(new ModeValue("Circle Mode", "mode", this, lineMode1, new String[]{"Full", "Short", "Long"}, 0));
    //3d mode
    public BlockPosValue pos1Circle1 = addSetting(new BlockPosValue("Pos1", "pos1", this, dimensionCircle1));
    public BlockPosValue pos2Circle1 = addSetting(new BlockPosValue("Pos2", "pos2", this, dimensionCircle1));
    public BlockPosValue pos3Circle1 = addSetting(new BlockPosValue("Pos3", "pos3", this, dimensionCircle1));
    //2d mode
    public ModeValue plane2DCircle1 = addSetting(new ModeValue("Plane", "plane", this, dimensionCircle1, new String[]{"XZ", "XY", "YZ"}, 0));
    public BlockPosValue middlePoint2DCircle1 = addSetting(new BlockPosValue("MiddlePoint", "MiddlePoint", this, dimensionCircle1));
    public ModeValue selectMode2DCircle1 = addSetting(new ModeValue("SelectMode", "SelectMode", this, dimensionCircle1, new String[]{"Radius", "Point on circle"}, 0));
    public IntegerValue radius2DCircle1 = addSetting(new IntegerValue("Radius", "Radius", this, selectMode2DCircle1, 25, 0, 1000));
    public BlockPosValue posOnCircle2DCircle1 = addSetting(new BlockPosValue("PointOnCircle", "PointOnCircle", this, selectMode2DCircle1));
    //settings of arch
    public BlockPosValue pos1Arch1 = addSetting(new BlockPosValue("Pos1", "pos1", this, lineMode1));
    public BlockPosValue pos2Arch1 = addSetting(new BlockPosValue("Pos2", "pos2", this, lineMode1));
    public BlockPosValue pos3Arch1 = addSetting(new BlockPosValue("Pos3", "pos3", this, lineMode1));
    //settings of line
    public BlockPosValue pos1Line1 = addSetting(new BlockPosValue("Pos1", "pos1", this, lineMode1));
    public BlockPosValue pos2Line1 = addSetting(new BlockPosValue("Pos2", "pos2", this, lineMode1));
    //settings of line1
    public IntegerValue blockAmountLine1 = addSetting(new IntegerValue("Blocks", "blocks", this, line1, 40, 1, 1000));
    public ColorValue colorLine1 = addSetting(new ColorValue("Color", "color", this, line1));

    public Setting line2 = addSetting(new SettingPlaceHolder("Line 2", "", this));
    public ModeValue lineMode2 = addSetting(new ModeValue("lineMode", "Circle or Arch", this, line2, new String[]{"Circle", "Arch", "Line"}, 0));
    //settings of circle
    public ModeValue dimensionCircle2 = addSetting(new ModeValue("Dimension", "Dimension", this, lineMode2, new String[]{"3D", "2D"}, 0));
    public ModeValue circleModeCircle2 = addSetting(new ModeValue("Circle Mode", "mode", this, lineMode2, new String[]{"Full", "Short", "Long"}, 0));
    //3d mode
    public BlockPosValue pos1Circle2 = addSetting(new BlockPosValue("Pos1", "pos1", this, dimensionCircle2));
    public BlockPosValue pos2Circle2 = addSetting(new BlockPosValue("Pos2", "pos2", this, dimensionCircle2));
    public BlockPosValue pos3Circle2 = addSetting(new BlockPosValue("Pos3", "pos3", this, dimensionCircle2));
    //2d mode
    public ModeValue plane2DCircle2 = addSetting(new ModeValue("Plane", "plane", this, dimensionCircle2, new String[]{"XZ", "XY", "YZ"}, 0));
    public BlockPosValue middlePoint2DCircle2 = addSetting(new BlockPosValue("MiddlePoint", "MiddlePoint", this, dimensionCircle2));
    public ModeValue selectMode2DCircle2 = addSetting(new ModeValue("SelectMode", "SelectMode", this, dimensionCircle2, new String[]{"Radius", "Point on circle"}, 0));
    public IntegerValue radius2DCircle2 = addSetting(new IntegerValue("Radius", "Radius", this, selectMode2DCircle2, 25, 0, 1000));
    public BlockPosValue posOnCircle2DCircle2 = addSetting(new BlockPosValue("PointOnCircle", "PointOnCircle", this, selectMode2DCircle2));
    //settings of arch
    public BlockPosValue pos1Arch2 = addSetting(new BlockPosValue("Pos1", "pos1", this, lineMode2));
    public BlockPosValue pos2Arch2 = addSetting(new BlockPosValue("Pos2", "pos2", this, lineMode2));
    public BlockPosValue pos3Arch2 = addSetting(new BlockPosValue("Pos3", "pos3", this, lineMode2));
    //settings of line
    public BlockPosValue pos1Line2 = addSetting(new BlockPosValue("Pos1", "pos1", this, lineMode2));
    public BlockPosValue pos2Line2 = addSetting(new BlockPosValue("Pos2", "pos2", this, lineMode2));
    //settings of line2
    public IntegerValue blockAmountLine2 = addSetting(new IntegerValue("Blocks", "blocks", this, line2, 40, 1, 1000));
    public ColorValue colorLine2 = addSetting(new ColorValue("Color", "color", this, line2));

    public SettingPlaceHolder line3 = addSetting(new SettingPlaceHolder("Line 3", "", this));
    public ModeValue lineMode3 = addSetting(new ModeValue("lineMode", "Circle or Arch", this, line3, new String[]{"Circle", "Arch", "Line"}, 0));
    //settings of circle
    public ModeValue dimensionCircle3 = addSetting(new ModeValue("Dimension", "Dimension", this, lineMode3, new String[]{"3D", "2D"}, 0));
    public ModeValue circleModeCircle3 = addSetting(new ModeValue("Circle Mode", "mode", this, lineMode3, new String[]{"Full", "Short", "Long"}, 0));
    //3d mode
    public BlockPosValue pos1Circle3 = addSetting(new BlockPosValue("Pos1", "pos1", this, dimensionCircle3));
    public BlockPosValue pos2Circle3 = addSetting(new BlockPosValue("Pos2", "pos2", this, dimensionCircle3));
    public BlockPosValue pos3Circle3 = addSetting(new BlockPosValue("Pos3", "pos3", this, dimensionCircle3));
    //2d mode
    public ModeValue plane2DCircle3 = addSetting(new ModeValue("Plane", "plane", this, dimensionCircle3, new String[]{"XZ", "XY", "YZ"}, 0));
    public BlockPosValue middlePoint2DCircle3 = addSetting(new BlockPosValue("MiddlePoint", "MiddlePoint", this, dimensionCircle3));
    public ModeValue selectMode2DCircle3 = addSetting(new ModeValue("SelectMode", "SelectMode", this, dimensionCircle3, new String[]{"Radius", "Point on circle"}, 0));
    public IntegerValue radius2DCircle3 = addSetting(new IntegerValue("Radius", "Radius", this, selectMode2DCircle3, 25, 0, 1000));
    public BlockPosValue posOnCircle2DCircle3 = addSetting(new BlockPosValue("PointOnCircle", "PointOnCircle", this, selectMode2DCircle3));
    //settings of arch
    public BlockPosValue pos1Arch3 = addSetting(new BlockPosValue("Pos1", "pos1", this, lineMode3));
    public BlockPosValue pos2Arch3 = addSetting(new BlockPosValue("Pos2", "pos2", this, lineMode3));
    public BlockPosValue pos3Arch3 = addSetting(new BlockPosValue("Pos3", "pos3", this, lineMode3));
    //settings of line
    public BlockPosValue pos1Line3 = addSetting(new BlockPosValue("Pos1", "pos1", this, lineMode3));
    public BlockPosValue pos2Line3 = addSetting(new BlockPosValue("Pos2", "pos2", this, lineMode3));
    //settings of line3
    public IntegerValue blockAmountLine3 = addSetting(new IntegerValue("Blocks", "blocks", this, line3, 40, 1, 1000));
    public ColorValue colorLine3 = addSetting(new ColorValue("Color", "color", this, line3));

    //connection
    public SettingPlaceHolder connection = addSetting(new SettingPlaceHolder("Connection", "", this));
    public ModeValue connectionMode = addSetting(new ModeValue("Mode", "connection mode", this, connection, new String[]{"Circle", "Arch", "Line"}, 0));
    public ModeValue circleModeConnection = addSetting(new ModeValue("Circle Mode", "mode", this, connection, new String[]{"Full", "Short", "Long"}, 0));
    public IntegerValue blockAmountConnection = addSetting(new IntegerValue("Blocks", "blocks", this, connection, 80, 1, 1000));
    public ColorValue colorConnection = addSetting(new ColorValue("Color", "color", this, connection));

    public ModeValue precisionMode = addSetting(new ModeValue("Render mode", "determine how the circle is rendered", this, new String[]{"Precision", "On Block"}, 0));
    public BlockTypeValue blockToPaste = addSetting(new BlockTypeValue("Block to Paste", "blockType", this, "stone"));
    public BooleanValue pasteCircle = addSetting(new BooleanValue("Paste Plane", "Paste Plane", this, false));

    //actual variables
    private ArrayList<DoublePos> line1Blocks = null;
    private ArrayList<DoublePos> line2Blocks = null;
    private ArrayList<DoublePos> line3Blocks = null;
    private ArrayList<DoublePos> planeBlocks = new ArrayList<>();

    public CurvedPlane() {
        super("CurvedPlane", "Draws a curved plane", Category.SHAPES);

        //c1
        dimensionCircle1.setModeIndex(0);
        circleModeCircle1.setModeIndex(0);
        pos1Circle1.setModeIndex(0);
        pos2Circle1.setModeIndex(0);
        pos3Circle1.setModeIndex(0);
        plane2DCircle1.setModeIndex(1);
        middlePoint2DCircle1.setModeIndex(1);
        selectMode2DCircle1.setModeIndex(1);
        radius2DCircle1.setModeIndex(0);
        posOnCircle2DCircle1.setModeIndex(1);
        pos1Arch1.setModeIndex(1);
        pos2Arch1.setModeIndex(1);
        pos3Arch1.setModeIndex(1);
        pos1Line1.setModeIndex(2);
        pos2Line1.setModeIndex(2);

        //c2
        dimensionCircle2.setModeIndex(0);
        circleModeCircle2.setModeIndex(0);
        pos1Circle2.setModeIndex(0);
        pos2Circle2.setModeIndex(0);
        pos3Circle2.setModeIndex(0);
        plane2DCircle2.setModeIndex(1);
        middlePoint2DCircle2.setModeIndex(1);
        selectMode2DCircle2.setModeIndex(1);
        radius2DCircle2.setModeIndex(0);
        posOnCircle2DCircle2.setModeIndex(1);
        pos1Arch2.setModeIndex(1);
        pos2Arch2.setModeIndex(1);
        pos3Arch2.setModeIndex(1);
        pos1Line2.setModeIndex(2);
        pos2Line2.setModeIndex(2);

        //c3
        dimensionCircle3.setModeIndex(0);
        circleModeCircle3.setModeIndex(0);
        pos1Circle3.setModeIndex(0);
        pos2Circle3.setModeIndex(0);
        pos3Circle3.setModeIndex(0);
        plane2DCircle3.setModeIndex(1);
        middlePoint2DCircle3.setModeIndex(1);
        selectMode2DCircle3.setModeIndex(1);
        radius2DCircle3.setModeIndex(0);
        posOnCircle2DCircle3.setModeIndex(1);
        pos1Arch3.setModeIndex(1);
        pos2Arch3.setModeIndex(1);
        pos3Arch3.setModeIndex(1);
        pos1Line3.setModeIndex(2);
        pos2Line3.setModeIndex(2);
    }


    @Override
    public void renderBox(WorldRenderContext context, MatrixStack matrices) {
        if (!this.isActive()) return;

        //get blocks if they're not calculated yet
        if (line1Blocks == null || line2Blocks == null || line3Blocks == null || planeBlocks == null) {
            calculatePlane();
        }

        //render blocks
        for (DoublePos pos : line1Blocks) {
            render(matrices, pos, colorLine1.getColorObj());
        }

        for (DoublePos pos : line2Blocks) {
            render(matrices, pos, colorLine2.getColorObj());
        }

        for (DoublePos pos : line3Blocks) {
            render(matrices, pos, colorLine3.getColorObj());
        }

        for (DoublePos pos : planeBlocks) {
            render(matrices, pos, colorConnection.getColorObj());
        }

    }

    @Override
    public void onSettingUpdate(Setting setting) {
        if (setting.getModule() == this) {
            if (setting == pasteCircle && pasteCircle.isTrue()) {
                blockPlacer.paste(new BlockPasteAction(BlockArrayUtil.getUniqueBlockPositions(planeBlocks), blockToPaste.getBlockType2().getDefaultState()));
                pasteCircle.updateValue(false);
                return;
            }
            calculatePlane();
        }
    }

    private void render(MatrixStack matrixStack, DoublePos pos, Color color) {
        if (precisionMode.getMode() == 0) {
            RenderBoxUtil.draw3d(matrixStack, BoxUtil.createBox(pos), color);
        } else {
            RenderBoxUtil.draw3d(matrixStack, BoxUtil.createBox(pos.toBlockPos()), color);
        }
    }

    public ArrayList<DoublePos> getBlocksFromLine(
            int lineMode, // "Circle", "Arch", "Line"
            int dimensionMode, //"3D", "2D"
            int circleMode, //"Full", "Short", "Long"
            BlockPos pos1Circle, BlockPos pos2Circle, BlockPos pos3Circle,
            int plane2D, // "XZ", "XY", "YZ"
            BlockPos middlePoint2D,
            int selectMode2D, // "Radius", "Point on circle"
            int radius2D,
            BlockPos posOnCircle2D,
            BlockPos pos1Arch, BlockPos pos2Arch,BlockPos pos3Arch,
            BlockPos pos1Line, BlockPos pos2Line,
            int blockAmountLine
            ) {
        ArrayList<DoublePos> blocks = new ArrayList<>();

        switch (lineMode) {
            case 0:
                //circle
                if (dimensionMode == 0) {
                    //3d mode
                    DoublePos pos1 = new DoublePos(pos1Circle);
                    DoublePos pos2 = new DoublePos(pos2Circle);
                    DoublePos pos3 = new DoublePos(pos3Circle);
                    return CircleUtil.getBlocksOn3DCircle(pos1, pos2, pos3, circleMode, blockAmountLine);
                } else {
                    //2d mode
                    switch (selectMode2D) {
                        case 0:
                            //radius
                            DoublePos middlePoint = new DoublePos(middlePoint2D);
                            return CircleUtil.getBlocksOn2DCircle(middlePoint, null, radius2D, plane2D, blockAmountLine);
                        case 1:
                            //point on circle
                            DoublePos middlePoint2 = new DoublePos(middlePoint2D);
                            DoublePos posOnCircle = new DoublePos(posOnCircle2D);
                            return CircleUtil.getBlocksOn2DCircle(middlePoint2, posOnCircle, 0, plane2D, blockAmountLine);
                    }
                }
                break;
            case 1:
                //arch
                return ArchUtil.getArchBlocks(new DoublePos(pos1Arch), new DoublePos(pos2Arch), new DoublePos(pos3Arch), blockAmountLine);
            case 2:
                //line
                for (int i = 0; i <= blockAmountLine; i++) {
                    double x = pos1Line.getX() + (pos2Line.getX() - pos1Line.getX()) * (double) i / (double) blockAmountLine;
                    double y = pos1Line.getY() + (pos2Line.getY() - pos1Line.getY()) * (double) i / (double) blockAmountLine;
                    double z = pos1Line.getZ() + (pos2Line.getZ() - pos1Line.getZ()) * (double) i / (double) blockAmountLine;
                    blocks.add(new DoublePos(x, y, z));
                }
                return blocks;
        }
        return blocks;
    }

    public void calculatePlane() {
        line1Blocks = getBlocksFromLine(
                lineMode1.getMode(),
                dimensionCircle1.getMode(),
                circleModeCircle1.getMode(),
                pos1Circle1.getPos(),
                pos2Circle1.getPos(),
                pos3Circle1.getPos(),
                plane2DCircle1.getMode(),
                middlePoint2DCircle1.getPos(),
                selectMode2DCircle1.getMode(),
                radius2DCircle1.getValue(),
                posOnCircle2DCircle1.getPos(),
                pos1Arch1.getPos(),
                pos2Arch1.getPos(),
                pos3Arch1.getPos(),
                pos1Line1.getPos(),
                pos2Line1.getPos(),
                blockAmountLine1.getValue()
        );

        line2Blocks = getBlocksFromLine(
                lineMode2.getMode(),
                dimensionCircle2.getMode(),
                circleModeCircle2.getMode(),
                pos1Circle2.getPos(),
                pos2Circle2.getPos(),
                pos3Circle2.getPos(),
                plane2DCircle2.getMode(),
                middlePoint2DCircle2.getPos(),
                selectMode2DCircle2.getMode(),
                radius2DCircle2.getValue(),
                posOnCircle2DCircle2.getPos(),
                pos1Arch2.getPos(),
                pos2Arch2.getPos(),
                pos3Arch2.getPos(),
                pos1Line2.getPos(),
                pos2Line2.getPos(),
                blockAmountLine2.getValue()
        );

        line3Blocks = getBlocksFromLine(
                lineMode3.getMode(),
                dimensionCircle3.getMode(),
                circleModeCircle3.getMode(),
                pos1Circle3.getPos(),
                pos2Circle3.getPos(),
                pos3Circle3.getPos(),
                plane2DCircle3.getMode(),
                middlePoint2DCircle3.getPos(),
                selectMode2DCircle3.getMode(),
                radius2DCircle3.getValue(),
                posOnCircle2DCircle3.getPos(),
                pos1Arch3.getPos(),
                pos2Arch3.getPos(),
                pos3Arch3.getPos(),
                pos1Line3.getPos(),
                pos2Line3.getPos(),
                blockAmountLine3.getValue()
        );

        ArrayList<DoublePos> blocks = new ArrayList<>();
        int amountOfBlocks = getAmountOfBlocks();
        switch (connectionMode.getMode()) {
            case 0:
                //Circle
                for (int i = 0; i <= amountOfBlocks; i++) {
                    blocks.addAll(CircleUtil.getBlocksOn3DCircle(getDoublePos(line1Blocks, (double) i / amountOfBlocks), getDoublePos(line2Blocks, (double) i / amountOfBlocks), getDoublePos(line3Blocks, (double) i / amountOfBlocks), circleModeConnection.getMode(), blockAmountConnection.getValue()));
                }
                break;
            case 1:
                //arch
                for (int i = 0; i < amountOfBlocks; i++) {
                    blocks.addAll(ArchUtil.getArchBlocks(getDoublePos(line1Blocks, (double) i / amountOfBlocks), getDoublePos(line2Blocks, (double) i / amountOfBlocks), getDoublePos(line3Blocks, (double) i / amountOfBlocks), blockAmountConnection.getValue()));
                }
                break;
            case 2:
                //line from line1 to line3
                for (int i = 0; i < amountOfBlocks; i++) {
                    blocks.addAll(LineUtil.getBlocksOnLine(getDoublePos(line1Blocks, (double) i / amountOfBlocks), getDoublePos(line3Blocks, (double) i / amountOfBlocks), blockAmountConnection.getValue()));
                }
                break;
        }
        this.planeBlocks = blocks;
    }

    private int getAmountOfBlocks() {
        return Math.max(line1Blocks.size(), Math.max(line2Blocks.size(), line3Blocks.size())) - 1;
    }

    private DoublePos getDoublePos(ArrayList<DoublePos> list, double progress) {
        return list.get((int) (Math.round(progress * (list.size() - 1))));
    }
}
