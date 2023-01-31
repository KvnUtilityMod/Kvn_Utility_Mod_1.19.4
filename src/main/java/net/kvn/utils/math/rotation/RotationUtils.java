package net.kvn.utils.math.rotation;

import net.kvn.utils.math.DoublePos;

public class RotationUtils {

    //rotate point over x-axis
    public static double[] rotateX(double x, double y, double z, double rad) {
        double[] rotated = new double[3];
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        rotated[0] = x;
        rotated[1] = y * cos - z * sin;
        rotated[2] = y * sin + z * cos;
        return rotated;
    }
    public static double[] rotateX(double[] point, double rad) {
        return rotateX(point[0], point[1], point[2], rad);
    }
    public static DoublePos rotateX(DoublePos point, double rad) {
        double[] rotated = rotateX(point.getX(), point.getY(), point.getZ(), rad);
        return new DoublePos(rotated);
    }

    //rotate point over y-axis
    public static double[] rotateY(double x, double y, double z, double rad) {
        double[] rotated = new double[3];
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        rotated[0] = x * cos + z * sin;
        rotated[1] = y;
        rotated[2] = -x * sin + z * cos;
        return rotated;
    }
    public static double[] rotateY(double[] point, double rad) {
        return rotateY(point[0], point[1], point[2], rad);
    }
    public static DoublePos rotateY(DoublePos point, double rad) {
        double[] rotated = rotateY(point.getX(), point.getY(), point.getZ(), rad);
        return new DoublePos(rotated);
    }

    //rotate point over z-axis
    public static double[] rotateZ(double x, double y, double z, double rad) {
        double[] rotated = new double[3];
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        rotated[0] = x * cos - y * sin;
        rotated[1] = x * sin + y * cos;
        rotated[2] = z;
        return rotated;
    }
    public static double[] rotateZ(double[] point, double rad) {
        return rotateZ(point[0], point[1], point[2], rad);
    }
    public static DoublePos rotateZ(DoublePos point, double rad) {
        double[] rotated = rotateZ(point.getX(), point.getY(), point.getZ(), rad);
        return new DoublePos(rotated);
    }
}
