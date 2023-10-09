package net.kvn.modules.client;

import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.BlockTypeValue;
import net.kvn.utils.world.WorldUtil;

import static net.kvn.KvnUtilityMod.mc;

public class FillMyPos extends Module {

    public BlockTypeValue blockType = addSetting(new BlockTypeValue("Pos1", "pos1", this, "stone"));

    public FillMyPos() {
        super("FillMyPos", "FillMyPos", Category.CLIENT);
    }

    @Override
    public void onEnable() {
        if (mc.player == null) return;
        WorldUtil.setBlock(mc.player.getBlockPos(), blockType.getBlockType2());
    }
}
