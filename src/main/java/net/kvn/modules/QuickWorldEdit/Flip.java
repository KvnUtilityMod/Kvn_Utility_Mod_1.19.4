package net.kvn.modules.QuickWorldEdit;

import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.BooleanValue;

import static net.kvn.utils.PlayerUtil.sendCommand;

public class Flip extends Module {

    public BooleanValue pasteAir = addSetting(new BooleanValue("Paste Air", "also pastes air", this, false));

    public Flip() {
        super("Flipp", "Flips the selected area when activated", Category.QUICKWORLDEDIT);
    }

    @Override
    public void onEnable() {
        sendCommand("/copy");
        sendCommand("/flip");
        paste();
    }

    private void paste() {
        if (pasteAir.isTrue()) {
            sendCommand("/paste");
        } else {
            sendCommand("/paste -a");
        }
    }
}
