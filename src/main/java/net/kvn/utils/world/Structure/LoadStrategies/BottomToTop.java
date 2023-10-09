package net.kvn.utils.world.Structure.LoadStrategies;

import net.kvn.utils.world.BlockStatePos;
import net.kvn.utils.world.Structure.KvnStructure;
import net.kvn.utils.world.Structure.LoadStrategy;

import java.util.ArrayList;

public class BottomToTop implements LoadStrategy {

    @Override
    public ArrayList<BlockStatePos> getBlocksOrder(KvnStructure structure) {
        ArrayList<BlockStatePos> blocksOrder = new ArrayList<>();
        for (int y = 0; y <= structure.getyDiff(); y++) {
            for (BlockStatePos statePos : structure.getBlockStatePosList()) {
                if (statePos.getPos().getY() == y) {
                    blocksOrder.add(statePos);
                }
            }
        }
        return blocksOrder;
    }
}
