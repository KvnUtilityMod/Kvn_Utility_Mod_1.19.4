package net.kvn.utils.world.Structure;

import net.kvn.utils.world.BlockStatePos;
import net.kvn.utils.world.WorldUtil;
import net.minecraft.util.math.BlockPos;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class KvnStructure {

    boolean isStructureSaved = false;
    int xDiff;
    int yDiff;
    int zDiff;
    ArrayList<BlockStatePos> blockStatePosList = new ArrayList<>();

    public KvnStructure() {}

    public void saveStructure(int x1, int y1, int z1, int x2, int y2, int z2) {
        blockStatePosList = new ArrayList<>();
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);
        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);
        int maxZ = Math.max(z1, z2);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    blockStatePosList.add(new BlockStatePos(
                            new BlockPos(x - minX, y - minY, z - minZ),
                            WorldUtil.getBlockState(new BlockPos(x, y, z))));
                }
            }
        }

        isStructureSaved = true;
        xDiff = Math.abs(x1 - x2);
        zDiff = Math.abs(z1 - z2);
        yDiff = Math.abs(y1 - y2);

        int notAirBlocks = 0;
        for (BlockStatePos blockStatePos : blockStatePosList) {
            if (!blockStatePos.getState().isAir()) {
                notAirBlocks++;
            }
        }
        System.out.println("x: " + xDiff + " y: " + yDiff + " z: " + zDiff);
        System.out.println("Saved structure with " + notAirBlocks + " blocks");
    }
    public void saveStructure(BlockPos pos1, BlockPos pos2) {
        saveStructure(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
    }

    public int getxDiff() {
        return xDiff;
    }

    public int getyDiff() {
        return yDiff;
    }

    public int getzDiff() {
        return zDiff;
    }

    public ArrayList<BlockStatePos> getBlockStatePosList() {
        return blockStatePosList;
    }

    public boolean isStructureSaved() {
        return isStructureSaved;
    }
}
