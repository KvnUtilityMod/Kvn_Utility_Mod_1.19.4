package net.kvn.modules.QuickWorldEdit;

import net.kvn.modules.Category;
import net.kvn.modules.Module;

import static net.kvn.utils.PlayerUtil.sendCommand;

public class SetPos1 extends Module {

    public SetPos1(){
        super("SetPos1" , "Sets the position1 to your current position when activated", Category.QUICKWORLDEDIT);
    }

    @Override
    public void onEnable(){
        sendCommand("/pos1");
    }
}
