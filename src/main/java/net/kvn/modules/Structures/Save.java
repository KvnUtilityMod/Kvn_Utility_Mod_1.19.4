package net.kvn.modules.Structures;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.RenderBox;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.BlockPosValue;
import net.kvn.settings.BooleanValue;
import net.kvn.settings.ColorValue;
import net.kvn.utils.PlayerUtil;
import net.kvn.utils.render.BoxUtil;
import net.kvn.utils.render.RenderBoxUtil;
import net.kvn.utils.world.Structure.KvnStructure;
import net.minecraft.client.util.math.MatrixStack;

public class Save extends Module implements RenderBox {

    public BooleanValue showBox = addSetting(new BooleanValue("ShowBox", "Show box", this, false));
    public ColorValue color = addSetting(new ColorValue("Color", "Color of the box", this));
    public BlockPosValue pos1 = addSetting(new BlockPosValue("Pos1", "First position", this));
    public BlockPosValue pos2 = addSetting(new BlockPosValue("Pos2", "Second position", this));

    public KvnStructure structure = new KvnStructure();

    public Save() {
        super("Save", "Save structures", Category.STRUCTURES);
    }

    @Override
    public void onEnable() {
        structure.saveStructure(pos1.getPos(), pos2.getPos());
        PlayerUtil.sendChatMsg("Saved structure with " + structure.getBlockStatePosList().size() + " blocks in memory");
    }

    @Override
    public void renderBox(WorldRenderContext context, MatrixStack matrices) {
        if (showBox.isTrue()) {
            RenderBoxUtil.DrawBoxOutline(matrices, pos1.getPos(), pos2.getPos(), 0.12, color.getColorObj());
        }
    }
}
