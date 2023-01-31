package net.kvn.modules.QuickWorldEdit;

import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.BooleanValue;
import net.kvn.settings.IntegerValue;

import static net.kvn.utils.PlayerUtil.sendCommand;

public class Stack extends Module {

    IntegerValue stackAmount = addSetting(new IntegerValue("StackAmount", "Copies and stacks the selected area", this, 1, 1, 100));
    BooleanValue copy = addSetting(new BooleanValue("Copy", "Copies the selected area", this, true));

    public Stack() {
        super("Stack", "Copies and stacks the selected area", Category.QUICKWORLDEDIT);
    }

    @Override
    public void onEnable() {
        if (copy.isTrue()) {
            sendCommand("/copy");
        }
        sendCommand("/stack " + stackAmount.getValue());
    }
}
