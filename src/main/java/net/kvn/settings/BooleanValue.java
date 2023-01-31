package net.kvn.settings;

import net.kvn.modules.Module;
import net.kvn.utils.file.FileReader;
import net.kvn.utils.file.FileWriter;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.booleanSettings;
import static net.kvn.KvnUtilityMod.customEventHandler;
import static net.kvn.utils.input.TextUtil.getIndex;

public class BooleanValue extends Setting {

    private boolean value;
    private String saveString;

    public BooleanValue(String name, String description, Module module, Setting settingOf, boolean value) {
        super(name, description, module, settingOf);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
        this.value = value;
    }

    public BooleanValue(String name, String description, Module module, boolean value) {
        super(name, description, module, null);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
        this.value = value;
    }

    public boolean isTrue() {
        return value;
    }

    private void setValue(boolean value) {
        this.value = value;
    }

    public void updateValue(boolean value) {
        this.setValue(value);
        this.setValueFile(value);
        customEventHandler.onSettingUpdate(this);
    }

    public void toggleValue() {
        this.updateValue(!this.isTrue());
    }

    //load boolean from file
    public ArrayList<String> loadBooleanFromFile(ArrayList<String> enabledBooleans){
        //get the index of the string
        int index = getIndex(this.saveString, enabledBooleans);
        if (index == -1) {
            enabledBooleans.add(this.saveString + " = " + this.isTrue());
        } else {
            this.value = enabledBooleans.get(index).contains("true");
        }
        return enabledBooleans;
    }

    public void setValueFile(boolean value){
        //read file
        ArrayList<String> enabledBooleans = FileReader.readLines(booleanSettings);

        //set value
        int index = getIndex(this.saveString, enabledBooleans);
        if (index == -1) {
            enabledBooleans.add(this.saveString + " = " + value);
        } else {
            enabledBooleans.set(index, this.saveString + " = " + value);
        }

        //write file
        FileWriter.writeLines(enabledBooleans, booleanSettings);
    }
}
