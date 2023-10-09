package net.kvn.utils.world;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;

public class BlockUtil {

    public static ArrayList<Block> getAllBlocks() {
        Registry<Block> r = Registries.BLOCK;
        ArrayList<Block> blocks = new ArrayList<>();
        for (Block b : r) {
            blocks.add(b);
        }

        return blocks;
    }

    public static Block getBlockFromName(String name) {
        Registry<Block> r = Registries.BLOCK;
        for (Block b : r) {
            if (b.getTranslationKey().endsWith("." + name)) {
                return b;
            }
        }
        return null;
    }
}
