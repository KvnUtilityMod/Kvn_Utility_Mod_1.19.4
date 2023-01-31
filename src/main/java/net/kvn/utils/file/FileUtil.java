package net.kvn.utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static String getKvnFolder(){
        int i = 0;
        StringBuilder result = new StringBuilder();
        String fullPath = System.getProperty("user.dir");

        while (!result.toString().contains(".minecraft") && i < fullPath.length()){
            result.append(fullPath.charAt(i));
            i++;
        }

        return result.toString() + File.separator + "kvn" + File.separator;
    }

    public static String getKvnFolderWithoutSeparator(){
        int i = 0;
        StringBuilder result = new StringBuilder();
        String fullPath = System.getProperty("user.dir");

        while (!result.toString().contains(".minecraft") && i < fullPath.length()){
            result.append(fullPath.charAt(i));
            i++;
        }

        return result.toString() + File.separator + "kvn";
    }

    public static boolean doesFileExist(String filePath){
        File file = new File(filePath);
        return file.exists();
    }

    public static void createFile(String filePath){
        File myObj = new File(filePath);
        try {
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println(filePath);
            e.printStackTrace();
        }
    }

    public static void deleteFile(String filepath){
        File myObj = new File(filepath);
        myObj.delete();
    }

    public static void createDir(String dirPath){
        if (!Files.isDirectory(Paths.get(dirPath))){
            try {
                Files.createDirectory(Paths.get(dirPath));
            } catch (IOException e){
            }
        }
    }
}
