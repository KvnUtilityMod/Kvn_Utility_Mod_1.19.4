package net.kvn.modules.shapes;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.SettingUpdate;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.*;
import net.kvn.utils.math.CircleUtil;
import net.kvn.utils.math.DoublePos;
import net.kvn.utils.math.rotation.Projection;
import net.kvn.utils.render.BoxUtil;
import net.kvn.utils.render.ColorUtils;
import net.kvn.utils.render.RenderBoxUtil;
import net.kvn.utils.world.BlockPasteAction;
import net.kvn.utils.world.BlockArrayUtil;
import net.minecraft.block.Block;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
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

    public IntegerValue circleWidth = addSetting(new IntegerValue("Circle Width", "Circle Width in cm", this, 100, 1, 100000));
    public IntegerValue amountOfCircles = addSetting(new IntegerValue("Amount of Circles", "Amount of Circles", this, 1, 1, 10000));
    public IntegerValue circleHeight = addSetting(new IntegerValue("Circle Height", "Circle Height in cm", this, 100, 1, 100000));
    public IntegerValue layers = addSetting(new IntegerValue("Layers", "Layers", this, 1, 1, 10000));
    public IntegerValue blockAmount = addSetting(new IntegerValue("Blocks", "blocks", this, 40, 1, 1000));
    public ModeValue circleMode = addSetting(new ModeValue("Circle Mode", "mode", this, new String[]{"Full", "Short", "Long"}, 0));
    public ModeValue precisionMode = addSetting(new ModeValue("Render mode", "determine how the circle is rendered", this, new String[]{"Precision", "On Block"}, 0));
    public ColorValue color = addSetting(new ColorValue("Color", "color", this, null));
    public BlockTypeValue blockToPaste = addSetting(new BlockTypeValue("Block to Paste", "blockType", this, "stone"));
    public BooleanValue pasteCircle = addSetting(new BooleanValue("Paste Circle", "Paste Circle", this, false));

    private ArrayList<DoublePos> circlePoints;

    public Circle() {
        super("Circle", "circle", Category.SHAPES);
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

        if (circlePoints == null) {
            calculateCirclePoints();
        }

        //render the positions of the blocks on the circle and the middle point
        if (dimension.getMode() == 0) {
            RenderBoxUtil.draw3d(matrices, new Box(pos1.getPos()), ColorUtils.getFullAlpha(color.getDarkerColor(85)));
            RenderBoxUtil.draw3d(matrices, new Box(pos2.getPos()), ColorUtils.getFullAlpha(color.getDarkerColor(85)));
            RenderBoxUtil.draw3d(matrices, new Box(pos3.getPos()), ColorUtils.getFullAlpha(color.getDarkerColor(85)));
            RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(CircleUtil.getMiddlePoint3DCircle(new DoublePos(pos1.getPos()), new DoublePos(pos2.getPos()), new DoublePos(pos3.getPos()))), ColorUtils.getFullAlpha(color.getDarkerColor(30)));
        } else {
            RenderBoxUtil.draw3d(matrices, new Box(middlePoint2D.getPos()), ColorUtils.getFullAlpha(color.getDarkerColor(85)));
            if (selectMode2D.getMode() == 1) {
                RenderBoxUtil.draw3d(matrices, new Box(posOnCircle2D.getPos()), ColorUtils.getFullAlpha(color.getDarkerColor(85)));
            }
        }

        //render the circleShape
        if (circlePoints != null) {
            if (precisionMode.getMode() == 0) {
                for (DoublePos doublePos : circlePoints) {
                    RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(doublePos), color.getColorObj());
                }
            } else {
                for (BlockPos blockPos : BlockArrayUtil.getUniqueBlockPositions(circlePoints)) {
                    RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(blockPos), color.getColorObj());
                }
            }
        }
    }

    @Override
    public void onSettingUpdate(Setting setting) {
        if (setting == pasteCircle) {
            if (pasteCircle.isTrue()) {
                Block block = blockToPaste.getBlockType2();
                new Thread(() -> {
                    ArrayList<BlockPos> positionsToPaste = BlockArrayUtil.getUniqueBlockPositions(circlePoints);
                    blockPlacer.paste(new BlockPasteAction(positionsToPaste, block.getDefaultState()));
                    pasteCircle.updateValue(false);
                }).start();
            }
        }

        if (setting.getModule() == this) {
            calculateCirclePoints();
        }
    }

    public void calculateCirclePoints() {
        ArrayList<DoublePos> tempCirclePoints = new ArrayList<>();
        Projection projectionForHeight = new Projection();
        double maxDelta = circleWidth.getValue() -1;
        maxDelta = maxDelta / 2;
        double delta = - maxDelta;

        if (amountOfCircles.getValue() <= 1) {
            delta = 0;
        }

        //3d mode -> calculate the circle in 3d
        if (dimension.getMode() == 0) {
            DoublePos pos1 = new DoublePos(this.pos1.getPos());
            DoublePos pos2 = new DoublePos(this.pos2.getPos());
            DoublePos pos3 = new DoublePos(this.pos3.getPos());
            DoublePos middlePoint = CircleUtil.getMiddlePoint3DCircle(pos1, pos2, pos3);
            for (int i = 0; i < amountOfCircles.getValue(); i++) {
                tempCirclePoints.addAll(
                        CircleUtil.getBlocksOn3DCircle(
                                pos1.getDoublePosCloserTo(middlePoint, delta),
                                pos2.getDoublePosCloserTo(middlePoint, delta),
                                pos3.getDoublePosCloserTo(middlePoint, delta),
                                circleMode.getMode(),
                                blockAmount.getValue()
                        )
                );
                delta += maxDelta * 2 / (amountOfCircles.getValue() - 1);
            }

            projectionForHeight = Projection.getProjection(pos1, pos2, pos3);
        }

        if (dimension.getMode() == 1) {
            //2d mode && selectMode2D == radius -> calculate the circle in 2d with a radius
            if (selectMode2D.getMode() == 0) {
                DoublePos middlePoint = new DoublePos(this.middlePoint2D.getPos());
                double tmpRadius = radius2D.getValue() - maxDelta;
                for (int i = 0; i < amountOfCircles.getValue(); i++) {
                    tempCirclePoints.addAll(
                            CircleUtil.getBlocksOn2DCircle(
                                    middlePoint,
                                    null,
                                    tmpRadius,
                                    plane2D.getMode(),
                                    blockAmount.getValue()
                            )
                    );
                    tmpRadius += maxDelta * 2 / (amountOfCircles.getValue() - 1);
                }

                ArrayList<DoublePos> positionsForProjection = CircleUtil.getBlocksOn2DCircle(middlePoint, null, radius2D.getValue(), plane2D.getMode(), 3);
                projectionForHeight = Projection.getProjection(positionsForProjection.get(0), positionsForProjection.get(1), positionsForProjection.get(2));
            }

            //2d mode && selectMode2D == point on circle -> calculate the circle in 2d with 2 points
            if (selectMode2D.getMode() == 1) {
                DoublePos middlePoint = new DoublePos(this.middlePoint2D.getPos());
                DoublePos posOnCircle = new DoublePos(this.posOnCircle2D.getPos());
                for (int i = 0; i < amountOfCircles.getValue(); i++) {
                    tempCirclePoints.addAll(
                            CircleUtil.getBlocksOn2DCircle(
                                    middlePoint,
                                    posOnCircle.getDoublePosCloserTo(middlePoint, delta),
                                    0,
                                    plane2D.getMode(),
                                    blockAmount.getValue()
                            )
                    );
                    delta += maxDelta * 2 / (amountOfCircles.getValue() - 1);
                }

                ArrayList<DoublePos> positionsForProjection = CircleUtil.getBlocksOn2DCircle(middlePoint, posOnCircle, 0, plane2D.getMode(), 3);
                projectionForHeight = Projection.getProjection(positionsForProjection.get(0), positionsForProjection.get(1), positionsForProjection.get(2));
            }
        }

        double maxDepthDelta = circleHeight.getValue() - 1;
        maxDepthDelta = maxDepthDelta / 2;
        double depthDelta;

        if (layers.getValue() <= 1 || circleHeight.getValue() <= 1) {
            circlePoints = tempCirclePoints;
            return;
        }

        ArrayList<DoublePos> tempCirclePoints2 = new ArrayList<>();
        for (DoublePos doublePos : tempCirclePoints) {
            DoublePos projectedPos = projectionForHeight.project(doublePos);
            depthDelta = - maxDepthDelta;
            for (int i = 0; i < layers.getValue(); i++) {
                tempCirclePoints2.add(projectionForHeight.projectBack(new DoublePos(projectedPos.getX(), projectedPos.getY(), projectedPos.getZ() + depthDelta)));
                depthDelta += maxDepthDelta * 2 / (layers.getValue() - 1);
            }
        }

        circlePoints = tempCirclePoints2;
    }
}



