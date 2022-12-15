package net.kvn.utils.input;

import java.util.ArrayList;

public class TextUtil {

    public static int getIndex(String str, ArrayList<String> strings){
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).contains(str)) return i;
        }
        return -1;
    }
}
