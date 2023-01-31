package net.kvn.gui.MainGui.settingbuttones;

import net.kvn.modules.Module;
import net.kvn.settings.BooleanValue;
import net.minecraft.client.util.math.MatrixStack;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;

public class BooleanGui extends SettingGui {

    private BooleanValue setting;

    public BooleanGui(Module module, BooleanValue setting, int x, int y, int width, int height) {
        super(module, setting, x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, int x, int y, float delta) {
        super.render(matrices, mouseX, mouseY, x, y, delta);
        mc.textRenderer.draw(matrices, setting.getName(), super.getX() + (super.getWidth() - mc.textRenderer.getWidth(setting.getName())) / 2F, super.getY() + (super.getHeight() - 9) / 2F, setting.isTrue() ? moduleManager.clickGui.textSetting.getColor() : 0xFFFFFFFF);
    }

    @Override
    public void onMouseClick(int button, int x, int y) {
        super.onMouseClick(button, x, y);
        if (button != 1 && x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() && y < super.getY() + super.getHeight()) setting.toggleValue();
    }
}
