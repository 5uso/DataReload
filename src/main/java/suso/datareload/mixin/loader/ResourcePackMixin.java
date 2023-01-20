package suso.datareload.mixin.loader;

import com.mojang.serialization.DataResult;
import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import suso.datareload.Utility;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

@Mixin(DirectoryResourcePack.class)
public class ResourcePackMixin {
    @Inject(
            method = "method_45182",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private static void error(String prefix, DataResult.PartialResult<List<String>> result, CallbackInfo ci) {
        Text t = MutableText.of(new LiteralTextContent("\n"))
                .append(Utility.strToText("- Invalid path" + prefix + ":", Formatting.RED))
                .append(Utility.strToText(result.message(), Formatting.AQUA));
        Utility.sendMessage(t);
    }
}
