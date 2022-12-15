package net.kvn.utils.render;


import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class BoxUtil {

    public static Box createBox(BlockPos pos) {
        return new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    public static Box createBox(BlockPos pos1, BlockPos pos2) {
        return new Box(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
    }

    public static Box createBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new Box(x1, y1, z1, x2, y2, z2);
    }
}
