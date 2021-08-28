package suso.datareload.mixin.loader;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import net.minecraft.loot.LootTable;
import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.text.LiteralText;
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
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;)V",
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void error(File file, int maxDepth, String namespace, List<Identifier> found, String prefix, Predicate<String> pathFilter, CallbackInfo ci, File[] files, File[] var8, int var9, int var10, InvalidIdentifierException var13) {
        String[] split = var13.getMessage().split(":", 2);

        Text t = new LiteralText("\n")
                .append(new LiteralText("- " + split[0] + ":").formatted(Formatting.RED))
                .append(new LiteralText(split[1]).formatted(Formatting.AQUA));
        Utility.sendMessage(t);
    }
}
