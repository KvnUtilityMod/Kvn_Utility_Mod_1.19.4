package net.kvn.settings;

import net.kvn.modules.Module;

public class SettingPlaceHolder extends Setting {

    public SettingPlaceHolder(String name, String description, Module module, Setting settingOf) {
        super(name, description, module, settingOf);
    }

    public SettingPlaceHolder(String name, String description, Module module) {
        super(name, description, module, null);
    }
}
