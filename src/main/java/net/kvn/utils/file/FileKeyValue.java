package net.kvn.utils.file;

import java.util.ArrayList;

public class FileKeyValue {

    public static void saveValue(String key, String value, String filePath)
    {
        ArrayList<String> input = FileReader.readLines(filePath);
        String line = FileReader.getLineWithString(key, filePath);
        String keyValue = key + " = {" + value + "}";

        if (line.equals(""))
            input.add(keyValue);
        else
            input.set(input.indexOf(line), keyValue);

        FileWriter.writeLines(input, filePath);
    }

    public static String getValue(String key, String filePath)
    {
        String line = FileReader.getLineWithString(key, filePath);

        if (line.equals(""))
            return "";

        return line.substring(line.indexOf("{") + 1, line.indexOf("}"));
    }
}
