package net.kvn.modules.QuickWorldEdit;

import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.utils.PlayerUtil;

public class RedoQW extends Module {

        public RedoQW() {
            super("Redo", "Redoes the last action", Category.QUICKWORLDEDIT);
        }

        @Override
        public void onEnable() {
            PlayerUtil.sendCommand("/redo");
        }
}
