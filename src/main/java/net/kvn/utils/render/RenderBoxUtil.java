package net.kvn.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;
//import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

import static net.kvn.KvnUtilityMod.mc;

public class RenderBoxUtil {

    public static void DrawBoxOutline(MatrixStack stack, BlockPos pos1, BlockPos pos2, double lineWith, Color color) {
        double xMin = Math.min(pos1.getX(), pos2.getX());
        double yMin = Math.min(pos1.getY(), pos2.getY());
        double zMin = Math.min(pos1.getZ(), pos2.getZ());
        double xMax = Math.max(pos1.getX(), pos2.getX());
        double yMax = Math.max(pos1.getY(), pos2.getY());
        double zMax = Math.max(pos1.getZ(), pos2.getZ());

        //4 top lines
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMin, yMax + (1 - lineWith), zMin, xMax + 1, yMax + 1, zMin + lineWith), color);
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMin, yMax + (1 - lineWith), zMin, xMin + lineWith, yMax + 1, zMax + 1), color);
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMin, yMax + (1 - lineWith), zMax + (1 - lineWith), xMax + 1, yMax + 1, zMax + 1), color);
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMax + (1 - lineWith), yMax + (1 - lineWith), zMin, xMax + 1, yMax + 1, zMax + 1), color);

        //4 side lines
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMin, yMin, zMin, xMin + lineWith, yMax + 1, zMin + lineWith), color);
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMin, yMin, zMax + (1 - lineWith), xMin + lineWith, yMax + 1, zMax + 1), color);
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMax + (1 - lineWith), yMin, zMax + (1 - lineWith), xMax + 1, yMax + 1, zMax + 1), color);
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMax + (1 - lineWith), yMin, zMin, xMax + 1, yMax + 1, zMin + lineWith), color);

        //4 bottom lines
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMin, yMin, zMin, xMax + 1, yMin + lineWith, zMin + lineWith), color);
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMin, yMin, zMin, xMin + lineWith, yMin + lineWith, zMax + 1), color);
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMin, yMin, zMax + (1 - lineWith), xMax + 1, yMin + lineWith, zMax + 1), color);
        RenderBoxUtil.draw3d(stack, BoxUtil.createBox(xMax + (1 - lineWith), yMin, zMin, xMax + 1, yMin + lineWith, zMax + 1), color);
    }

    public static void draw3d(MatrixStack stack, Box box, Color color) {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        Matrix4f matrix = stack.peek().getPositionMatrix();
        stack.push();

        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();

        Vec3d vec = new Vec3d(box.minX, box.minY, box.minZ).subtract(mc.gameRenderer.getCamera().getPos());

        float minX = (float)vec.x;
        float minY = (float)vec.y;
        float minZ = (float)vec.z;
        float maxX = (float) (vec.x + box.getXLength());
        float maxY = (float) (vec.y + box.getYLength());
        float maxZ = (float) (vec.z + box.getZLength());

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlpha();

        // top
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();

        // bottom
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();

        // back
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();

        // front
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();

        // left
        buffer.vertex(matrix, minX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, minX, maxY, minZ).color(r, g, b, a).next();

        // right
        buffer.vertex(matrix, maxX, minY, minZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, minY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, maxZ).color(r, g, b, a).next();
        buffer.vertex(matrix, maxX, maxY, minZ).color(r, g, b, a).next();

        tessellator.draw();
        stack.pop();
    }
}
