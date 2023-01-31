package net.kvn.modules.shapes;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.RenderBox;
import net.kvn.event.events.SettingUpdate;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.*;
import net.kvn.utils.math.DoublePos;
import net.kvn.utils.render.BoxUtil;
import net.kvn.utils.render.RenderBoxUtil;
import net.kvn.utils.world.BlockPasteAction;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.blockPlacer;

public class Line extends Module implements RenderBox, SettingUpdate {

    public BlockPosValue pos1 = addSetting(new BlockPosValue("Pos1", "The first position of the line", this));
    public BlockPosValue pos2 = addSetting(new BlockPosValue("Pos2", "The second position of the line", this));
    public IntegerValue amountOfBlocks = addSetting(new IntegerValue("#Blocks", "The amount of blocks on the line", this, 300, 1, 100000));
    public ModeValue precisionMode = addSetting(new ModeValue("Render mode", "determine how the circle is rendered", this, new String[]{"Precision", "On Block"}, 0));
    public ColorValue color = addSetting(new ColorValue("Color", "The color of the line", this));
    public BlockTypeValue blockType = addSetting(new BlockTypeValue("BlockType", "The type of block to be placed", this, "stone"));
    public BooleanValue paste = addSetting(new BooleanValue("Paste", "Pastes the line when activated", this, true));

    public Line() {
        super("Line", "Draws a line when activated", Category.SHAPES);
    }

    @Override
    public void renderBox(WorldRenderContext context, MatrixStack matrices) {
        if (!this.isActive()) return;

        for (DoublePos pos : getBlocksOnLine()) {
            if (precisionMode.getMode() == 0) {
                RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(pos), color.getColorObj());
            } else {
                RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(pos.toBlockPos()), color.getColorObj());
            }
        }
    }

    @Override
    public void onSettingUpdate(Setting setting) {
        if (setting == paste) {
            if (paste.isTrue()) {
                ArrayList<BlockPos> blocks = new ArrayList<>();
                for (DoublePos pos : getBlocksOnLine()) {
                    blocks.add(pos.toBlockPos());
                }

                blockPlacer.paste(new BlockPasteAction(blocks, blockType.getBlockType2().getDefaultState()));
                paste.updateValue(false);
            }
        }
    }

    public ArrayList<DoublePos> getBlocksOnLine() {
        ArrayList<DoublePos> blocks = new ArrayList<>();
        double x1 = pos1.getX();
        double y1 = pos1.getY();
        double z1 = pos1.getZ();
        double x2 = pos2.getX();
        double y2 = pos2.getY();
        double z2 = pos2.getZ();
        double xDiff = x2 - x1;
        double yDiff = y2 - y1;
        double zDiff = z2 - z1;
        double xStep = xDiff / amountOfBlocks.getValue();
        double yStep = yDiff / amountOfBlocks.getValue();
        double zStep = zDiff / amountOfBlocks.getValue();
        for (int i = 0; i < amountOfBlocks.getValue(); i++) {
            blocks.add(new DoublePos(x1 + xStep * i, y1 + yStep * i, z1 + zStep * i));
        }

        blocks.add(new DoublePos(x2, y2, z2));
        return blocks;
    }
}
