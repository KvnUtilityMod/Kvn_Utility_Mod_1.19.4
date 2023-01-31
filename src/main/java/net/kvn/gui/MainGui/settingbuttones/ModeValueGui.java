package net.kvn.gui.MainGui.settingbuttones;

import net.kvn.modules.Module;
import net.kvn.settings.ModeValue;
import net.kvn.settings.Setting;
import net.kvn.utils.render.GuiUtil;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;

public class ModeValueGui extends SettingGui {

    ModeValue setting;

    public ModeValueGui(Module module, ModeValue setting, int x, int y, int width, int height) {
        super(module, setting, x, y, width, height);
        this.setting = setting;
        updateSettingsOf();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, int x, int y, float delta) {
        super.render(matrices, mouseX, mouseY, x, y, delta);
        String str = setting.getName() + " [" + setting.getModeName() + "]";
        mc.textRenderer.draw(matrices, str, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str)) / 2F, super.getY() + (super.getHeight() - 9) / 2F, moduleManager.clickGui.textSetting.getColor());
    }
    @Override
    public void onMouseClick(int button, int x, int y) {
        super.onMouseClick(button, x, y);
        if (x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() && y < super.getY() + super.getHeight()) {
            setting.nextMode();
            updateSettingsOf();
        }
    }

    public ModeValue getSetting() {
        return setting;
    }

    public void updateSettingsOf() {
        super.setModeSettings(GuiUtil.getSettingGuis(super.getModule(), super.getModule().getSettingsOf(setting), super.getX(), super.getY() + super.getHeight(), super.getWidth(), super.getHeight()));
    }
}
