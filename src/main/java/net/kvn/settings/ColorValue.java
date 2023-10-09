package net.kvn.settings;

import net.kvn.modules.Module;
import net.kvn.utils.file.FileReader;
import net.kvn.utils.file.FileWriter;

import java.awt.*;
import java.util.ArrayList;

import static net.kvn.KvnUtilityMod.colorSettings;
import static net.kvn.KvnUtilityMod.customEventHandler;
import static net.kvn.utils.input.TextUtil.getIndex;

public class ColorValue extends Setting {

    int mode = 0;
    int alpha = 150;
    String saveString;

    //mode 0
    int rainBowSpeed = 10; //  1 - 100
    int rainBowSaturation = 50; //  0 - 100
    int rainBowBrightness = 100; //  0 - 100

    //mode 1
    int red = 0; //  0 - 255
    int green = 0; //  0 - 255
    int blue = 0; //  0 - 255

    //mode 2
    int hueDegrees = 100; //  0 - 359
    int hueSaturation = 100;  //  0 - 100
    int hueBrightness = 100; //  0 - 100

    public ColorValue(String name, String description, Module module, Setting settingOf){
        super(name, description, module, settingOf);
        saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
    }

    public ColorValue(String name, String description, Module module){
        super(name, description, module, null);
        saveString = module.getName() + " " + super.getSettingOfString() + " " + name;
    }

    //get color object
    public Color getColorObj(){

        switch (mode){

            case 0:
                if (rainBowSpeed <= 0) rainBowSpeed = 1;
                float color = (float) (((double)System.currentTimeMillis() / (double)(300 / rainBowSpeed)) % 360);
                Color c = Color.getHSBColor(color / 360f, rainBowSaturation / 100f, rainBowBrightness / 100f);
                return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);

            case 1:
                return new Color(red, green, blue, alpha);

            case 2:
                Color c2 = Color.getHSBColor(hueDegrees / 360f, hueSaturation / 100f, hueBrightness / 100f);
                return new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), alpha);
        }
        mode = 0;
        return new Color(0, 0, 0);
    }

    //get color
    public int getColor() {
        return getColorObj().getRGB();
    }

    public Color getDarkerColor(int amount){
        if (amount < 0) amount = 0;
        if (amount > 100) amount = 100;
        int multiplier = 100 - amount;

        Color c = getColorObj();
        int red = c.getRed() - (c.getRed() * multiplier / 100);
        int green = c.getGreen() - (c.getGreen() * multiplier / 100);
        int blue = c.getBlue() - (c.getBlue() * multiplier / 100);
        int alpha = c.getAlpha() + ((255 - c.getAlpha()) * multiplier / 100);

        return new Color(red, green, blue, alpha);
    }

    //next mode
    public void nextMode() {
        mode++;
        if (mode > 2) mode = 0;
    }

    //get string mode
    public String getStrMode() {
        switch (mode) {
            case 0:
                return "Rainbow";
            case 1:
                return "RGB";
            case 2:
                return "Hue";
        }
        return "Rainbow";
    }

    //load from file
    public ArrayList<String> loadColorFromFile(ArrayList<String> colorSettings){
        int index = getIndex(saveString, colorSettings);
        if (index == -1){
            colorSettings.add(saveString + " = [" + mode + ", " + alpha + ", " + rainBowSpeed + ", " + rainBowSaturation + ", " + rainBowBrightness + ", " + red + ", " + green + ", " + blue + ", " + hueDegrees + ", " + hueSaturation + ", " + hueBrightness + "]");
            return colorSettings;
        } else {
            try {
                String[] color = colorSettings.get(index).split(" = ")[1].replace("[", "").replace("]", "").split(", ");
                setMode(Integer.parseInt(color[0]));
                setAlpha(Integer.parseInt(color[1]));
                setRainBowSpeed(Integer.parseInt(color[2]));
                setRainBowSaturation(Integer.parseInt(color[3]));
                setRainBowBrightness(Integer.parseInt(color[4]));
                setRed(Integer.parseInt(color[5]));
                setGreen(Integer.parseInt(color[6]));
                setBlue(Integer.parseInt(color[7]));
                setHueDegrees(Integer.parseInt(color[8]));
                setHueSaturation(Integer.parseInt(color[9]));
                setHueBrightness(Integer.parseInt(color[10]));
            } catch (Exception e){
                colorSettings.set(index, saveString + " = [" + mode + ", " + alpha + ", " + rainBowSpeed + ", " + rainBowSaturation + ", " + rainBowBrightness + ", " + red + ", " + green + ", " + blue + ", " + hueDegrees + ", " + hueSaturation + ", " + hueBrightness + "]");
            }
            return colorSettings;
        }
    }

    public void updateColorInFile(){
        ArrayList<String> colorSettingStrings = FileReader.readLines(colorSettings);

        int index = getIndex(saveString, colorSettingStrings);
        if (index == -1){
            colorSettingStrings.add(saveString + " = [" + mode + ", " + alpha + ", " + rainBowSpeed + ", " + rainBowSaturation + ", " + rainBowBrightness + ", " + red + ", " + green + ", " + blue + ", " + hueDegrees + ", " + hueSaturation + ", " + hueBrightness + "]");
        } else {
            colorSettingStrings.set(index, saveString + " = [" + mode + ", " + alpha + ", " + rainBowSpeed + ", " + rainBowSaturation + ", " + rainBowBrightness + ", " + red + ", " + green + ", " + blue + ", " + hueDegrees + ", " + hueSaturation + ", " + hueBrightness + "]");
        }
        FileWriter.writeLines(colorSettingStrings, colorSettings);
        customEventHandler.onSettingUpdate(this);
    }

    public void setValue(int button, int value){
        if (button == 3){
            setAlpha(value);
            return;
        }

        switch (mode) {
            case 0 -> {
                if (button == 0) setRainBowSpeed(value);
                if (button == 1) setRainBowSaturation(value);
                if (button == 2) setRainBowBrightness(value);
            }
            case 1 -> {
                if (button == 0) setRed(value);
                if (button == 1) setGreen(value);
                if (button == 2) setBlue(value);
            }
            case 2 -> {
                if (button == 0) setHueDegrees(value);
                if (button == 1) setHueSaturation(value);
                if (button == 2) setHueBrightness(value);
            }
        }
    }

    public int getMode() {
        return mode;
    }
    public void setMode(int mode) {
        this.mode = mode;
        updateColorInFile();
    }
    public void setModeNoSave(int mode) {
        if (mode > 2) mode = 0;
        if (mode < 0) mode = 0;
        this.mode = mode;
    }
    public int getAlpha() {
        return alpha;
    }
    public void setAlpha(int alpha) {
        if (alpha < 0) alpha = 0;
        if (alpha > 255) alpha = 255;
        this.alpha = alpha;
        updateColorInFile();
    }
    public void setAlphaNoSave(int alpha) {
        if (alpha < 0) alpha = 0;
        if (alpha > 255) alpha = 255;
        this.alpha = alpha;
    }

    public int getRainBowSpeed() {
        return rainBowSpeed;
    }
    public void setRainBowSpeed(int rainBowSpeed) {
        if (rainBowSpeed <= 0) rainBowSpeed = 1;
        if (rainBowSpeed > 100) rainBowSpeed = 100;
        this.rainBowSpeed = rainBowSpeed;
        updateColorInFile();
    }
    public void setRainBowSpeedNoSave(int rainBowSpeed) {
        if (rainBowSpeed <= 0) rainBowSpeed = 1;
        if (rainBowSpeed > 100) rainBowSpeed = 100;
        this.rainBowSpeed = rainBowSpeed;
    }
    public int getRainBowSaturation() {
        return rainBowSaturation;
    }
    public void setRainBowSaturation(int rainBowSaturation) {
        if (rainBowSaturation < 0) rainBowSaturation = 0;
        if (rainBowSaturation > 100) rainBowSaturation = 100;
        this.rainBowSaturation = rainBowSaturation;
        updateColorInFile();
    }
    public void setRainBowSaturationNoSave(int rainBowSaturation) {
        if (rainBowSaturation < 0) rainBowSaturation = 0;
        if (rainBowSaturation > 100) rainBowSaturation = 100;
        this.rainBowSaturation = rainBowSaturation;
    }
    public int getRainBowBrightness() {
        return rainBowBrightness;
    }
    public void setRainBowBrightness(int rainBowBrightness) {
        if (rainBowBrightness < 0) rainBowBrightness = 0;
        if (rainBowBrightness > 100) rainBowBrightness = 100;
        this.rainBowBrightness = rainBowBrightness;
        updateColorInFile();
    }
    public void setRainBowBrightnessNoSave(int rainBowBrightness) {
        if (rainBowBrightness < 0) rainBowBrightness = 0;
        if (rainBowBrightness > 100) rainBowBrightness = 100;
        this.rainBowBrightness = rainBowBrightness;
    }

    public int getRed() {
        return red;
    }
    public void setRed(int red) {
        if (red > 255) red = 255;
        if (red < 0) red = 0;
        this.red = red;
        updateColorInFile();
    }
    public void setRedNoSave(int red) {
        if (red > 255) red = 255;
        if (red < 0) red = 0;
        this.red = red;
    }
    public int getGreen() {
        return green;
    }
    public void setGreen(int green) {
        if (green > 255) green = 255;
        if (green < 0) green = 0;
        this.green = green;
        updateColorInFile();
    }
    public void setGreenNoSave(int green) {
        if (green > 255) green = 255;
        if (green < 0) green = 0;
        this.green = green;
    }
    public int getBlue() {
        return blue;
    }
    public void setBlue(int blue) {
        if (blue > 255) blue = 255;
        if (blue < 0) blue = 0;
        this.blue = blue;
        updateColorInFile();
    }
    public void setBlueNoSave(int blue) {
        if (blue > 255) blue = 255;
        if (blue < 0) blue = 0;
        this.blue = blue;
    }

    public int getHueDegrees() {
        return hueDegrees;
    }
    public void setHueDegrees(int hueDegrees) {
        if (hueDegrees > 359) hueDegrees = 359;
        if (hueDegrees < 0) hueDegrees = 0;
        this.hueDegrees = hueDegrees;
        updateColorInFile();
    }
    public void setHueDegreesNoSave(int hueDegrees) {
        if (hueDegrees > 359) hueDegrees = 359;
        if (hueDegrees < 0) hueDegrees = 0;
        this.hueDegrees = hueDegrees;
    }
    public int getHueSaturation() {
        return hueSaturation;
    }
    public void setHueSaturation(int hueSaturation) {
        if (hueSaturation > 100) hueSaturation = 100;
        if (hueSaturation < 0) hueSaturation = 0;
        this.hueSaturation = hueSaturation;
        updateColorInFile();
    }
    public void setHueSaturationNoSave(int hueSaturation) {
        if (hueSaturation > 100) hueSaturation = 100;
        if (hueSaturation < 0) hueSaturation = 0;
        this.hueSaturation = hueSaturation;
    }
    public int getHueBrightness() {
        return hueBrightness;
    }
    public void setHueBrightness(int hueBrightness) {
        if (hueBrightness > 100) hueBrightness = 100;
        if (hueBrightness < 0) hueBrightness = 0;
        this.hueBrightness = hueBrightness;
        updateColorInFile();
    }
    public void setHueBrightnessNoSave(int hueBrightness) {
        if (hueBrightness > 100) hueBrightness = 100;
        if (hueBrightness < 0) hueBrightness = 0;
        this.hueBrightness = hueBrightness;
    }
}
