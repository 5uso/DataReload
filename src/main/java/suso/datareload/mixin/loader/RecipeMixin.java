package suso.datareload.mixin.loader;

import net.minecraft.recipe.RecipeManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import suso.datareload.Utility;

@Mixin(RecipeManager.class)
public class RecipeMixin {
    @ModifyArg(
            method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            ),
            index = 1
    )
    public Object error(String format, Object identifier, Object var9) {
        Text t = Text.literal("\n")
                .append(Utility.strToText("- Parsing error loading recipe ", Formatting.RED))
                .append(Utility.strToText(identifier.toString(), Formatting.AQUA))
                .append(Utility.strToText("\n "))
                .append(Utility.strToText(((Exception)var9).getMessage()));
        Utility.sendMessage(t);
        return identifier;
    }
}
