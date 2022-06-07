package suso.datareload.mixin.loader;

import net.minecraft.resource.DirectoryResourcePack;
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
            method = "findFiles",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;)V",
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void error(File file, String namespace, List<Identifier> found, String prefix, Predicate<String> pathFilter, CallbackInfo ci, File[] files, File[] var7, int var8, int var9, File file2, InvalidIdentifierException var13) {
        String[] split = var13.getMessage().split(":", 2);

        Text t = MutableText.of(new LiteralTextContent("\n"))
                .append(Utility.strToText("- " + split[0] + ":", Formatting.RED))
                .append(Utility.strToText(split[1], Formatting.AQUA));
        Utility.sendMessage(t);
    }
}
