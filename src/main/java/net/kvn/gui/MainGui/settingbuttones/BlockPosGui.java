package net.kvn.gui.MainGui.settingbuttones;

import net.kvn.modules.Module;
import net.kvn.settings.BlockPosValue;
import net.minecraft.client.util.math.MatrixStack;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;
import static net.kvn.utils.PlayerUtil.getPlayerPos;

public class BlockPosGui extends SettingGui {

    private BlockPosValue setting;

    public BlockPosGui(Module module, BlockPosValue setting, int x, int y, int width, int height) {
        super(module, setting, x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, int x, int y, float delta) {
        super.render(matrices, mouseX, mouseY, x, y, delta);
        String str = setting.getName() + " [" + setting.getX() + ", " + setting.getY() + ", " + setting.getZ() + "]";
        mc.textRenderer.draw(matrices, str, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str)) / 2F, super.getY() + (super.getHeight() - 9) / 2F, moduleManager.clickGui.textSetting.getColor());
    }

    @Override
    public void onMouseClick(int button, int x, int y) {
        super.onMouseClick(button, x, y);
        if (button != 1 && x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() && y < super.getY() + super.getHeight()) setting.setPos(getPlayerPos());
    }

    public BlockPosValue getSetting() {
        return setting;
    }
}
