package net.kvn.utils.math.rotation;

import net.kvn.utils.math.DoublePos;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class Projection {

    ArrayList<KvnRotation> rotations = new ArrayList<>();
    double[] movement = new double[3];

    public Projection() {
        //Initialize movement to zero
        movement[0] = 0;
        movement[1] = 0;
        movement[2] = 0;
    }

    public Projection(double x, double y, double z) {
        //Set the movement based on the parameters
        movement[0] = x;
        movement[1] = y;
        movement[2] = z;
    }

    public void Projection(ArrayList<KvnRotation> rotations, double x, double y, double z) {
        //Constructor with rotations and movement as parameters
        //Note: The method name is incorrect, should be "public Projection"
        this.rotations = rotations;
        movement[0] = x;
        movement[1] = y;
        movement[2] = z;
    }

    public DoublePos project(DoublePos point) {
        //Apply movement to the point
        point = move(point);

        //Apply each rotation in the list to the point
        for (KvnRotation rotation : rotations) {
            point = rotation.rotate(point);
        }

        //Return the final point
        return point;
    }

    public DoublePos projectBack(DoublePos point) {
        //Apply each rotation in reverse order to the point
        for (int i = rotations.size() - 1; i >= 0; i--) {
            point = rotations.get(i).rotateBack(point);
        }

        //Undo the movement applied in project()
        return moveBack(point);
    }

    public DoublePos move(DoublePos point) {
        //Move the point according to the movement vector
        return new DoublePos(point.getX() + movement[0], point.getY() + movement[1], point.getZ() + movement[2]);
    }

    public DoublePos moveBack(DoublePos point) {
        //Undo the movement applied in move()
        return new DoublePos(point.getX() - movement[0], point.getY() - movement[1], point.getZ() - movement[2]);
    }

    public DoublePos doLastRotation(DoublePos point) {
        if (rotations.size() > 0) {
            //Apply only the last rotation in the list to the point
            return rotations.get(rotations.size() - 1).rotate(point);
        } else {
            //If the list is empty, return the original point
            return point;
        }
    }

    public void clearProjection() {
        //Remove all rotations and reset movement to zero
        rotations.clear();
        movement[0] = 0;
        movement[1] = 0;
        movement[2] = 0;
    }

    public void addRotation(KvnRotation rotation) {
        //Add a rotation to the list
        rotations.add(rotation);
    }

    public void addRotation(RotationAxis axis, double rad) {
        //Create a new KvnRotation object and add it to the list
        rotations.add(new KvnRotation(axis, rad));
    }

    public void setMovement(double x, double y, double z) {
        //Set the movement vector based on the parameters
        movement[0] = x;
        movement[1] = y;
        movement[2] = z;
    }

    public void setXMovement(double x) {
        //Set only the x-component of the movement vector
        movement[0] = x;
    }

    public void setYMovement(double y) {
        //Set only the y-component of the movement vector
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

    //projection which puts pos1 at (0,0,0), pos2 at (x,0,0), and pos3 at (x,y,0)
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

    //same but for BlockPos objects
    public static Projection getProjection(BlockPos pos1, BlockPos pos2, BlockPos pos3) {
        return getProjection(new DoublePos(pos1), new DoublePos(pos2), new DoublePos(pos3));
    }

    //projection which puts pos1 at (0,0,0), pos2 at (x,0,0)
    public static Projection getProjectionToOriginAndAxisX(DoublePos pos1, DoublePos pos2) {
        Projection projection = new Projection();

        //set pos1 to 0,0,0
        projection.setMovement(-pos1.getX(), -pos1.getY(), -pos1.getZ());
        DoublePos dPos2 = projection.move(pos2);

        //rotate pos2 to the x-y plane
        projection.addRotation(RotationAxis.YAxis, Math.atan2(dPos2.getZ(), dPos2.getX()));

        return projection;
    }

}
