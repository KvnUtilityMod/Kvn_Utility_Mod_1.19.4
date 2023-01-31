package net.kvn.utils.render;

import java.awt.*;

public class ColorUtils {

    public static Color getFullAlpha(Color color){
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
    }
}
