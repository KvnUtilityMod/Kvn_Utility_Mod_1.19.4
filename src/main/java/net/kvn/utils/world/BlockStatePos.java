package net.kvn.utils.world;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockStatePos {

    private BlockPos pos;
    private BlockState state;

    public BlockStatePos(BlockPos pos, BlockState state) {
        this.pos = pos;
        this.state = state;
    }

    public BlockStatePos() {}

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getState() {
        return state;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public void setState(BlockState state) {
        this.state = state;
    }
}
