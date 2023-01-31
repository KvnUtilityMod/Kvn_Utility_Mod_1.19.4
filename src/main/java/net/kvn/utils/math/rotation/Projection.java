package net.kvn.utils.math.rotation;

import net.kvn.utils.math.DoublePos;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class Projection {

    ArrayList<KvnRotation> rotations = new ArrayList<>();
    double[] movement = new double[3];

    public Projection() {
        movement[0] = 0;
        movement[1] = 0;
        movement[2] = 0;
    }

    public Projection(double x, double y, double z) {
        movement[0] = x;
        movement[1] = y;
        movement[2] = z;
    }

    public void Projection(ArrayList<KvnRotation> rotations, double x, double y, double z) {
        this.rotations = rotations;
        movement[0] = x;
        movement[1] = y;
        movement[2] = z;
    }

    public DoublePos project(DoublePos point) {
        point = move(point);
        for (KvnRotation rotation : rotations) {
            point = rotation.rotate(point);
        }

        return point;
    }
    public DoublePos projectBack(DoublePos point) {
        for (int i = rotations.size() - 1; i >= 0; i--) {
            point = rotations.get(i).rotateBack(point);
        }

        return moveBack(point);
    }

    public DoublePos move(DoublePos point) {
        return new DoublePos(point.getX() + movement[0], point.getY() + movement[1], point.getZ() + movement[2]);
    }
    public DoublePos moveBack(DoublePos point) {
        return new DoublePos(point.getX() - movement[0], point.getY() - movement[1], point.getZ() - movement[2]);
    }

    public DoublePos doLastRotation(DoublePos point) {
        if (rotations.size() > 0) {
            return rotations.get(rotations.size() - 1).rotate(point);
        } else {
            return point;
        }
    }

    public void clearProjection() {
        rotations.clear();
        movement[0] = 0;
        movement[1] = 0;
        movement[2] = 0;
    }

    public void addRotation(KvnRotation rotation) {
        rotations.add(rotation);
    }
    public void addRotation(RotationAxis axis, double rad) {
        rotations.add(new KvnRotation(axis, rad));
    }

    public void setMovement(double x, double y, double z) {
        movement[0] = x;
        movement[1] = y;
        movement[2] = z;
    }
    public void setXMovement(double x) {
        movement[0] = x;
    }
    public void setYMovement(double y) {
        movement[1] = y;
    }
    public void setZMovement(double z) {
        movement[2] = z;
    }

    public double[] getMovement() {
        return movement;
    }
    public double getXMovement() {
        return movement[0];
    }
    public double getYMovement() {
        return movement[1];
    }
    public double getZMovement() {
        return movement[2];
    }

    public static Projection getProjection(DoublePos pos1, DoublePos pos2, DoublePos pos3) {
        Projection projection = new Projection();

        //set pos1 to 0,0,0
        projection.setMovement(-pos1.getX(), -pos1.getY(), -pos1.getZ());
        DoublePos dPos2 = projection.move(pos2);
        DoublePos dPos3 = projection.move(pos3);

        //rotate pos2 to the x-y plane and rotate pos3 the same amount
        projection.addRotation(RotationAxis.YAxis, Math.atan2(dPos2.getZ(), dPos2.getX()));
        DoublePos pos2Rotated = projection.doLastRotation(dPos2);
        DoublePos pos3Rotated = projection.doLastRotation(dPos3);

        //rotate pos2 to the x-axis and rotate pos3 the same amount
        projection.addRotation(RotationAxis.ZAxis, -Math.atan2(pos2Rotated.getY(), pos2Rotated.getX()));
        DoublePos pos3Rotated2 = projection.doLastRotation(pos3Rotated);

        //rotate pos3 to the x-y plane
        projection.addRotation(RotationAxis.XAxis, -Math.atan2(pos3Rotated2.getZ(), pos3Rotated2.getY()));

        return projection;
    }

    //3blockPose
    public static Projection getProjection(BlockPos pos1, BlockPos pos2, BlockPos pos3) {
        return getProjection(new DoublePos(pos1), new DoublePos(pos2), new DoublePos(pos3));
    }
}
