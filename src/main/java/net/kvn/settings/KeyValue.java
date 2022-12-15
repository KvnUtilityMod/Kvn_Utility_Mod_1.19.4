package net.kvn.settings;

import net.kvn.modules.Module;
import net.kvn.utils.file.FileReader;
import net.kvn.utils.file.FileWriter;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.moduleKeys;
import static net.kvn.utils.input.TextUtil.getIndex;

public class KeyValue extends Setting {

    private int key = 0;
    private Module module;
    private String saveString;

    public KeyValue(Module module) {
        super("key", "key to toggle module", module, null);
        this.module = module;
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + super.getName() + " = ";
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
        setKeyFile(key);
    }

    //file stuff
    public ArrayList<String> loadKeyFromFile(ArrayList<String> keySettings){
        int index = getIndex(this.saveString, keySettings);
        if (index == -1) {
            keySettings.add(saveString + this.key);
        } else {
            try {
                this.key = Integer.parseInt(keySettings.get(index).split("=")[1].trim());
            } catch (Exception e){
                keySettings.set(index, saveString + this.key);
            }
        }
        return keySettings;
    }

    public void setKeyFile(int key){
        //read file
        ArrayList<String> keySettings = FileReader.readLines(moduleKeys);
        //load key
        keySettings = this.loadKeyFromFile(keySettings);
        //set key
        this.key = key;
        //save key
        keySettings.set(getIndex(this.saveString, keySettings), this.saveString + this.key);
        //write file
        FileWriter.writeLines(keySettings, moduleKeys);
    }

}
