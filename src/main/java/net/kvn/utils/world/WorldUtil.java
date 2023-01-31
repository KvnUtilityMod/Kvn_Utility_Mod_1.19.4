package net.kvn.utils.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

import static net.kvn.KvnUtilityMod.mc;

public class WorldUtil {

    public static ServerWorld getServerWorld() {
        try {
            for (ServerWorld serverWorld : mc.getServer().getWorlds()) {
                if (serverWorld.getDimension() == mc.world.getDimension()) {
                    return serverWorld;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setBlock(BlockPos pos, Block block) {
        getServerWorld().setBlockState(pos, block.getDefaultState());
    }

    public static void setBlock(BlockPos pos, BlockState state) {
        getServerWorld().setBlockState(pos, state);
    }

    public static BlockState getBlockState(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }
}
