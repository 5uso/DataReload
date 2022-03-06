package suso.datareload;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class Utility {

    public static MinecraftServer server;

    public static void sendMessage(Text t) {
        if(server != null && server.isRunning()) {
            for (ServerPlayerEntity player : Utility.server.getPlayerManager().getPlayerList()) {
                if (Utility.server.getPlayerManager().isOperator(player.getGameProfile())) {
                    player.sendSystemMessage(t, Util.NIL_UUID);
                }
            }
        }
    }

    public static String removeEx(String msg) {
        msg = msg.substring(msg.indexOf(": ") + 2);
        msg = msg.replace("Use JsonReader.setLenient(true) to accept m", "M");
        return msg;
    }

}
