package net.kvn.gui.MainGui.settingbuttones;

import net.kvn.modules.Module;
import net.kvn.settings.KeyValue;
import net.minecraft.client.util.math.MatrixStack;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;
import static net.kvn.utils.input.KeyLib.getKeyName;

public class KeyValueGui extends SettingGui {

    private KeyValue setting;
    private boolean isListening;

    public KeyValueGui(Module module, KeyValue setting, int x, int y, int width, int height) {
        super(module, setting, x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, int x, int y, float delta) {
        super.render(matrixStack, mouseX, mouseY, x, y, delta);
        String str = isListening ? "Listening..." : "Key: " + getKeyName(setting.getKey());
        mc.textRenderer.draw(matrixStack, str, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str)) / 2F, super.getY() + (super.getHeight() - 9) / 2F, moduleManager.clickGui.textSetting.getColor());
    }

    @Override
    public void onMouseClick(int button, int x, int y) {
        super.onMouseClick(button, x, y);
        if (button == 0 && x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() && y < super.getY() + super.getHeight()) isListening = !isListening;
    }

    @Override
    public void onKeyPressed(int key) {
        super.onKeyPressed(key);
        if (isListening) {
            if (key == 256) {
                isListening = false;
                return;
            }
            if (key == 261) {
                isListening = false;
                setting.setKey(0);
                return;
            }
            setting.setKey(key);
            isListening = false;
        }
    }
}
