package net.kvn.modules.build;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.RenderBox;
import net.kvn.event.events.SettingUpdate;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.*;
import net.kvn.utils.math.DoublePos;
import net.kvn.utils.math.rotation.Projection;
import net.kvn.utils.render.BoxUtil;
import net.kvn.utils.render.RenderBoxUtil;
import net.kvn.utils.world.WorldUtil;
import net.minecraft.client.util.math.MatrixStack;

public class Mirror extends Module implements RenderBox, SettingUpdate {

    public ModeValue mode = addSetting(new ModeValue("Mode", "mode", this, new String[]{"line", "point"}, 0));
    public BlockPosValue pos = addSetting(new BlockPosValue("Pos", "The position", this, mode));
    public BlockPosValue pos1 = addSetting(new BlockPosValue("Pos1", "The first position", this, mode));
    public BlockPosValue pos2 = addSetting(new BlockPosValue("Pos2", "The second position", this, mode));
    public BlockPosValue mirrorPos = addSetting(new BlockPosValue("MirrorPos", "The position to miror", this));
    public BooleanValue mirrorBlocks = addSetting(new BooleanValue("MirrorBlocks", "Mirror blocks", this, false));
    public ColorValue color = addSetting(new ColorValue("Color", "The color of the box", this));

    public Mirror() {
        super("Miror", "Miror", Category.BUILD);
        pos1.setModeIndex(0);
        pos2.setModeIndex(0);
        pos.setModeIndex(1);
    }

    @Override
    public void renderBox(WorldRenderContext context, MatrixStack matrices) {
        if (!this.isActive()) return;

        RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(mirrorPos.getPos()), color.getColorObj());
        RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(getMirrorPos()), color.getColorObj());

        if (mode.getMode() == 0) {
            RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(pos1.getPos()), color.getColorObj());
            RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(pos2.getPos()), color.getColorObj());
            return;
        }

        if (mode.getMode() == 1) {
            RenderBoxUtil.draw3d(matrices, BoxUtil.createBox(pos.getPos()), color.getColorObj());
        }
    }

    @Override
    public void onSettingUpdate(Setting setting) {
        if (mirrorBlocks.isTrue()) {
            WorldUtil.setBlock(getMirrorPos().toBlockPos(), WorldUtil.getBlockState(mirrorPos.getPos()));
            mirrorBlocks.updateValue(false);
        }
    }

    public DoublePos getMirrorPos() {
        /*
        Projection projection = Projection.getProjection(pos1.getPos(), pos2.getPos(), mirrorPos.getPos());
        DoublePos projMirrorPos = projection.project(new DoublePos(mirrorPos.getPos()));
        DoublePos mirroredPos = new DoublePos(0 - projMirrorPos.getX(), 0 - projMirrorPos.getY(), 0 - projMirrorPos.getZ());
        return projection.projectBack(mirroredPos);

         */

        // 0 = line | only mirror over a plane
        if (mode.getMode() == 0) {
            Projection projection = Projection.getProjectionToOriginAndAxisX(new DoublePos(pos1.getPos()), new DoublePos(pos2.getPos()));
            DoublePos projMirrorPos = projection.project(new DoublePos(mirrorPos.getPos()));
            return projection.projectBack(new DoublePos(projMirrorPos.getX(), projMirrorPos.getY(), 0 - projMirrorPos.getZ()));
        }

        // 1 = point | mirror over a point
        return new DoublePos(
                mirrorPos.getPos().getX() + 2 * (pos.getPos().getX() - mirrorPos.getPos().getX()),
                mirrorPos.getPos().getY() + 2 * (pos.getPos().getY() - mirrorPos.getPos().getY()),
                mirrorPos.getPos().getZ() + 2 * (pos.getPos().getZ() - mirrorPos.getPos().getZ())
        );
    }
}
