package net.kvn.utils.render;

import net.kvn.settings.Setting;

public class GuiUtil {

    public static int getLevelOfSetting(Setting setting){
        int level = 0;
        while (setting.getSettingOf() != null){
            level++;
            setting = setting.getSettingOf();
        }
        return level;
    }
}
