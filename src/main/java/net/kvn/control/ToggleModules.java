package net.kvn.control;

import net.kvn.event.events.KeyPress;
import net.kvn.modules.Module;
import net.kvn.modules.ModuleManager;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;

public class ToggleModules implements KeyPress {

    public ToggleModules() {}

    @Override
    public void onKeyPressed(int key) {
        if (mc.currentScreen != null) return;

        for (Module m : moduleManager.getModules()) {
            if (m.getKey() == key) {
                m.toggle();
            }
        }
    }
}
