package net.kvn.utils.world;

import net.minecraft.util.math.BlockPos;

public class Distance {

    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
    }

    public static double getDistance(BlockPos pos1, BlockPos pos2) {
        return getDistance(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
    }
}
