package net.kvn.utils.render;

import net.kvn.gui.MainGui.SettingsGui;
import net.kvn.gui.MainGui.settingbuttones.*;
import net.kvn.modules.Module;
import net.kvn.settings.*;

import java.util.ArrayList;

public class GuiUtil {

    public static int getLevelOfSetting(Setting setting){
        int level = 0;
        while (setting.getSettingOf() != null){
            level++;
            setting = setting.getSettingOf();
        }
        return level;
    }

    public static SettingGui getSettingGui(Module m, Setting s, int x, int y, int with, int height) {
        if (s instanceof Visible) return new VisibleGui(m, (Visible) s, x, y, with, height);
        else if (s instanceof KeyValue) return new KeyValueGui(m, (KeyValue) s, x, y, with, height);
        else if (s instanceof BooleanValue) return new BooleanGui(m, (BooleanValue) s, x, y, with, height);
        else if (s instanceof IntegerValue) return new IntegerValueGui(m, (IntegerValue) s, x, y, with, height);
        else if (s instanceof BlockPosValue) return new BlockPosGui(m, (BlockPosValue) s, x, y, with, height);
        else if (s instanceof ColorValue) return new ColorValueGui(m, (ColorValue) s, x, y, with, height);
        else if (s instanceof ModeValue) return new ModeValueGui(m, (ModeValue) s, x, y, with, height);
        else if (s instanceof BlockTypeValue) return new BlockTypeValueGui(m, (BlockTypeValue) s, x, y, with, height);
        return null;
    }
    public static ArrayList<SettingGui> getSettingGuis(Module m, ArrayList<Setting> settings, int x, int y, int with, int height) {
        ArrayList<SettingGui> settingGuis = new ArrayList<>();
        for (Setting s : settings) {
            SettingGui gui = getSettingGui(m, s, x, y, with, height);
            if (gui != null) {
                settingGuis.add(gui);
                y += gui.getTotalHeight();
            }
        }
        return settingGuis;
    }
}
