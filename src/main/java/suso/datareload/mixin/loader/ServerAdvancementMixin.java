package suso.datareload.mixin.loader;

import com.google.gson.JsonElement;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import suso.datareload.Utility;

import java.util.Map;

@Mixin(ServerAdvancementLoader.class)
public class ServerAdvancementMixin {
    @Inject(
            method = "method_20723",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void error(Map<Identifier, JsonElement> map, Identifier id, JsonElement json, CallbackInfo ci, Exception var6) {
        Text t = new LiteralText("\n")
                .append(new LiteralText("- Parsing error loading custom advancement ").formatted(Formatting.RED))
                .append(new LiteralText(id.toString()).formatted(Formatting.AQUA))
                .append(new LiteralText("\n "))
                .append(new LiteralText(var6.getMessage()));
        Utility.sendMessage(t);
    }
}
