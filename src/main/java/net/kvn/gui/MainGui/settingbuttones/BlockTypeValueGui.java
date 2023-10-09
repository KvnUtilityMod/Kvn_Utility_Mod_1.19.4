package net.kvn.gui.MainGui.settingbuttones;

import net.kvn.modules.Module;
import net.kvn.settings.BlockTypeValue;
import net.kvn.utils.input.TextUtil;
import net.minecraft.block.Block;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static net.kvn.KvnUtilityMod.mc;
import static net.kvn.KvnUtilityMod.moduleManager;

public class BlockTypeValueGui extends SettingGui {

    private BlockTypeValue setting;
    private boolean isListening;
    private String input = "";
    private Map<String, Integer> scores = new HashMap<>();
    private ArrayList<String> orderedScores = new ArrayList<>();
    private String bestMatch = "";

    public BlockTypeValueGui(Module module, BlockTypeValue setting, int x, int y, int width, int height) {
        super(module, setting, x, y, width, height);
        this.setting = setting;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, int x, int y, float delta) {
        super.render(matrixStack, mouseX, mouseY, x, y, delta);
        String str = isListening ? "[ " + input + " ]" : "Block: " + setting.getBlockType();
        if (input.length() > 0) str += " tab:" + bestMatch;
        //Ik ben hier geweest
        mc.textRenderer.draw(matrixStack, str, super.getX() + (super.getWidth() - mc.textRenderer.getWidth(str)) / 2F, super.getY() + (super.getHeight() - 9) / 2F, moduleManager.clickGui.textSetting.getColor());
    }

    @Override
    public void onMouseClick(int button, int x, int y)  {
        super.onMouseClick(button, x, y);
        if (button == 0 && x > super.getX() && x < super.getX() + super.getWidth() && y > super.getY() && y < super.getY() + super.getHeight()) {
            isListening = !isListening;
        } else {
            if (isListening) saveInput();
        }
    }

    @Override
    public void onKeyPressed(int key) {
        System.out.println(key);
        super.onKeyPressed(key);
        if (isListening) {
            if (key == 256) {
                isListening = false;
                return;
            }
            if (key == 257 || key == 335) {
                saveInput();
                return;
            }
            if (key == 258) {
                saveRecommendedInput();
            }
            if (key == 259) {
                if (input.length() > 0) input = input.substring(0, input.length() - 1);
                updateSuggestions();
            }
        }
    }

    @Override
    public void onCharInput(char c)  {
        super.onCharInput(c);
        if (isListening) {
            input += c;
            updateSuggestions();
        }
    }

    private void updateSuggestions() {
        if (input.length() > 0) {
            scores = TextUtil.getBlockScores(input);
            orderedScores = scores.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toCollection(ArrayList::new));
            bestMatch = orderedScores.get(orderedScores.size() - 1);
        }
    }

    private void saveInput() {
        if (input.equals("")) return;
        setting.setBlockType(input);
        isListening = false;
        input = "";
    }

    private void saveRecommendedInput() {
        if (input.equals("")) return;
        setting.setBlockType(bestMatch);
        System.out.println("saved");
        isListening = false;
        input = "";
    }
}
