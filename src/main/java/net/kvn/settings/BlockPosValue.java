package net.kvn.settings;

import net.kvn.modules.Module;
import net.kvn.utils.file.FileReader;
import net.kvn.utils.file.FileWriter;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.blockPosSettings;
import static net.kvn.utils.input.TextUtil.getIndex;

public class BlockPosValue extends Setting {

    private int x = 0;
    private int y = 0;
    private int z = 0;
    private String saveString;

    public BlockPosValue(String name, String description, Module module, Setting settingOf, int x, int y, int z) {
        super(name, description, module, settingOf);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public BlockPosValue(String name, String description, Module module, int x, int y, int z) {
        super(name, description, module, null);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public BlockPosValue(String name, String description, Module module, Setting settingOf) {
        super(name, description, module, settingOf);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
    }
    public BlockPosValue(String name, String description, Module module) {
        super(name, description, module, null);
        this.saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
    }

    public ArrayList<String> loadBlockPosFromFile(ArrayList<String> blockPos){
        int index = getIndex(this.saveString, blockPos);
        if (index == -1) {
            blockPos.add(saveString + " [" + this.x + "," + this.y + "," + this.z + "]");
        } else {
            try {
                String[] split = blockPos.get(index).split("\\[")[1].split("\\]")[0].split(",");
                this.x = Integer.parseInt(split[0]);
                this.y = Integer.parseInt(split[1]);
                this.z = Integer.parseInt(split[2]);
            } catch (Exception e){
                blockPos.set(index, saveString + " [" + this.x + "," + this.y + "," + this.z + "]");
            }
        }
        return blockPos;
    }

    public void setBlockPosFile(int x, int y, int z){
        //read file
        ArrayList<String> blockPos = FileReader.readLines(blockPosSettings);

        int i = getIndex(saveString, blockPos);
        if (i == -1) {
            blockPos.add(saveString + " [" + x + "," + y + "," + z + "]");
        } else {
            blockPos.set(i, saveString + " [" + x + "," + y + "," + z + "]");
        }
        //write file
        FileWriter.writeLines(blockPos, blockPosSettings);
    }

    public net.minecraft.util.math.BlockPos getPos() {
        return new net.minecraft.util.math.BlockPos(x, y, z);
    }

    public void setPos(net.minecraft.util.math.BlockPos pos) {
        setBlockPosFile(pos.getX(), pos.getY(), pos.getZ());
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public void setPos(int x, int y, int z) {
        setBlockPosFile(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        setBlockPosFile(x, this.y, this.z);
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        setBlockPosFile(this.x, y, this.z);
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        setBlockPosFile(this.x, this.y, z);
        this.z = z;
    }
}
