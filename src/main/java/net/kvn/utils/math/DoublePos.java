package net.kvn.utils.math;

import net.minecraft.util.math.BlockPos;

public class DoublePos {

    private double x;
    private double y;
    private double z;

    public DoublePos() {
        this(0, 0, 0);
    }

    public DoublePos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public DoublePos(double[] pos) {
        this.x = pos[0];
        this.y = pos[1];
        this.z = pos[2];
    }

    public DoublePos(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public double getDistance(double x, double y, double z) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2) + Math.pow(this.z - z, 2));
    }

    public double getDistance(DoublePos pos) {
        return getDistance(pos.getX(), pos.getY(), pos.getZ());
    }

    public double getDistance(BlockPos pos) {
        return getDistance(pos.getX(), pos.getY(), pos.getZ());
    }

    public BlockPos toBlockPos() {
        return new BlockPos(Math.round(x), Math.round(y), Math.round(z));
    }

    public double[] toArray() {
        return new double[] {x, y, z};
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setPos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setPos(DoublePos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public void setPos(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "DoublePos [" + x + ", " + y + ", " + z + "]";
    }
}