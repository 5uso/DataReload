package suso.datareload.mixin.loader;

import net.minecraft.resource.JsonDataLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import suso.datareload.Utility;

@Mixin(JsonDataLoader.class)
public class JsonDataMixin {
    @ModifyArg(
            method = "load",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;[Ljava/lang/Object;)V",
                    remap = false
            ),
            index = 1
    )
    private static Object[] parseError(Object[] args) {
        Object identifier = args[0];
        Object identifier2 = args[1];
        Exception var14 = (Exception)args[2];
        Text t = Text.literal("\n")
                .append(Utility.strToText("- Couldn't parse data file ", Formatting.RED))
                .append(Utility.strToText(identifier2.toString(), Formatting.AQUA))
                .append(Utility.strToText(" from ", Formatting.RED))
                .append(Utility.strToText(identifier.toString(), Formatting.YELLOW))
                .append(Utility.strToText("\n "))
                .append(Utility.strToText(Utility.removeEx(var14.getMessage())));
        Utility.sendMessage(t);
        return args;
    }
}
