package net.kvn.modules.shapes;

import net.kvn.modules.Category;
import net.kvn.modules.Module;

import static net.kvn.KvnUtilityMod.blockPlacer;

public class Redo extends Module {

    public Redo() {
        super("redo", "redo", Category.SHAPES);
    }

    @Override
    public void onEnable() {
        blockPlacer.redo();
    }

}
