package net.kvn.utils;

import net.minecraft.util.math.BlockPos;

import static net.kvn.KvnUtilityMod.LOGGER;
import static net.kvn.KvnUtilityMod.mc;

public class PlayerUtil {

    //player pos
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.getX()), Math.floor(mc.player.getY()), Math.floor(mc.player.getZ()));
    }

    //send chat message
    public static void sendChatMsg(String msg) {
        try {
            mc.player.sendChatMessage(msg, null);
        } catch (Exception e) {
            LOGGER.error("Error while sending chat message: " + msg + " \nStacktrace: "  + e);
        }
    }

    //send command
    public static void sendCommand(String cmd) {
        try {
            mc.player.sendCommand(cmd);
        } catch (Exception e) {
            LOGGER.error("Error while sending command: " + cmd + " \nStacktrace: "  + e);
        }
    }
}
