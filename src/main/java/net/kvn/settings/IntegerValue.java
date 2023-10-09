package net.kvn.settings;

import net.kvn.modules.Module;
import net.kvn.utils.file.FileReader;
import net.kvn.utils.file.FileWriter;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.customEventHandler;
import static net.kvn.KvnUtilityMod.integerSettings;
import static net.kvn.utils.input.TextUtil.getIndex;

public class IntegerValue extends Setting {

    private int value;
    private int min;
    private int max;
    private String saveString;

    public IntegerValue(String name, String description, Module module, Setting settingOf, int value, int min, int max) {
        super(name, description, module, settingOf);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public IntegerValue(String name, String description, Module module, int value, int min, int max) {
        super(name, description, module, null);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public int getValue() {
        return value;
    }

    private void setValue(int value) {
        this.value = value;
    }

    public void updateValue(int value) {
        this.setValue(value);
        this.setValueFile(value);
    }

    //load boolean from file
    public ArrayList<String> loadIntegerFromFile(ArrayList<String> integerValues){
        //get the index of the string
        int index = getIndex(this.saveString, integerValues);
        if (index == -1) {
            integerValues.add(this.saveString + " = " + this.getValue());
        } else {
            try {
                this.value = Integer.parseInt(integerValues.get(index).split(" = ")[1]);
            } catch (NumberFormatException e) {
                integerValues.set(index, this.saveString + " = " + this.getValue());
            }
        }
        return integerValues;
    }

    public void setValueFile(int value){
        //read file
        ArrayList<String> integerValues = FileReader.readLines(integerSettings);
        //load booleans from file
        int index = getIndex(this.saveString, integerValues);
        if (index == -1) {
            integerValues.add(this.saveString + " = " + value);
        } else {
            integerValues.set(index, this.saveString + " = " + value);
        }
        //write file
        FileWriter.writeLines(integerValues, integerSettings);
        customEventHandler.onSettingUpdate(this);
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
