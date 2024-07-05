package suso.datareload.mixin.loader;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import net.minecraft.registry.RegistryOps;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import suso.datareload.Utility;

@Mixin(ServerAdvancementLoader.class)
public class ServerAdvancementMixin {
    @ModifyVariable(
            method = "method_20723",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    public Exception error(Exception var6, RegistryOps<JsonElement> ops, ImmutableMap.Builder<Identifier, JsonElement> map, Identifier id, JsonElement json) {
        Text t = Text.literal("\n")
                .append(Utility.strToText("- Parsing error loading custom advancement ", Formatting.RED))
                .append(Utility.strToText(id.toString(), Formatting.AQUA))
                .append(Utility.strToText("\n "))
                .append(Utility.strToText(var6.getMessage()));
        Utility.sendMessage(t);
        return var6;
    }
}
