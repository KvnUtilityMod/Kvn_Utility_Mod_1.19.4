package net.kvn.modules.QuickWorldEdit;

import net.kvn.modules.Category;
import net.kvn.modules.Module;

import static net.kvn.utils.PlayerUtil.sendCommand;

public class Copy extends Module {

    public Copy(){
        super("Copy" , "Copies the selected area when activated", Category.QUICKWORLDEDIT);
    }

    @Override
    public void onEnable(){
        sendCommand("/copy");
    }
}