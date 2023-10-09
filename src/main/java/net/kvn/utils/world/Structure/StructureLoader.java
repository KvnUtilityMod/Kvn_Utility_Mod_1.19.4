package net.kvn.utils.world.Structure;

import net.kvn.utils.world.BlockStatePos;
import net.kvn.utils.world.WorldUtil;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class StructureLoader {

    private boolean loading = false;
    private int index = 0;
    private double startTime = 0;
    private double durationMillis = 5000;
    private BlockPos startPos;
    private KvnStructure structure;
    private LoadStrategy loadStrategy;
    private ArrayList<BlockStatePos> blocksToLoad;
    int blockStatesToChange = 0;
    int blockStatesChanged = 0;

    public StructureLoader() {}

    public void loadNextBlocks() {
        int blockCountToLoad = getBlockAmountToLoad();
        blockStatesChanged += blockCountToLoad;

        if (blockCountToLoad <= 0 && System.currentTimeMillis() - startTime > durationMillis) {
            loading = false;
            return;
        }

        while (blockCountToLoad > 0 && index < blocksToLoad.size()) {
            BlockStatePos blockStatePos = blocksToLoad.get(index);

            if (WorldUtil.getBlockState(getRealPos(blockStatePos)) != blockStatePos.getState()) {
                WorldUtil.setBlock(getRealPos(blockStatePos), blockStatePos.getState());
                blockCountToLoad--;
            }
            index++;
        }
    }

    private BlockPos getRealPos(BlockStatePos blockStatePos) {
        return new BlockPos(
                startPos.getX() + blockStatePos.getPos().getX(),
                startPos.getY() + blockStatePos.getPos().getY(),
                startPos.getZ() + blockStatePos.getPos().getZ()
        );
    }

    public boolean isDone() {
        return blocksToLoad.size() == 0;
    }

    public double getDurationMillis() {
        return durationMillis;
    }

    public void startLoading(int timeMillis, KvnStructure structure, LoadStrategy loadStrategy, BlockPos startPos) {
        this.structure = structure;
        this.loadStrategy = loadStrategy;
        this.durationMillis = timeMillis;
        this.startPos = startPos;
        blocksToLoad = structure.getBlockStatePosList();
        startTime = System.currentTimeMillis();
        loading = true;
        index = 0;
        updateBlockStatesToChange();
        blockStatesChanged = 0;

        if (loadStrategy == null) {
            blocksToLoad = structure.getBlockStatePosList();
        } else {
            blocksToLoad = loadStrategy.getBlocksOrder(structure);
        }
    }

    public int getBlockAmountToLoad() {
        double totalPercent = (System.currentTimeMillis() - startTime) / durationMillis;
        totalPercent = Math.min(totalPercent, 1.0);
        return (int) (blockStatesToChange * totalPercent) - blockStatesChanged;
    }

    public void setStructure(KvnStructure structure) {
        this.structure = structure;
    }
    public KvnStructure getStructure() {
        return structure;
    }

    public void setLoadStrategy(LoadStrategy loadStrategy) {
        this.loadStrategy = loadStrategy;
    }
    public LoadStrategy getLoadStrategy() {
        return loadStrategy;
    }

    public boolean isLoading() {
        return loading;
    }

    public ArrayList<BlockStatePos> getBlocksToLoad() {
        return blocksToLoad;
    }

    public void updateBlockStatesToChange() {
        blockStatesToChange = 0;
        for (BlockStatePos blockStatePos : blocksToLoad) {
            if (WorldUtil.getBlockState(getRealPos(blockStatePos)) != blockStatePos.getState()) {
                blockStatesToChange++;
            }
        }
    }
}
