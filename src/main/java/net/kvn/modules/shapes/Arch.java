package net.kvn.modules.shapes;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.RenderBox;
import net.kvn.event.events.SettingUpdate;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.*;
import net.kvn.utils.math.DoublePos;
import net.kvn.utils.math.rotation.Projection;
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
        for (DoublePos pos : getArchBlocks()) {
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
                ArrayList<DoublePos> blocks = getArchBlocks();
                blockPlacer.paste(new BlockPasteAction(blockToPaste.getBlockType2().getDefaultState(), blocks));
                pasteCircle.updateValue(false);
            }
        }
    }

    public ArrayList<DoublePos> getArchBlocks() {
        ArrayList<DoublePos> positions2D = new ArrayList<>();
        ArrayList<DoublePos> result = new ArrayList<>();

        //get the right projection
        Projection projection = Projection.getProjection(pos1.getPos(), pos3.getPos(), pos2.getPos());
        DoublePos projPos1 = projection.project(new DoublePos(pos1.getPos()));
        DoublePos projPos2 = projection.project(new DoublePos(pos2.getPos()));
        DoublePos projPos3 = projection.project(new DoublePos(pos3.getPos()));
        double radius = Math.abs(projPos3.getX()) / 2;
        double height = projPos2.getY();

        RenderBoxUtil.draw3d(new MatrixStack(), BoxUtil.createBox(projPos1.toBlockPos()), color.getColorObj());
        RenderBoxUtil.draw3d(new MatrixStack(), BoxUtil.createBox(projPos2.toBlockPos()), color.getColorObj());
        RenderBoxUtil.draw3d(new MatrixStack(), BoxUtil.createBox(projPos3.toBlockPos()), color.getColorObj());

        //get the 2d positions
        for (int i = 0; i < blockAmount.getValue(); i++) {
            double angle = (i * Math.PI) / blockAmount.getValue();
            double x = radius * Math.cos(angle) + radius;
            double y = radius * Math.sin(angle) * (height / (radius + 0.000000001));
            //System.out.println("radius: " + radius + " height: " + height + " x: " + x + " y: " + y);
            //System.out.println("x: " + x + " y: " + y);
            positions2D.add(new DoublePos(x, y, 0));
        }

        //render the 2d positions to debug
        result.addAll(positions2D);

        //get the 3d positions
        for (DoublePos pos : positions2D) {
            result.add(projection.projectBack(pos));
        }

        return result;
    }


}
