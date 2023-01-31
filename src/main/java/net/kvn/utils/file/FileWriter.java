package net.kvn.utils.file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileWriter {

    //write arraylist to file
    public static void writeLines(ArrayList<String> lines, String filePath){
        try{
            writeLineToFileThrowsError(lines, filePath);
        } catch (FileNotFoundException e) {
            FileUtil.createFile(filePath);
            try {
                writeLineToFileThrowsError(lines, filePath);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void writeLineToFileThrowsError(ArrayList<String> lines, String filePath) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filePath);
        for (String line : lines){
            writer.println(line);
        }
        writer.close();
    }

    //append line to file
    public static void appendLineToFile(String line, String filePath){
        try{
            appendLineToFileThrowsError(line, filePath);
        } catch (FileNotFoundException e) {
            FileUtil.createFile(filePath);
            try {
                appendLineToFileThrowsError(line, filePath);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void appendLineToFileThrowsError(String line, String filePath) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filePath);
        writer.println(line);
        writer.close();
    }
}
