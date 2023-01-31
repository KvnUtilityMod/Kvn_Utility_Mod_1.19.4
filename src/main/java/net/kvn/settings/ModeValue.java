package net.kvn.settings;

import net.kvn.modules.Module;
import net.kvn.utils.file.FileReader;
import net.kvn.utils.file.FileWriter;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.modeSettings;
import static net.kvn.utils.input.TextUtil.getIndex;

public class ModeValue extends Setting {

    private String[] modes;
    private int mode;
    private String saveString;

    public ModeValue(String name, String description, Module module, Setting settingOf, String[] modes, int mode) {
        super(name, description, module, settingOf);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
        this.modes = modes;
        this.mode = mode;
    }

    public ModeValue(String name, String description, Module module, String[] modes, int mode) {
        super(name, description, module, null);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
        this.modes = modes;
        this.mode = mode;
    }

    @Override
    public boolean isSettingsOpened() {
        return false;
    }

    public ArrayList<String> loadModeFromFile(ArrayList<String> modeValues){
        //get the index of the string
        int index = getIndex(this.saveString, modeValues);
        if (index == -1) {
            modeValues.add(this.saveString + " = " + this.getMode());
        } else {
            try {
                this.mode = Integer.parseInt(modeValues.get(index).split(" = ")[1]);
            } catch (NumberFormatException e) {
                modeValues.set(index, this.saveString + " = " + this.getMode());
            }
        }
        return modeValues;
    }

    public void updateModeInFile(){
        //read file
        ArrayList<String> modeValues = FileReader.readLines(modeSettings);
        //load booleans from file
        int index = getIndex(this.saveString, modeValues);
        if (index == -1) {
            modeValues.add(this.saveString + " = " + mode);
        } else {
            modeValues.set(index, this.saveString + " = " + mode);
        }
        //write file
        FileWriter.writeLines(modeValues, modeSettings);
    }

    public String[] getModes() {
        return modes;
    }

    public void setModes(String[] modes) {
        this.modes = modes;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        updateModeInFile();
    }

    public String getModeName() {
        return modes[mode];
    }

    public void setModeName(String name) {
        for (int i = 0; i < modes.length; i++) {
            if (modes[i].equalsIgnoreCase(name)) {
                mode = i;
                return;
            }
        }
    }

    public void nextMode() {
        mode++;
        if (mode >= modes.length) {
            mode = 0;
        }
        updateModeInFile();
    }

    public void previousMode() {
        mode--;
        if (mode < 0) {
            mode = modes.length - 1;
        }
    }

    public void setModeByIndex(int index) {
        if (index >= 0 && index < modes.length) {
            mode = index;
        }
    }

    public void setModeByName(String name) {
        for (int i = 0; i < modes.length; i++) {
            if (modes[i].equalsIgnoreCase(name)) {
                mode = i;
                return;
            }
        }
    }

    public boolean is(String mode) {
        return modes[this.mode].equalsIgnoreCase(mode);
    }
}
