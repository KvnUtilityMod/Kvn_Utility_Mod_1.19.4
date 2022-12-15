package net.kvn.settings;

import net.kvn.KvnUtilityMod;
import net.kvn.modules.Module;
import net.kvn.utils.file.FileReader;
import net.kvn.utils.file.FileWriter;

import java.util.ArrayList;

import static net.kvn.utils.input.TextUtil.getIndex;

public class Visible extends Setting {

    private boolean isVisible;
    private String saveString;
    private Module module;

    public Visible(Module module, boolean isVisible) {
        super("Visible", "Determines or the setting is visible in the hud when activated", module, null);
        this.saveString = module.getName() + " " + "Visible";
        this.isVisible = isVisible;
        this.module = module;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        setValueFile(visible);
    }

    public void toggleVisible() {
        this.setVisible(!this.isVisible());
    }

    //load boolean from file
    public ArrayList<String> loadIsVisibleFromFile(ArrayList<String> visibleModules){
        //get the index of the string
        int index = getIndex(this.saveString, visibleModules);
        if (index == -1) {
            visibleModules.add(this.saveString + " = " + this.isVisible());
        } else {
            this.isVisible = visibleModules.get(index).contains("true");
        }
        return visibleModules;
    }

    public void setValueFile(boolean value){
        //read file
        ArrayList<String> visibleModules = FileReader.readLines(KvnUtilityMod.visibleModules);

        //set value
        int index = getIndex(this.saveString, visibleModules);
        if (index == -1) {
            visibleModules.add(this.saveString + " = " + value);
        } else {
            visibleModules.set(index, this.saveString + " = " + value);
        }

        //write file
        FileWriter.writeLines(visibleModules, KvnUtilityMod.visibleModules);
    }
}
