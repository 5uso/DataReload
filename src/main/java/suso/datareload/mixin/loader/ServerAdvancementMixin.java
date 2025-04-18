package suso.datareload.mixin.loader;

import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import suso.datareload.Utility;

@Mixin(ServerAdvancementLoader.class)
public class ServerAdvancementMixin {
    @Inject(
            method = "method_54922",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    private static void validationWarning(Identifier id, String error, CallbackInfo ci) {
        Text t = Text.literal("\n")
                .append(Utility.strToText("- Validation problem in loot table ", Formatting.RED))
                .append(Utility.strToText(id.toString(), Formatting.AQUA))
                .append(Utility.strToText("\n "))
                .append(Utility.strToText(error));
        Utility.sendMessage(t);
    }
}
