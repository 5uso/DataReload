package suso.datareload.mixin.loader;

import com.google.gson.JsonElement;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import suso.datareload.Utility;

import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;

@Mixin(JsonDataLoader.class)
public class JsonDataMixin {
    @Inject(
            method = "prepare",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void parseError(ResourceManager resourceManager, Profiler profiler, CallbackInfoReturnable<Map<Identifier, JsonElement>> cir, Map<Identifier, JsonElement> map, int i, Iterator<Identifier> var5, Identifier identifier, String string, Identifier identifier2, Exception var20) {
        Text t = new LiteralText("\n")
                .append(new LiteralText("- Couldn't parse data file ").formatted(Formatting.RED))
                .append(new LiteralText(identifier2.toString()).formatted(Formatting.AQUA))
                .append(new LiteralText(" from ").formatted(Formatting.RED))
                .append(new LiteralText(identifier.toString()).formatted(Formatting.YELLOW))
                .append(new LiteralText("\n "))
                .append(new LiteralText(Utility.removeEx(var20.getMessage())));
        Utility.sendMessage(t);
    }

    @Inject(
            method = "prepare",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void emptyError(ResourceManager resourceManager, Profiler profiler, CallbackInfoReturnable<Map<Identifier, JsonElement>> cir, Map<Identifier, JsonElement> map, int i, Iterator<Identifier> var5, Identifier identifier, String string, Identifier identifier2, Resource resource, InputStream inputStream, Reader reader) {
        Text t = new LiteralText("\n")
                .append(new LiteralText("- Couldn't load data file ").formatted(Formatting.RED))
                .append(new LiteralText(identifier2.toString()).formatted(Formatting.AQUA))
                .append(new LiteralText(" from ").formatted(Formatting.RED))
                .append(new LiteralText(identifier.toString()).formatted(Formatting.YELLOW))
                .append(new LiteralText(" as it's null or empty").formatted(Formatting.RED));
        Utility.sendMessage(t);
    }
}
