package net.kvn.modules;

import net.kvn.modules.QuickWorldEdit.*;
import net.kvn.modules.Structures.CountBlocks;
import net.kvn.modules.Structures.Load;
import net.kvn.modules.Structures.Save;
import net.kvn.modules.build.Mirror;
import net.kvn.modules.build.Ruler;
import net.kvn.modules.client.ClickGui;
import net.kvn.modules.client.FillMyPos;
import net.kvn.modules.client.QuickCrafting;
import net.kvn.modules.client.TestModule;
import net.kvn.modules.shapes.*;
import net.kvn.modules.shapes.Redo;
import net.kvn.modules.shapes.Undo;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.customEventHandler;

public class ModuleManager {

    private ArrayList<Module> modules = new ArrayList<>();

    //Build
    public Ruler ruler = addModule(new Ruler());
    public Mirror mirror = addModule(new Mirror());

    //Client
    public TestModule testModule =  addModule(new TestModule());
    public ClickGui clickGui = addModule(new ClickGui());
    public FillMyPos fillMyPos = addModule(new FillMyPos());
    public QuickCrafting quickCrafting = addModule(new QuickCrafting());

    //QuickWorldEdit
    public SetPos1 setPos1 = addModule(new SetPos1());
    public SetPos2 setPos2 = addModule(new SetPos2());
    public Copy copy = addModule(new Copy());
    public Rotate rotate = addModule(new Rotate());
    public ReplaceAir replaceAir = addModule(new ReplaceAir());
    public Paste paste = addModule(new Paste());
    public Flip flip = addModule(new Flip());
    public Stack stack = addModule(new Stack());
    public MirrorQW mirror2 = addModule(new MirrorQW());
    public RedoQW redoQW = addModule(new RedoQW());
    public UndoQW undoQW = addModule(new UndoQW());

    //Shapes
    public Line line = addModule(new Line());
    public Arch arch = addModule(new Arch());
    public Circle circle = addModule(new Circle());
    public CurvedPlane curvedPlane = addModule(new CurvedPlane());
    public Undo undo = addModule(new Undo());
    public Redo redo = addModule(new Redo());

    //Structure
    public Load load = addModule(new Load());
    public Save save = addModule(new Save());
    public CountBlocks countBlocks = addModule(new CountBlocks());


    public ModuleManager() {}

    public ArrayList<Module> getModulesOfCategory(Category category) {
        ArrayList<Module> modulesOfCategory = new ArrayList<>();
        for (Module module : modules) {
            if (module.getCategory() == category) {
                modulesOfCategory.add(module);
            }
        }
        return modulesOfCategory;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public <M extends Module> M addModule(M module) {
        customEventHandler.addListener(module);
        modules.add(module);
        return module;
    }
}
