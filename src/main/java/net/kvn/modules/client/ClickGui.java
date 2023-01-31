package net.kvn.modules.client;

import net.kvn.gui.MainGui.MainGui;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.ColorValue;

import static net.kvn.KvnUtilityMod.mc;

public class ClickGui extends Module {

    public ColorValue textGui = addSetting(new ColorValue("Text of gui", "The color of the gui text", this));
    public ColorValue backgroundGui = addSetting(new ColorValue("Background gui", "The color of the gui background", this));
    public ColorValue textSetting = addSetting(new ColorValue("Text of settings", "The color of the text settings", this));
    public ColorValue backgroundSettings = addSetting(new ColorValue("Background settings", "The color of the setting background", this));

    public ClickGui() {
        super("ClickGui", "clickGui", Category.CLIENT);
        setKey(75);

        textGui.setModeNoSave(2);
        textGui.setHueDegreesNoSave(200);
        textGui.setHueSaturationNoSave(100);
        textGui.setHueBrightnessNoSave(30);
        textGui.setAlphaNoSave(230);

        backgroundGui.setModeNoSave(1);
        backgroundGui.setRedNoSave(30);
        backgroundGui.setGreenNoSave(150);
        backgroundGui.setBlueNoSave(220);
        backgroundGui.setAlphaNoSave(180);

        textSetting.setModeNoSave(1);
        textSetting.setRedNoSave(100);
        textSetting.setGreenNoSave(230);
        textSetting.setBlueNoSave(200);
        textSetting.setAlphaNoSave(255);

        backgroundSettings.setModeNoSave(1);
        backgroundSettings.setRedNoSave(25);
        backgroundSettings.setGreenNoSave(125);
        backgroundSettings.setBlueNoSave(135);
        backgroundSettings.setAlphaNoSave(255);
    }

    @Override
    public void onEnable() {
        mc.setScreenAndRender(MainGui.INSTANCE);
    }
}
