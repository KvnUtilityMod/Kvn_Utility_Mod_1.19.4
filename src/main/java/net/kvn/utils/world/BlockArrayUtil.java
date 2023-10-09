package net.kvn.utils.world;

import net.kvn.utils.math.DoublePos;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class BlockArrayUtil {

    public static boolean containsBlockPos(BlockPos pos, ArrayList<BlockPos> list) {
        for (BlockPos blockPos : list) {
            if (pos.getX() == blockPos.getX() && pos.getY() == blockPos.getY() && pos.getZ() == blockPos.getZ())
                return true;
        }
        return false;
    }

    public static ArrayList<BlockPos> getUniqueBlockPositions(ArrayList<DoublePos> list) {
        ArrayList<BlockPos> result = new ArrayList<>();
        for (DoublePos doublePos : list) {
            BlockPos blockPos = doublePos.toBlockPos();
            if (!containsBlockPos(blockPos, result))
                result.add(blockPos);
        }
        return result;
    }
}
