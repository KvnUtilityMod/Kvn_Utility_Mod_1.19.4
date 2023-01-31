package net.kvn.utils.world;

import net.kvn.utils.math.DoublePos;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class BlockPasteAction {

    ArrayList<BlockStatePos> pastedBlocks = new ArrayList<>();
    ArrayList<BlockStatePos> removedBlocks = new ArrayList<>();

    public BlockPasteAction() {}

    public BlockPasteAction(ArrayList<BlockStatePos> pastedBlocks) {
        this.pastedBlocks = pastedBlocks;
    }

    public BlockPasteAction(ArrayList<BlockPos> positions, BlockState state) {
        for (BlockPos pos : positions) {
            pastedBlocks.add(new BlockStatePos(pos, state));
            removedBlocks.add(new BlockStatePos(pos, WorldUtil.getBlockState(pos)));
        }
    }

    public BlockPasteAction(BlockState state, ArrayList<DoublePos> positions) {
        for (DoublePos pos : positions) {
            pastedBlocks.add(new BlockStatePos(pos.toBlockPos(), state));
            removedBlocks.add(new BlockStatePos(pos.toBlockPos(), WorldUtil.getBlockState(pos.toBlockPos())));
        }
    }

    public void undoAction() {
        for (BlockStatePos block : removedBlocks) {
            WorldUtil.setBlock(block.getPos(), block.getState());
        }
    }

    public ArrayList<BlockStatePos> getPastedBlocks() {
        return pastedBlocks;
    }

    public ArrayList<BlockStatePos> getRemovedBlocks() {
        return removedBlocks;
    }

    public void setPastedBlocks(ArrayList<BlockStatePos> pastedBlocks) {
        this.pastedBlocks = pastedBlocks;
    }

    public void setRemovedBlocks(ArrayList<BlockStatePos> removedBlocks) {
        this.removedBlocks = removedBlocks;
    }

    public void addBlock(BlockStatePos block) {
        pastedBlocks.add(block);
    }
}
