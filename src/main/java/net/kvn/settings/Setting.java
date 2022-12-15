package net.kvn.settings;

import net.kvn.modules.Module;

import java.util.ArrayList;

public abstract class Setting {

    private String name;
    private String description;
    private Module module;
    private Setting settingOf;
    private boolean settingsOpened = false;

    public Setting(String name, String description, Module module, Setting settingOf) {
        this.name = name;
        this.description = description;
        this.module = module;
        this.settingOf = settingOf;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Module getModule() {
        return module;
    }

    public Setting getSettingOf() {
        return settingOf;
    }

    public String getSettingOfString() {
        if (this.getSettingOf() == null) {
            return "null";
        } else {
            return getSettingOf().getName();
        }
    }

    public boolean isSettingsOpened() {
        return settingsOpened;
    }
    public void setSettingsOpened(boolean settingsOpened) {
        this.settingsOpened = settingsOpened;
    }
    public void toggleSettingsOpened() {
        this.settingsOpened = !this.settingsOpened;
    }

}
