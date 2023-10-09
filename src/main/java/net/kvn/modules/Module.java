package net.kvn.modules;

import net.kvn.settings.KeyValue;
import net.kvn.settings.ModeValue;
import net.kvn.settings.Setting;
import net.kvn.settings.Visible;
import net.kvn.utils.file.FileReader;
import net.kvn.utils.file.FileWriter;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.enabledModules;

public abstract class Module {

    private String name;
    private String description;
    private Category category;
    private boolean active = false;
    private Visible visible;
    private KeyValue keySetting;
    ArrayList<Setting> settings = new ArrayList<>();


    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.visible = new Visible(this, true);
        this.keySetting = new KeyValue(this);
        settings.add(this.getVisible());
        settings.add(this.getKeySetting());
    }

    public <S extends Setting> S addSetting(S setting){
        settings.add(setting);
        return setting;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isActive() {
        return this.active;
    }
    private void setActive(boolean active) {
        this.active = active;
    }
    public void updateActive(boolean active) {
        if (active == this.isActive())
            return;
        if (active) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }
    public void toggle() {

        if (this.isActive())
            onDisable();
        else
            onEnable();
    }

    public void onEnable() {
        this.active = true;
        this.setIsEnabledFile(true);
//        System.out.println("onEnable() called for " + this.name + "!");
    }
    public void onDisable() {
        this.active = false;
        setIsEnabledFile(false);
//        System.out.println("onDisable() called for " + this.name + "!");
    }

    public int getKey() {
        return this.getKeySetting().getKey();
    }
    public void setKey(int key) {
        this.getKeySetting().setKey(key);
    }

    public ArrayList<Setting> getSettings(){
        return settings;
    }
    public ArrayList<Setting> getSettingsNoSettingOf(){
        ArrayList<Setting> settingsNoSettingOf = new ArrayList<>();
        settings.forEach(setting -> {
            if (setting.getSettingOf() == null) settingsNoSettingOf.add(setting);
        });
        return settingsNoSettingOf;
    }
    public ArrayList<Setting> getSettingsOf(Setting settingOf){
        ArrayList<Setting> settingsOf = new ArrayList<>();
        settings.forEach(setting -> {
            if (setting.getSettingOf() == settingOf) settingsOf.add(setting);
        });
        return settingsOf;
    }
    public ArrayList<Setting> getSettingsOf(ModeValue modeValue){
        ArrayList<Setting> settingsOf = new ArrayList<>();
        settings.forEach(setting -> {
            if (setting.getSettingOf() == modeValue && setting.getModeIndex() == modeValue.getMode()) settingsOf.add(setting);
        });
        return settingsOf;
    }
    public ArrayList<Setting> getSettingsByType(Class<? extends Setting> type){
        ArrayList<Setting> settingsByType = new ArrayList<>();
        settings.forEach(setting -> {
            if (setting.getClass() == type) settingsByType.add(setting);
        });
        return settingsByType;
    }

    public ArrayList<String> loadIsEnabledFromFile(ArrayList<String> enabledModules, ArrayList<String> initialisedModules){
        if (initialisedModules.contains(name)){
            this.setActive(enabledModules.contains(name));
        } else {
            if (this.isActive()){
                enabledModules.add(name);
            }
            initialisedModules.add(name);
        }
        return enabledModules;
    }
    public void setIsEnabledFile(boolean value){
        ArrayList<String> enabledModulesInFile = FileReader.readLines(enabledModules);
        if (value && !enabledModulesInFile.contains(name)){
            enabledModulesInFile.add(name);
        } else if (!value && enabledModulesInFile.contains(name)){
            enabledModulesInFile.remove(name);
        }
        FileWriter.writeLines(enabledModulesInFile, enabledModules);
    }

    public Visible getVisible() {
        return this.visible;
    }

    public KeyValue getKeySetting() {
        return this.keySetting;
    }
}
