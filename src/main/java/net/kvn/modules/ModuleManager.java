package net.kvn.modules;

import net.kvn.modules.QuickWorldEdit.*;
import net.kvn.modules.build.Ruler;
import net.kvn.modules.client.ClickGui;
import net.kvn.modules.client.TestModule;
import net.kvn.modules.shapes.*;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.customEventHandler;

public class ModuleManager {

    private ArrayList<Module> modules = new ArrayList<>();

    //Build
    public Ruler ruler = addModule(new Ruler());

    //Client
    public TestModule testModule =  addModule(new TestModule());
    public ClickGui clickGui = addModule(new ClickGui());

    //QuickWorldEdit
    public SetPos1 setPos1 = addModule(new SetPos1());
    public SetPos2 setPos2 = addModule(new SetPos2());
    public Copy copy = addModule(new Copy());
    public Rotate rotate = addModule(new Rotate());
    public Paste paste = addModule(new Paste());
    public Flip flip = addModule(new Flip());
    public Stack stack = addModule(new Stack());
    public Mirror mirror = addModule(new Mirror());

    //Shapes
    public Circle circle = addModule(new Circle());
    public Line line = addModule(new Line());
    public Arch arch = addModule(new Arch());
    public Undo undo = addModule(new Undo());
    public Redo redo = addModule(new Redo());

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
