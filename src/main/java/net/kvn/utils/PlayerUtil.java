package net.kvn.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

import static net.kvn.KvnUtilityMod.LOGGER;
import static net.kvn.KvnUtilityMod.mc;

public class PlayerUtil {

    //player pos
    public static BlockPos getPlayerPos() {
        return new BlockPos((int) Math.floor(mc.player.getX()), (int) Math.floor(mc.player.getY()), (int) Math.floor(mc.player.getZ()));
    }



    //send chat message
    public static void sendChatMsg(String msg) {
        try {
            MinecraftClient.getInstance().player.networkHandler.sendChatMessage(msg);
        } catch (Exception e) {
            LOGGER.error("Error while sending chat message: " + msg + " \nStacktrace: "  + e);
        }
    }

    //send command
    public static void sendCommand(String cmd) {
        try {
            MinecraftClient.getInstance().player.networkHandler.sendCommand(cmd);
        } catch (Exception e) {
            LOGGER.error("Error while sending command: " + cmd + " \nStacktrace: "  + e);
        }
    }
}
