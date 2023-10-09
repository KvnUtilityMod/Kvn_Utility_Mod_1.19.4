package net.kvn.utils.math;

import net.kvn.utils.math.rotation.Projection;

import java.util.ArrayList;

public class CircleUtil {

    //get blocks on circle 2d (planeMode 0 = xz, planeMode 1 = xy, planeMode 2 = yz)
    public static ArrayList<DoublePos> getBlocksOn2DCircle(DoublePos middlePoint, DoublePos pointOnCircle, double radius, int planeMode, int amountOfBlocks) {

        ArrayList<DoublePos> result = new ArrayList<>();
        double startAngle = 0;

        if (pointOnCircle != null) {
            switch (planeMode) {
                case 0:
                    //xz
                    radius = (int) Math.sqrt(Math.pow(pointOnCircle.getX() - middlePoint.getX(), 2) + Math.pow(pointOnCircle.getZ() - middlePoint.getZ(), 2));
                    startAngle = Math.atan2(pointOnCircle.getZ() - middlePoint.getZ(), pointOnCircle.getX() - middlePoint.getX());
                    break;
                case 1:
                    //xy
                    radius = (int) Math.sqrt(Math.pow(pointOnCircle.getX() - middlePoint.getX(), 2) + Math.pow(pointOnCircle.getY() - middlePoint.getY(), 2));
                    startAngle = Math.atan2(pointOnCircle.getY() - middlePoint.getY(), pointOnCircle.getX() - middlePoint.getX());
                    break;
                case 2:
                    //yz
                    radius = (int) Math.sqrt(Math.pow(pointOnCircle.getY() - middlePoint.getY(), 2) + Math.pow(pointOnCircle.getZ() - middlePoint.getZ(), 2));
                    startAngle = Math.atan2(pointOnCircle.getZ() - middlePoint.getZ(), pointOnCircle.getY() - middlePoint.getY());
                    break;
            }
        }

        switch (planeMode) {
            case 0:
                //xz
                for (int i = 0; i < amountOfBlocks; i++) {
                    double angle = 2 * Math.PI / amountOfBlocks;
                    double x = Math.cos(angle * i + startAngle) * radius + middlePoint.getX();
                    double z = Math.sin(angle * i + startAngle) * radius + middlePoint.getZ();
                    result.add(new DoublePos(x, middlePoint.getY(), z));
                }
                break;
            case 1:
                //xy
                for (int i = 0; i < amountOfBlocks; i++) {
                    double angle = 2 * Math.PI / amountOfBlocks;
                    double x = Math.cos(angle * i + startAngle) * radius + middlePoint.getX();
                    double y = Math.sin(angle * i + startAngle) * radius + middlePoint.getY();
                    result.add(new DoublePos(x, y, middlePoint.getZ()));
                }
                break;
            case 2:
                //yz
                for (int i = 0; i < amountOfBlocks; i++) {
                    double angle = 2 * Math.PI / amountOfBlocks;
                    double y = Math.cos(angle * i + startAngle) * radius + middlePoint.getY();
                    double z = Math.sin(angle * i + startAngle) * radius + middlePoint.getZ();
                    result.add(new DoublePos(middlePoint.getX(), y, z));
                }
                break;
        }
        return result;
    }

    //make this a public static method (circleMode 1 = full, circleMode 2 = short, circleMode 3 = long)
    public static ArrayList<DoublePos> getBlocksOn3DCircle(DoublePos pos1, DoublePos pos2, DoublePos pos3, int circleMode, int blockCount) {
        Projection projection = Projection.getProjection(pos1, pos2, pos3);
        ArrayList<DoublePos> positions2D = new ArrayList<>();
        ArrayList<DoublePos> result = new ArrayList<>();

        //project the positions to 2d space in the xz plane
        DoublePos dPos1 = projection.project(pos1);
        DoublePos dPos2 = projection.project(pos2);
        DoublePos dPos3 = projection.project(pos3);

        //get the center of the circle
        DoublePos centerProjectedPos = findCenterWithOrigin(dPos2, dPos3);
        double radius = centerProjectedPos.getDistance(dPos1);
        DoublePos middlePoint = projection.projectBack(centerProjectedPos);

        //add the blocks to the list
        double totalAngle = 2 * Math.PI;
        double startAngle = 0;

        if (circleMode == 1 || circleMode == 2) {
            double angleP1 = Math.atan2(dPos1.getY() - centerProjectedPos.getY(), dPos1.getX() - centerProjectedPos.getX());
            double angleP3 = Math.atan2(dPos3.getY() - centerProjectedPos.getY(), dPos3.getX() - centerProjectedPos.getX());
            double angleP13 = angleP3 - angleP1;
            if (circleMode == 1) {
                if (Math.abs(angleP13) < Math.PI) {
                    totalAngle = angleP13;
                    startAngle = angleP1;
                } else {
                    totalAngle = 2 * Math.PI - Math.abs(angleP13);
                    startAngle = angleP3;
                }
            } else {
                if (Math.abs(angleP13) < Math.PI) {
                    totalAngle = 2 * Math.PI - Math.abs(angleP13);
                    startAngle = angleP3;
                } else {
                    totalAngle = angleP13;
                    startAngle = angleP1;
                }
            }
        }

        //make sure blockCount is 2 or higher
        if (blockCount < 2) {
            blockCount = 2;
        }

        for (int i = 0; i < blockCount; i++) {
            double angle = totalAngle / (blockCount - 1);
            double x = Math.cos(angle * i + startAngle) * radius + centerProjectedPos.getX();
            double y = Math.sin(angle * i + startAngle) * radius + centerProjectedPos.getY();
            positions2D.add(new DoublePos(x, y, 0));
            result.add(projection.projectBack(positions2D.get(i)));
        }

        return result;
    }

    public static DoublePos findCenterWithOrigin(DoublePos pos2, DoublePos pos3) {
        double xCenter = pos2.getX() / 2;
        double slopeToCircleMid = (pos3.getX()) / ( - pos3.getY());
        double yCenter = (xCenter - (pos3.getX() / 2)) * slopeToCircleMid + pos3.getY() / 2;
        return new DoublePos(xCenter, yCenter, 0);
    }

    public static DoublePos getMiddlePoint3DCircle(DoublePos pos1, DoublePos pos2, DoublePos pos3) {
        Projection projection = Projection.getProjection(pos1, pos2, pos3);
        DoublePos dPos2 = projection.project(pos2);
        DoublePos dPos3 = projection.project(pos3);
        DoublePos centerProjectedPos = findCenterWithOrigin(dPos2, dPos3);
        return projection.projectBack(centerProjectedPos);
    }
}
