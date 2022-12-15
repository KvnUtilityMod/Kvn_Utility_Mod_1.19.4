package net.kvn.modules.client;

import net.kvn.event.events.MouseClick;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.BooleanValue;
import net.kvn.settings.ColorValue;
import net.kvn.settings.IntegerValue;

public class TestModule extends Module implements MouseClick {

    public net.kvn.settings.BooleanValue testBoolean = addSetting(new BooleanValue("TestBoolean", "testBoolean", this, true));
    public BooleanValue testBoolean2 = addSetting(new BooleanValue("TestBoolean2", "testBoolean2", this, testBoolean, true));
    public BooleanValue testBoolean3 = addSetting(new BooleanValue("TestBoolean3", "testBoolean3", this, testBoolean2, true));
    public BooleanValue testBoolean4 = addSetting(new BooleanValue("TestBoolean4", "testBoolean4", this, true));
    public ColorValue testColor = addSetting(new ColorValue("TestColor", "testColor", this, null));
    public IntegerValue testInteger = addSetting(new IntegerValue("TestInteger", "testInteger", this, 0, 0, 10));

    public TestModule() {
        super("TestModule", "testModule", Category.CLIENT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        System.out.println("TestModule enabled!");
    }

    @Override
    public void onMouseClick(int button, int x, int y) {
        /*
        System.out.println("Mouse clicked!");
        print the button, pressed, x and y
        System.out.println("Button: " + button + " Pressed: " + pressed + " X: " + x + " Y: " + y);
        */
    }
}
