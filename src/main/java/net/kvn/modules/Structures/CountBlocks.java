package net.kvn.modules.Structures;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.RenderBox;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.BlockPosValue;
import net.kvn.settings.BooleanValue;
import net.kvn.settings.ColorValue;
import net.kvn.utils.PlayerUtil;
import net.kvn.utils.render.RenderBoxUtil;
import net.kvn.utils.world.WorldUtil;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.math.BlockPos;

import java.util.List;

import static net.kvn.KvnUtilityMod.mc;

public class CountBlocks extends Module implements RenderBox {

    public BooleanValue showOutline = addSetting(new BooleanValue("show outline", "Show box", this, false));
    public ColorValue color = addSetting(new ColorValue("Color", "Color of the box", this));
    public BlockPosValue pos1 = addSetting(new BlockPosValue("Pos1", "First position", this));
    public BlockPosValue pos2 = addSetting(new BlockPosValue("Pos2", "Second position", this));

    public CountBlocks() {
        super("CountBlocks", "Counts the number of blocks in a structure", Category.STRUCTURES);
    }

    @Override
    public void onEnable() {

        int xMin = Math.min(pos1.getPos().getX(), pos2.getPos().getX());
        int xMax = Math.max(pos1.getPos().getX(), pos2.getPos().getX());
        int yMin = Math.min(pos1.getPos().getY(), pos2.getPos().getY());
        int yMax = Math.max(pos1.getPos().getY(), pos2.getPos().getY());
        int zMin = Math.min(pos1.getPos().getZ(), pos2.getPos().getZ());
        int zMax = Math.max(pos1.getPos().getZ(), pos2.getPos().getZ());

        int count = 0;

        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                for (int z = zMin; z <= zMax; z++) {
                    if (WorldUtil.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.AIR) {
                        count++;
                    }
                }
            }
        }

        mc.player.sendMessage(Text.literal("Blockcount: " + count));
    }

    @Override
    public void renderBox(WorldRenderContext context, MatrixStack matrices) {
        if (showOutline.isTrue()) {
            RenderBoxUtil.DrawBoxOutline(matrices, pos1.getPos(), pos2.getPos(), 0.12, color.getColorObj());
        }
    }
}
