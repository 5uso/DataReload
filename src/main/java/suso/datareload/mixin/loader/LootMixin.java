package suso.datareload.mixin.loader;

import net.minecraft.registry.ReloadableRegistries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import suso.datareload.Utility;

@Mixin(ReloadableRegistries.class)
public class LootMixin {
    @Inject(
            method = "method_58283",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    private static void error(String name, String message, CallbackInfo ci) {
        Text t = Text.literal("\n")
                .append(Utility.strToText("- Validation problem in loot table ", Formatting.RED))
                .append(Utility.strToText(name, Formatting.AQUA))
                .append(Utility.strToText("\n "))
                .append(Utility.strToText(message));
        Utility.sendMessage(t);
    }
}
