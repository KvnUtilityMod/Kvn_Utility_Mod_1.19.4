package net.kvn.modules.QuickWorldEdit;

import net.kvn.modules.Category;
import net.kvn.modules.Module;

import static net.kvn.utils.PlayerUtil.sendCommand;

public class SetPos2 extends Module {

    public SetPos2(){
        super("SetPos2" , "Sets the position1 to your current position when activated", Category.QUICKWORLDEDIT);
    }

    @Override
    public void onEnable(){
        sendCommand("/pos2");
    }
}
