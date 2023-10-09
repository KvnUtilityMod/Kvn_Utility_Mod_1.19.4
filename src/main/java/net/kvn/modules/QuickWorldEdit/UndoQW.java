package net.kvn.modules.QuickWorldEdit;

import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.utils.PlayerUtil;

public class UndoQW extends Module {

        public UndoQW() {
            super("Undo", "Undoes the last action", Category.QUICKWORLDEDIT);
        }

        @Override
        public void onEnable() {
            PlayerUtil.sendCommand("/undo");
        }
}
