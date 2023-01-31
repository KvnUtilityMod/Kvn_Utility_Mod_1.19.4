package net.kvn.settings;

import net.kvn.modules.Module;
import net.kvn.utils.file.FileReader;
import net.kvn.utils.file.FileWriter;
import net.kvn.utils.world.BlockUtil;
import net.minecraft.block.Block;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.blockTypeSettings;
import static net.kvn.utils.input.TextUtil.getIndex;

public class BlockTypeValue extends Setting {

    private String blockType;
    private String saveString;

    public BlockTypeValue(String name, String description, Module module, Setting settingOf, String blockType) {
        super(name, description, module, settingOf);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
        this.blockType = blockType;
    }

    public BlockTypeValue(String name, String description, Module module, String blockType) {
        super(name, description, module, null);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
        this.blockType = blockType;
    }

    public String getBlockType() {
        return blockType;
    }

    public Block getBlockType2() {
        return BlockUtil.getBlockFromName(blockType);
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
        setBlockTypeFile(blockType);
    }

    public ArrayList<String> loadBlockTypeFromFile(ArrayList<String> blockTypeValues){
        //get the index of the string
        int index = getIndex(this.saveString, blockTypeValues);
        if (index == -1) {
            blockTypeValues.add(this.saveString + " = " + this.getBlockType());
        } else {
            try {
                this.blockType = blockTypeValues.get(index).split(" = ")[1];
            } catch (Exception e) {
                blockTypeValues.set(index, this.saveString + " = " + this.getBlockType());
            }
        }
        return blockTypeValues;
    }

    public void setBlockTypeFile(String blockType){
        //read file
        ArrayList<String> blockTypeValues = FileReader.readLines(blockTypeSettings);
        //load booleans from file
        int index = getIndex(this.saveString, blockTypeValues);
        if (index == -1) {
            blockTypeValues.add(this.saveString + " = " + blockType);
        } else {
            blockTypeValues.set(index, this.saveString + " = " + blockType);
        }
        //write file
        FileWriter.writeLines(blockTypeValues, blockTypeSettings);
    }
}
