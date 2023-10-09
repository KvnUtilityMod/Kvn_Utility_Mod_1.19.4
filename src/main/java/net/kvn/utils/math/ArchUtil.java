package net.kvn.utils.math;

import net.kvn.utils.math.rotation.Projection;

import java.util.ArrayList;

public class ArchUtil {

    public static ArrayList<DoublePos> getArchBlocks(DoublePos pos1, DoublePos pos2, DoublePos pos3, int blockAmount) {
        ArrayList<DoublePos> result = new ArrayList<>();

        // Get the right projection
        Projection projection = Projection.getProjection(pos1, pos3, pos2);
        DoublePos projPos1 = projection.project(pos1);
        DoublePos projPos2 = projection.project(pos2);
        DoublePos projPos3 = projection.project(pos3);

        double startAngle = 0;
        double endAngle = 359;
        double heightMultiplier = (projPos2.getY() + 0.000001) / projPos3.getX();
        double angleStep = (endAngle - startAngle) / blockAmount;

        for (double angle = startAngle; angle <= endAngle; angle += angleStep) {
            double x = angle / 360 * Math.abs(projPos3.getX() - projPos1.getX());
            double y = archHeight(Math.abs(projPos3.getX() - projPos1.getX()), Math.toRadians(angle)) * heightMultiplier;
            result.add(projection.projectBack(new DoublePos(x, y, 0)));
        }

        return result;
    }

    public static double archHeight(double r, double a) {
        double x = r + r * Math.sin(a / 2);
        return x - r;
    }
}
