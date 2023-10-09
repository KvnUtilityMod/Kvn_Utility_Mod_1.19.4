package net.kvn.utils.world.Structure;

import net.kvn.utils.world.BlockStatePos;

import java.util.ArrayList;

public interface LoadStrategy {
    ArrayList<BlockStatePos> getBlocksOrder(KvnStructure structure);
}
