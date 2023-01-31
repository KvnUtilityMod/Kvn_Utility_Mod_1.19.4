package net.kvn.utils.world;

import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;

public class BlockPlacer {

    ArrayList<BlockPasteAction> actions = new ArrayList<>();
    ArrayList<BlockPasteAction> undone = new ArrayList<>();

    public BlockPlacer() {}

    public BlockPlacer(ArrayList<BlockPasteAction> actions) {
        this.actions = actions;
    }

    public void paste(BlockPasteAction action) {
        actions.add(action);
        ServerWorld world = WorldUtil.getServerWorld();
        for (BlockStatePos block : action.getPastedBlocks()) {
            world.setBlockState(block.getPos(), block.getState());
        }
    }

    public void undo() {
        if (actions.size() > 0) {
            BlockPasteAction action = actions.get(actions.size() - 1);
            action.undoAction();
            undone.add(action);
            actions.remove(action);
        }
    }

    public void redo() {
        if (undone.size() > 0) {
            BlockPasteAction action = undone.get(undone.size() - 1);
            paste(action);
            actions.add(action);
            undone.remove(action);
        }
    }
}
