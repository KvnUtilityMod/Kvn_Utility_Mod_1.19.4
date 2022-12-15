package net.kvn.utils.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    public static ArrayList<String> readLines(String filePath){
        ArrayList<String> linesFromFile = new ArrayList<String>();
        try{
            Scanner s = new Scanner(new File(filePath));
            while (s.hasNextLine()){
                linesFromFile.add(s.nextLine());
            }
            s.close();
            return linesFromFile;
        } catch (FileNotFoundException e) {
            FileUtil.createFile(filePath);
        }
        return linesFromFile;
    }

    public static String getLineWithString(String string, String filePath){
        try{
            Scanner s = new Scanner(new File(filePath));
            while (s.hasNextLine()){
                String line = s.nextLine();
                if (line.contains(string)){
                    return line;
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            return "";
        }
        return "";
    }
}
