package suso.datareload;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Utility {

    public static MinecraftServer server;

    public static void sendMessage(Text t) {
        if(server != null && server.isRunning()) {
            for (ServerPlayerEntity player : Utility.server.getPlayerManager().getPlayerList()) {
                if (Utility.server.getPlayerManager().isOperator(player.getGameProfile())) {
                    player.sendMessage(t);
                }
            }
        }
    }

    public static String removeEx(String msg) {
        while(true) {
            final var colonIndex = msg.indexOf(": ");
            if(colonIndex == -1)
                break;
            if(msg.substring(0, colonIndex).contains(" ")) {
                // Definitely not an error name, so it shouldn't be removed
                break;
            }
            msg = msg.substring(colonIndex + 2);
        }
        msg = msg.replace("Use JsonReader.setLenient(true) to accept m", "M");
        return msg;
    }

    public static MutableText strToText(String str, Formatting... format) {
        return Text.literal(str).formatted(format);
    }

}
