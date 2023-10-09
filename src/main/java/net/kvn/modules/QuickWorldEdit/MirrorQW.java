package net.kvn.modules.QuickWorldEdit;

import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.BooleanValue;
import net.kvn.settings.ModeValue;

import static net.kvn.utils.PlayerUtil.sendCommand;

public class MirrorQW extends Module {

    public ModeValue mode = addSetting(new ModeValue("Mode", "#sides", this, new String[]{"2", "4"}, 1));
    public BooleanValue pasteAir = addSetting(new BooleanValue("Paste Air", "also pastes air", this, false));

    public MirrorQW() {
        super("Mirror", "Copies the selected area when activated around your position", Category.QUICKWORLDEDIT);
    }

    @Override
    public void onEnable() {
        sendCommand("/copy");
        switch (mode.getMode()) {
            case 0:
                sendCommand("/rotate 180");
                paste();
                break;
            case 1:
                sendCommand("/rotate 90");
                paste();
                sendCommand("/rotate 90");
                paste();
                sendCommand("/rotate 90");
                paste();
                break;
        }
    }

    private void paste() {
        if (pasteAir.isTrue()) {
            sendCommand("/paste");
        } else {
            sendCommand("/paste -a");
        }
    }
}
