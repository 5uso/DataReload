package suso.datareload.mixin.loader;

import com.google.gson.JsonElement;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;

@Mixin(JsonDataLoader.class)
public class JsonDataMixin {
    @Inject(
            method = "Lnet/minecraft/resource/JsonDataLoader;prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)Ljava/util/Map;",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;[Ljava/lang/Object;)V",
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void parseError(ResourceManager resourceManager, Profiler profiler, CallbackInfoReturnable<Map<Identifier, JsonElement>> cir, Map<Identifier, JsonElement> map, ResourceFinder resourceFinder, Iterator<Map.Entry<Identifier, Resource>> var5, Map.Entry<Identifier, Resource> entry, Identifier identifier, Identifier identifier2, Exception var15) {
        Text t = MutableText.of(new LiteralTextContent("\n"))
                .append(Utility.strToText("- Couldn't parse data file ", Formatting.RED))
                .append(Utility.strToText(identifier2.toString(), Formatting.AQUA))
                .append(Utility.strToText(" from ", Formatting.RED))
                .append(Utility.strToText(identifier.toString(), Formatting.YELLOW))
                .append(Utility.strToText("\n "))
                .append(Utility.strToText(Utility.removeEx(var15.getMessage())));
        Utility.sendMessage(t);
    }
}
