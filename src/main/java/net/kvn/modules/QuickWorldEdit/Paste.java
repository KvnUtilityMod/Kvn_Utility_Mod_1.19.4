package net.kvn.modules.QuickWorldEdit;

import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.utils.PlayerUtil;

public class Paste extends Module {

    public Paste(){
        super("Paste" , "Pastes the copied area when activated", Category.QUICKWORLDEDIT);
    }

    @Override
    public void onEnable(){
        PlayerUtil.sendCommand("/paste");
    }
}
