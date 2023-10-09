package net.kvn.modules.QuickWorldEdit;

import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.utils.PlayerUtil;

public class ReplaceAir extends Module {

    public ReplaceAir() {
        super("ReplaceAir", "Replaces the selected area with air", Category.QUICKWORLDEDIT);
    }

    @Override
    public void onEnable() {
        PlayerUtil.sendCommand("/replace air");
    }

}
