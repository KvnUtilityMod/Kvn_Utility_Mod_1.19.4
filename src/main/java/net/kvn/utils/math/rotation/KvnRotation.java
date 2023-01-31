package net.kvn.utils.math.rotation;

import net.kvn.utils.math.DoublePos;

public class KvnRotation {

    RotationAxis axis;
    double rad;

    public KvnRotation(RotationAxis axis, double rad) {
        this.axis = axis;
        this.rad = rad;
    }

    public RotationAxis getAxis() {
        return axis;
    }

    public double getRad() {
        return rad;
    }

    public DoublePos rotate(DoublePos point) {
        return switch (axis) {
            case XAxis -> RotationUtils.rotateX(point, rad);
            case YAxis -> RotationUtils.rotateY(point, rad);
            case ZAxis -> RotationUtils.rotateZ(point, rad);
        };
    }

    public DoublePos rotateBack(DoublePos point) {
        return switch (axis) {
            case XAxis -> RotationUtils.rotateX(point, -rad);
            case YAxis -> RotationUtils.rotateY(point, -rad);
            case ZAxis -> RotationUtils.rotateZ(point, -rad);
        };
    }
}
