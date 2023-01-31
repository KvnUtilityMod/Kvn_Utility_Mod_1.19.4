package net.kvn.utils.math;

import net.kvn.utils.math.rotation.Projection;
import net.kvn.utils.render.BoxUtil;
import net.kvn.utils.render.ColorUtils;
import net.kvn.utils.render.RenderBoxUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.moduleManager;

public class CirlceShape {

    private BlockPos pos1 = new BlockPos(0, 0, 0);
    private BlockPos pos2 = new BlockPos(0, 0, 0);
    private BlockPos pos3 = new BlockPos(0, 0, 0);
    private DoublePos middlePoint = new DoublePos();
    private double radius;

    Projection projection = new Projection();

    public CirlceShape() {}

    public void calculateProjection() {
        projection = Projection.getProjection(new DoublePos(pos1), new DoublePos(pos2), new DoublePos(pos3));
    }

    public ArrayList<DoublePos> getBlocksOnCircle(MatrixStack m, int amount) {
        ArrayList<DoublePos> positions2D = new ArrayList<>();
        ArrayList<DoublePos> result = new ArrayList<>();

        if (moduleManager.circle.dimension.getMode() == 0) {
            DoublePos dPos1 = projection.project(new DoublePos(pos1));
            DoublePos dPos2 = projection.project(new DoublePos(pos2));
            DoublePos dPos3 = projection.project(new DoublePos(pos3));

            //get the center of the circle
            DoublePos centerProjectedPos = findCenterWithOrigin(dPos2, dPos3);
            radius = centerProjectedPos.getDistance(dPos1);
            middlePoint = projection.projectBack(centerProjectedPos);

            //add the blocks to the list
            double totalAngle = 2 * Math.PI;
            double startAngle = 0;
            if (moduleManager.circle.circleMode.getMode() == 1 || moduleManager.circle.circleMode.getMode() == 2) {
                double angleP1 = Math.atan2(dPos1.getY() - centerProjectedPos.getY(), dPos1.getX() - centerProjectedPos.getX());
                double angleP3 = Math.atan2(dPos3.getY() - centerProjectedPos.getY(), dPos3.getX() - centerProjectedPos.getX());
                double angleP13 = angleP3 - angleP1;
                if (moduleManager.circle.circleMode.getMode() == 1) {
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

            for (int i = 0; i < amount; i++) {
                double angle = totalAngle / amount;
                double x = Math.cos(angle * i + startAngle) * radius + centerProjectedPos.getX();
                double y = Math.sin(angle * i + startAngle) * radius + centerProjectedPos.getY();
                positions2D.add(new DoublePos(x, y, 0));
                result.add(projection.projectBack(positions2D.get(i)));
            }

            if (moduleManager.circle.debugBlocks.isTrue() && m != null) {
                RenderBoxUtil.draw3d(m, BoxUtil.createBox(centerProjectedPos), ColorUtils.getFullAlpha(moduleManager.circle.debugColor.getDarkerColor(50)));
                RenderBoxUtil.draw3d(m, BoxUtil.createBox(dPos1), ColorUtils.getFullAlpha(moduleManager.circle.debugColor.getDarkerColor(50)));
                RenderBoxUtil.draw3d(m, BoxUtil.createBox(dPos2), ColorUtils.getFullAlpha(moduleManager.circle.debugColor.getDarkerColor(50)));
                RenderBoxUtil.draw3d(m, BoxUtil.createBox(dPos3), ColorUtils.getFullAlpha(moduleManager.circle.debugColor.getDarkerColor(50)));

                for (DoublePos pos : positions2D) {
                    RenderBoxUtil.draw3d(m, BoxUtil.createBox(pos), ColorUtils.getFullAlpha(moduleManager.circle.debugColor.getColorObj()));
                }
            }
        } else {
            BlockPos middle = moduleManager.circle.middlePoint2D.getPos();
            int radius = moduleManager.circle.radius2D.getValue();
            double startAngle = 0;

            if (moduleManager.circle.selectMode2D.getMode() == 1) {
                BlockPos posOnCircle = moduleManager.circle.posOnCircle2D.getPos();
                switch (moduleManager.circle.plane2D.getMode()) {
                    case 0:
                        //xz
                        radius = (int) Math.sqrt(Math.pow(posOnCircle.getX() - middle.getX(), 2) + Math.pow(posOnCircle.getZ() - middle.getZ(), 2));
                        startAngle = Math.atan2(posOnCircle.getZ() - middle.getZ(), posOnCircle.getX() - middle.getX());
                        break;
                    case 1:
                        //xy
                        radius = (int) Math.sqrt(Math.pow(posOnCircle.getX() - middle.getX(), 2) + Math.pow(posOnCircle.getY() - middle.getY(), 2));
                        startAngle = Math.atan2(posOnCircle.getY() - middle.getY(), posOnCircle.getX() - middle.getX());
                        break;
                    case 2:
                        //yz
                        radius = (int) Math.sqrt(Math.pow(posOnCircle.getY() - middle.getY(), 2) + Math.pow(posOnCircle.getZ() - middle.getZ(), 2));
                        startAngle = Math.atan2(posOnCircle.getZ() - middle.getZ(), posOnCircle.getY() - middle.getY());
                        break;
                }
            }

            switch (moduleManager.circle.plane2D.getMode()) {
                case 0:
                    //xz
                    for (int i = 0; i < amount; i++) {
                        double angle = 2 * Math.PI / amount;
                        double x = Math.cos(angle * i + startAngle) * radius + middle.getX();
                        double z = Math.sin(angle * i + startAngle) * radius + middle.getZ();
                        result.add(new DoublePos(x, middle.getY(), z));
                    }
                    break;
                case 1:
                    //xy
                    for (int i = 0; i < amount; i++) {
                        double angle = 2 * Math.PI / amount;
                        double x = Math.cos(angle * i + startAngle) * radius + middle.getX();
                        double y = Math.sin(angle * i + startAngle) * radius + middle.getY();
                        result.add(new DoublePos(x, y, middle.getZ()));
                    }
                    break;
                case 2:
                    //yz
                    for (int i = 0; i < amount; i++) {
                        double angle = 2 * Math.PI / amount;
                        double y = Math.cos(angle * i + startAngle) * radius + middle.getY();
                        double z = Math.sin(angle * i + startAngle) * radius + middle.getZ();
                        result.add(new DoublePos(middle.getX(), y, z));
                    }
                    break;
            }
        }
        return result;
    }

    private DoublePos findCenterWithOrigin(DoublePos pos2, DoublePos pos3) {
        double xCenter = pos2.getX() / 2;
        double slopeToCircleMid = (pos3.getX()) / ( - pos3.getY());
        double yCenter = (xCenter - (pos3.getX() / 2)) * slopeToCircleMid + pos3.getY() / 2;
        return new DoublePos(xCenter, yCenter, 0);
    }

    public boolean arePositionsSet() {
        return pos1 != null && pos2 != null && pos3 != null;
    }

    public DoublePos getMiddlePoint() {
        return middlePoint;
    }

    public BlockPos getPos1() {
        return pos1;
    }
    public BlockPos getPos2() {
        return pos2;
    }
    public BlockPos getPos3() {
        return pos3;
    }
    public void setPos1(BlockPos pos1) {
        this.pos1 = pos1;
        this.calculateProjection();
    }
    public void setPos2(BlockPos pos2) {
        this.pos2 = pos2;
        this.calculateProjection();
    }
    public void setPos3(BlockPos pos3) {
        this.pos3 = pos3;
        this.calculateProjection();
    }
}
