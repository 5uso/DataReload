package suso.datareload.mixin.loader;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.FunctionLoader;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suso.datareload.Utility;

@Mixin(FunctionLoader.class)
public class FunctionMixin {
    @Inject(
            method = "method_29457",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    private static void error(Identifier id, ImmutableMap.Builder<Identifier, CommandFunction> immBuilder, CommandFunction commandFunction, Throwable ex, CallbackInfoReturnable<Object> ci) {
        Text t = MutableText.of(new LiteralTextContent("\n"))
                .append(Utility.strToText("- Failed to load function ", Formatting.RED))
                .append(Utility.strToText(id.toString(), Formatting.AQUA))
                .append(Utility.strToText("\n "))
                .append(Utility.strToText(Utility.removeEx(ex.getMessage())));
        Utility.sendMessage(t);
    }
}
