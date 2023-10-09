package net.kvn.utils.input;

import net.kvn.utils.world.BlockUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import java.util.ArrayList;
import java.util.Map;

public class TextUtil {

    public static int getInt(String str){
        try{
            return Integer.parseInt(str);
        } catch (Exception e){
            return 0;
        }
    }

    public static int getIndex(String str, ArrayList<String> strings){
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).contains(str)) return i;
        }
        return -1;
    }

    public static Map<String, Integer> getBlockScores(String str) {
        Map<String, Integer> scores = new java.util.HashMap<>();
        for (Block b : BlockUtil.getAllBlocks()) {
            String blockName = b.getTranslationKey().substring(b.getTranslationKey().lastIndexOf(".") + 1);
            scores.put(blockName, getSimilarity(blockName, str));
        }

        return scores;
    }

    public static int getSimilarity(String str, Block block){
        String blockName = block.getTranslationKey().substring(block.getTranslationKey().lastIndexOf(".") + 1);
        return getSimilarity(str, blockName);
    }

    public static int getSimilarity(String fullStr, String inputStr) {
        // Return 0 if either string is null or empty
        if (fullStr == null || inputStr == null || fullStr.length() == 0 || inputStr.length() == 0) {
            return 0;
        }

        // Convert the strings to lowercase for case-insensitivity
        fullStr = fullStr.toLowerCase();
        inputStr = inputStr.toLowerCase();

        // Initialize a 2D array to store the dynamic programming values
        int[][] dp = new int[fullStr.length() + 1][inputStr.length() + 1];

        // Initialize the first row and column to represent the distance from an empty string
        for (int i = 0; i <= fullStr.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= inputStr.length(); j++) {
            dp[0][j] = j;
        }

        // Iterate through the 2D array and fill in the values using the Levenshtein distance algorithm
        for (int i = 1; i <= fullStr.length(); i++) {
            for (int j = 1; j <= inputStr.length(); j++) {
                // If the characters at the current indices are the same, the distance is the same as the distance for the previous characters
                if (fullStr.charAt(i - 1) == inputStr.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Otherwise, the distance is the minimum of the distance from deleting, inserting, or replacing a character
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                }
            }
        }

        // The final value in the 2D array is the Levenshtein distance between the two strings
        int distance = dp[fullStr.length()][inputStr.length()];

        // Calculate the similarity score as 1 - (distance / max(fullStr.length(), inputStr.length()))
        int maxLength = Math.max(fullStr.length(), inputStr.length());
        int similarity = (int) (100 * (1.0 - (double) distance / maxLength));

        return similarity;
    }
}
