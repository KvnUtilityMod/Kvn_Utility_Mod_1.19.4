package net.kvn.utils.input;

import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyLib {

    public static String getKeyName(int key){
        if (key == 0)
            return "None";

        if (GLFW.glfwGetKeyName(key, 0) != null)
            return GLFW.glfwGetKeyName(key, 0);

        String s = InputUtil.fromKeyCode(key, 0).getTranslationKey();

        if (s == null)
            return "Key " + key;

        if (!s.contains(".") || s.charAt(s.length() - 1) == '.')
            return s;

        return s.substring(s.lastIndexOf(".") + 1);
    }
}
