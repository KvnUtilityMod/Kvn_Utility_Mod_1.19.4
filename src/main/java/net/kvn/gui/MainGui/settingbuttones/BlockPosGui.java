package net.kvn.gui.MainGui.settingbuttones;

import net.kvn.modules.Module;
import net.kvn.settings.BlockPosValue;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;
import static net.kvn.utils.PlayerUtil.getPlayerPos;
import static net.kvn.utils.render.GuiUtil.getLevelOfSetting;

public class BlockPosGui extends SettingGui {

    private BlockPosValue setting;
    private int cordSettingHeight = 0;
    private int listeningCord = -1;
    private String input = "";

    public BlockPosGui(Module module, BlockPosValue setting, int x, int y, int width, int height) {
        super(module, setting, x, y, width, height);
        this.setting = setting;
        this.cordSettingHeight = height - 3;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, int x, int y, float delta) {
        super.render(matrices, mouseX, mouseY, x, y, delta);
        mc.textRenderer.draw(matrices, setting.isSettingsOpened() ? "-" : "+", x + getLevelOfSetting(setting) * 3 + 2, y + (super.getHeight() - 9) / 2F, moduleManager.clickGui.textSetting.getColor());

        String str = setting.getName() + " [" + setting.getX() + ", " + setting.getY() + ", " + setting.getZ() + "]";
        mc.textRenderer.draw(matrices, str, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str)) / 2F, super.getY() + (super.getHeight() - 9) / 2F, moduleManager.clickGui.textSetting.getColor());

        if (setting.isSettingsOpened()) {
            //render background
            DrawableHelper.fill(matrices, super.getX(), super.getY() + super.getHeight(), super.getX() + super.getWidth(), super.getY() + super.getHeight() + cordSettingHeight * 3, moduleManager.clickGui.backgroundSettings.getColor());

            //render cords
            String xStr = listeningCord == 0 ? input : String.valueOf(setting.getX());
            String yStr = listeningCord == 1 ? input : String.valueOf(setting.getY());
            String zStr = listeningCord == 2 ? input : String.valueOf(setting.getZ());
            mc.textRenderer.draw(matrices, xStr, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(xStr)) / 2F, super.getY() + super.getHeight() + (cordSettingHeight - 9) / 2F, moduleManager.clickGui.textSetting.getColor());
            mc.textRenderer.draw(matrices, yStr, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(yStr)) / 2F, super.getY() + super.getHeight() + cordSettingHeight + (cordSettingHeight - 9) / 2F, moduleManager.clickGui.textSetting.getColor());
            mc.textRenderer.draw(matrices, zStr, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(zStr)) / 2F, super.getY() + super.getHeight() + cordSettingHeight * 2 + (cordSettingHeight - 9) / 2F, moduleManager.clickGui.textSetting.getColor());

            //render the + and - at the end of setting in the middle of their box of 10 wide
            int plusX = super.getX() + super.getWidth() - 10 - mc.textRenderer.getWidth("+") - (10 - mc.textRenderer.getWidth("+")) / 2;
            float plusMinY = super.getY() + super.getHeight() + (cordSettingHeight - 9) / 2F;
            int minusX = super.getX() + super.getWidth() - mc.textRenderer.getWidth("-") - (10 - mc.textRenderer.getWidth("-")) / 2;

            mc.textRenderer.draw(matrices, "+", plusX, plusMinY, moduleManager.clickGui.textSetting.getColor());
            mc.textRenderer.draw(matrices, "-", minusX, plusMinY, moduleManager.clickGui.textSetting.getColor());
            mc.textRenderer.draw(matrices, "+", plusX, plusMinY + cordSettingHeight, moduleManager.clickGui.textSetting.getColor());
            mc.textRenderer.draw(matrices, "-", minusX, plusMinY + cordSettingHeight, moduleManager.clickGui.textSetting.getColor());
            mc.textRenderer.draw(matrices, "+", plusX, plusMinY + cordSettingHeight * 2, moduleManager.clickGui.textSetting.getColor());
            mc.textRenderer.draw(matrices, "-", minusX, plusMinY + cordSettingHeight * 2, moduleManager.clickGui.textSetting.getColor());
        }
    }

    @Override
    public void onMouseClick(int button, int x, int y) {
        super.onMouseClick(button, x, y);
        if (button != 1 && x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() && y < super.getY() + super.getHeight()) setting.setPos(getPlayerPos());
        if (setting.isSettingsOpened()) {
            if (button == 0 && x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() + super.getHeight() && y < super.getY() + super.getHeight() + cordSettingHeight) {
                if (x > super.getX() + super.getWidth() - 10) {
                    setting.setX(setting.getX() - 1);
                    return;
                }
                if (x > super.getX() + super.getWidth() - 20) {
                    setting.setX(setting.getX() + 1);
                    return;
                }
                listeningCord = 0;
            }
            if (button == 0 && x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() + super.getHeight() + cordSettingHeight && y < super.getY() + super.getHeight() + cordSettingHeight * 2) {
                if (x > super.getX() + super.getWidth() - 10) {
                    setting.setY(setting.getY() - 1);
                    return;
                }
                if (x > super.getX() + super.getWidth() - 20) {
                    setting.setY(setting.getY() + 1);
                    return;
                }

                listeningCord = 1;
            }
            if (button == 0 && x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() + super.getHeight() + cordSettingHeight * 2 && y < super.getY() + super.getHeight() + cordSettingHeight * 3) {
                if (x > super.getX() + super.getWidth() - 10) {
                    setting.setZ(setting.getZ() - 1);
                    return;
                }
                if (x > super.getX() + super.getWidth() - 20) {
                    setting.setZ(setting.getZ() + 1);
                    return;
                }
                listeningCord = 2;
            }
        }
    }

    @Override
    public void onKeyPressed(int key) {
        super.onKeyPressed(key);
        if (listeningCord != -1) {
            if (key == 256) {
                listeningCord = -1;
                input = "";
                return;
            }
            if (key == 257 || key == 335) {
                try {
                    if (listeningCord == 0) setting.setX(Integer.parseInt(input));
                    if (listeningCord == 1) setting.setY(Integer.parseInt(input));
                    if (listeningCord == 2) setting.setZ(Integer.parseInt(input));
                } catch (NumberFormatException ignored) {}
                listeningCord = -1;
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
        if (listeningCord != -1) {
            input += c;
        }
    }

    @Override
    public int getTotalHeight() {
        int height = super.getHeight();
        if (setting.isSettingsOpened()) {
            height += cordSettingHeight * 3;
        }
        for (SettingGui gui : super.getModeSettings()) {
            height += gui.getTotalHeight();
        }
        if (setting.isSettingsOpened()){
            for (SettingGui gui : super.getSettingButtons()) {
                height += gui.getTotalHeight();
            }
        }
        return height;
    }

    public BlockPosValue getSetting() {
        return setting;
    }
}
