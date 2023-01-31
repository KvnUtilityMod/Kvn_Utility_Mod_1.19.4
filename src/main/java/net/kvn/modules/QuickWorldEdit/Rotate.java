package net.kvn.modules.QuickWorldEdit;

import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.IntegerValue;

import static net.kvn.utils.PlayerUtil.sendCommand;

public class Rotate extends Module {

    IntegerValue angle = addSetting(new IntegerValue("Angle", "", this, 90, 0, 360));

    public Rotate(){
        super("Rotate" , "Rotates the selected area", Category.QUICKWORLDEDIT);
    }

    @Override
    public void onEnable(){
        sendCommand("/rotate " + angle.getValue());
    }
}
