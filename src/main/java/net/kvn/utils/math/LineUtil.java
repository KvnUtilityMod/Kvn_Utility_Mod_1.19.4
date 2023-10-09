package net.kvn.utils.math;

import java.util.ArrayList;

public class LineUtil {
    public static ArrayList<DoublePos> getBlocksOnLine(DoublePos pos1, DoublePos pos2, int blockAmount) {
        ArrayList<DoublePos> result = new ArrayList<>();

        double x1 = pos1.getX();
        double y1 = pos1.getY();
        double z1 = pos1.getZ();

        double x2 = pos2.getX();
        double y2 = pos2.getY();
        double z2 = pos2.getZ();

        double dx = (x2 - x1) / blockAmount;
        double dy = (y2 - y1) / blockAmount;
        double dz = (z2 - z1) / blockAmount;

        for (int i = 0; i <= blockAmount; i++) {
            result.add(new DoublePos(x1 + dx * i, y1 + dy * i, z1 + dz * i));
        }

        return result;
    }
}
