package net.kvn.gui.MainGui.settingbuttones;

import net.kvn.modules.Module;
import net.kvn.settings.IntegerValue;
import net.minecraft.client.util.math.MatrixStack;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;

public class IntegerValueGui extends SettingGui {

    private IntegerValue setting;
    private boolean isListening = false;
    private int mouseClickedX = 0;
    private int mouseClickedY = 0;
    private String input = "";

    public IntegerValueGui(Module module, IntegerValue setting, int x, int y, int width, int height) {
        super(module, setting, x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, int x, int y, float delta) {
        super.render(matrices, mouseX, mouseY, x, y, delta);
        String text = isListening ? setting.getName() + ": [" + input + "]" : setting.getName() + ": " + setting.getValue();
        mc.textRenderer.draw(matrices, text, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(text)) / 2F, super.getY() + (super.getHeight() - 9) / 2F, moduleManager.clickGui.textSetting.getColor());
    }

    @Override
    public void onMouseClick(int button, int x, int y) {
        super.onMouseClick(button, x, y);
        if (button != 1 && x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() && y < super.getY() + super.getHeight()) {
            mouseClickedX = x;
            mouseClickedY = y;
        } else {
            isListening = false;
        }
    }

    @Override
    public void onMouseRelease(int button, int x, int y) {
        super.onMouseRelease(button, x, y);
        if (button != 1 && x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() && y < super.getY() + super.getHeight()) {
            if (mouseClickedX == x && mouseClickedY == y) {
                isListening = !isListening;
            }
        }
    }

    @Override
    public void onKeyPressed(int key) {
        super.onKeyPressed(key);
        if (isListening) {
            if (key == 256) {
                isListening = false;
                return;
            }
            if (key == 257 || key == 335) {
                try {
                    setting.updateValue(Integer.parseInt(input));
                } catch (NumberFormatException ignored) {}
                isListening = false;
                input = "";
                return;
            }
            if (key == 259) {
                if (input.length() > 0) input = input.substring(0, input.length() - 1);
            }
        }
    }

    @Override
    public void onCharInput(char c)  {
        super.onCharInput(c);
        if (isListening) {
            input += c;
        }
    }
}
