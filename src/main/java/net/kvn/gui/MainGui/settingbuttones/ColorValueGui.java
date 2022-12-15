package net.kvn.gui.MainGui.settingbuttones;

import net.kvn.modules.Module;
import net.kvn.settings.ColorValue;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;
import static net.kvn.utils.render.GuiUtil.getLevelOfSetting;

public class ColorValueGui extends SettingGui {

    private ColorValue setting;
    private int buttonheight = 0;
    private int buttonListening = -1;
    private String input = "";

    public ColorValueGui(Module module, ColorValue colorValue, int x, int y, int width, int height) {
        super(module, colorValue, x, y, width, height);
        this.setting = colorValue;
        this.buttonheight = height;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, int x, int y, float delta) {
        super.render(matrices, mouseX, mouseY, x, y, delta);

        int textColor = moduleManager.clickGui.textSetting.getColor();
        String str = setting.getName() + ": " + setting.getStrMode();
        mc.textRenderer.draw(matrices, str, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str)) / 2F, super.getY() + (super.getHeight() - 9) / 2F, textColor);
        DrawableHelper.fill(matrices, (int) (super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str)) / 2F), super.getY() + buttonheight - 4, (int) (super.getX() + (super.getWidth() + mc.textRenderer.getWidth(str)) / 2F), super.getY() + buttonheight - 1, new Color(255, 255, 255, 255).getRGB());
        DrawableHelper.fill(matrices, (int) (super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str)) / 2F), super.getY() + buttonheight - 4, (int) (super.getX() + (super.getWidth() + mc.textRenderer.getWidth(str)) / 2F), super.getY() + buttonheight - 1, setting.getColor());

        if (setting.isSettingsOpened()){
            int levels = getLevelOfSetting(setting) + 1;
            Color c = moduleManager.clickGui.textSetting.getColorObj();
            DrawableHelper.fill(matrices, super.getX(), super.getY() + buttonheight, super.getX() + super.getWidth(), super.getY() + getHeight(), moduleManager.clickGui.backgroundSettings.getColor());
            DrawableHelper.fill(matrices, x + (levels * 3), y + buttonheight, x + (levels * 3) + 1, y + getHeight(), new Color(Math.max(c.getGreen() - 50, 0), Math.max(c.getBlue() - 50, 0), Math.min(c.getAlpha() + 50, 255)).getRGB());

            String str1 = "";
            String str2 = "";
            String str3 = "";
            String str4 = buttonListening == 3 ? "Alpha: " + input : "Alpha: " + setting.getAlpha();

            switch (setting.getMode()){
                case 0:
                    str1 = buttonListening == 0 ? "Rainbow Speed: [ " + input + " ]" : "Rainbow Speed: " + setting.getRainBowSpeed();
                    str2 = buttonListening == 1 ? "Rainbow Saturation: [ " + input + " ]" : "Rainbow Saturation: " + setting.getRainBowSaturation();
                    str3 = buttonListening == 2 ? "Rainbow Brightness: [ " + input + " ]" : "Rainbow Brightness: " + setting.getRainBowBrightness();
                    break;
                case 1:
                    str1 = buttonListening == 0 ? "Red: [ " + input + " ]" : "Red: " + setting.getRed();
                    str2 = buttonListening == 1 ? "Green: [ " + input + " ]" : "Green: " + setting.getGreen();
                    str3 = buttonListening == 2 ? "Blue: [ " + input + " ]" : "Blue: " + setting.getBlue();
                    break;
                case 2:
                    str1 = buttonListening == 0 ? "Hue: [ " + input + " ]" : "Hue: " + setting.getHueDegrees();
                    str2 = buttonListening == 1 ? "Saturation: [ " + input + " ]" : "Saturation: " + setting.getHueSaturation();
                    str3 = buttonListening == 2 ? "Brightness: [ " + input + " ]" : "Brightness: " + setting.getHueBrightness();
                    break;
            }
            mc.textRenderer.draw(matrices, str1, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str1)) / 2F, super.getY() + buttonheight + (super.getHeight() - 9) / 2F, textColor);
            mc.textRenderer.draw(matrices, str2, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str2)) / 2F, super.getY() + buttonheight * 2+ (super.getHeight() - 9) / 2F, textColor);
            mc.textRenderer.draw(matrices, str3, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str3)) / 2F, super.getY() + buttonheight * 3+ (super.getHeight() - 9) / 2F, textColor);
            mc.textRenderer.draw(matrices, str4, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str4)) / 2F, super.getY() + buttonheight * 4 + (super.getHeight() - 9) / 2F, textColor);
        }
    }

    @Override
    public void onMouseClick(int button, int x, int y) {
        super.onMouseClick(button, x, y);
        if (x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() && y < super.getY() + super.getHeight() && button != 1) setting.nextMode();

        if (setting.isSettingsOpened()){

            if (x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() + buttonheight && y < super.getY() + getHeight() && button != 1) {

                int buttonIndex = (y - (super.getY() + buttonheight)) / buttonheight;
                if (buttonIndex == buttonListening) {
                    try {
                        int value = Integer.parseInt(input);
                        setting.setValue(buttonListening, value);
                    } catch (NumberFormatException ignored) {}
                    buttonListening = -1;
                    input = "";
                    return;
                }
                buttonListening = buttonIndex;

            } else {
                buttonListening = -1;
            }
            input = "";
        }
    }

    @Override
    public void onKeyPressed(int key) {
        super.onKeyPressed(key);
        if (buttonListening != -1){
            if (key == 256) {
                buttonListening = -1;
                return;
            }
            if (key == 257 || key == 335) {
                try {
                    int value = Integer.parseInt(input);
                    setting.setValue(buttonListening, value);
                } catch (NumberFormatException ignored) {}
                buttonListening = -1;
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
        if (buttonListening != -1) input += c;
    }

    @Override
    public int getHeight() {
        return setting.isSettingsOpened() ? buttonheight * 5 : buttonheight;
    }

    @Override
    public boolean hasSubPartsOpened() {
        return true;
    }
}