package net.kvn.modules.shapes;

import net.kvn.modules.Category;
import net.kvn.modules.Module;

import static net.kvn.KvnUtilityMod.blockPlacer;

public class Undo extends Module {

    public Undo() {
        super("Undo", "undo", Category.SHAPES);
    }

    @Override
    public void onEnable() {
        blockPlacer.undo();
    }
}
