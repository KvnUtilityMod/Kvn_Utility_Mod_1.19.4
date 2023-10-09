package net.kvn.gui.MainGui.settingbuttones;

import net.kvn.modules.Module;
import net.kvn.settings.SettingPlaceHolder;
import net.minecraft.client.util.math.MatrixStack;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;

public class SettingPlaceHolderGui extends SettingGui {

    private SettingPlaceHolder setting;

    public SettingPlaceHolderGui(Module m, SettingPlaceHolder setting, int x, int y, int width, int height) {
        super(m, setting, x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, int x, int y, float delta) {
        super.render(matrixStack, mouseX, mouseY, x, y, delta);
        String str = setting.getName();
        mc.textRenderer.draw(matrixStack, setting.getName(), super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str)) / 2F, super.getY() + (super.getHeight() - 9) / 2F, moduleManager.clickGui.textSetting.getColor());
    }
}
