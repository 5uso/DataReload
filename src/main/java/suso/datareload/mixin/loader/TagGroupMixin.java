package suso.datareload.mixin.loader;

import com.google.gson.JsonObject;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import suso.datareload.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Mixin(TagGroupLoader.class)
public class TagGroupMixin {
    @Inject(
            method = "loadTags",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false,
                    ordinal = 0
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void emptyError(ResourceManager manager, CallbackInfoReturnable<Map<Identifier, Tag.Builder>> cir, Map<Identifier, Tag.Builder> map, Iterator<Identifier> var3, Identifier identifier, String string, Identifier identifier2, Iterator<Resource> var7, Resource resource, InputStream inputStream, Reader reader, JsonObject jsonObject) {
        Text t = new LiteralText("\n")
                .append(new LiteralText("- Couldn't read tag list ").formatted(Formatting.RED))
                .append(new LiteralText(identifier2.toString()).formatted(Formatting.AQUA))
                .append(new LiteralText(" from ").formatted(Formatting.RED))
                .append(new LiteralText(identifier.toString()).formatted(Formatting.YELLOW))
                .append(new LiteralText(" in data pack ").formatted(Formatting.RED))
                .append(new LiteralText(resource.getResourcePackName()).formatted(Formatting.YELLOW))
                .append(new LiteralText(" as it is empty or null").formatted(Formatting.RED));
        Utility.sendMessage(t);
    }

    @Inject(
            method = "loadTags",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void jsonError(ResourceManager manager, CallbackInfoReturnable<Map<Identifier, Tag.Builder>> cir, Map<Identifier, Tag.Builder> map, Iterator<Identifier> var3, Identifier identifier, String string, Identifier identifier2, Iterator<Resource> var7, Resource resource, Exception var25) {
        Text t = new LiteralText("\n")
                .append(new LiteralText("- Couldn't read tag list ").formatted(Formatting.RED))
                .append(new LiteralText(identifier2.toString()).formatted(Formatting.AQUA))
                .append(new LiteralText(" from ").formatted(Formatting.RED))
                .append(new LiteralText(identifier.toString()).formatted(Formatting.YELLOW))
                .append(new LiteralText(" in data pack ").formatted(Formatting.RED))
                .append(new LiteralText(resource.getResourcePackName()).formatted(Formatting.YELLOW))
                .append(new LiteralText("\n "))
                .append(new LiteralText(Utility.removeEx(var25.getMessage())));
        Utility.sendMessage(t);
    }

    @Inject(
            method = "loadTags",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false,
                    ordinal = 1
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void otherError(ResourceManager manager, CallbackInfoReturnable<Map<Identifier, Tag.Builder>> cir, Map<Identifier, Tag.Builder> map, Iterator<Identifier> var3, Identifier identifier, String string, Identifier identifier2, IOException var27) {
        Text t = new LiteralText("\n")
                .append(new LiteralText("- Couldn't read tag list ").formatted(Formatting.RED))
                .append(new LiteralText(identifier2.toString()).formatted(Formatting.AQUA))
                .append(new LiteralText(" from ").formatted(Formatting.RED))
                .append(new LiteralText(identifier.toString()).formatted(Formatting.YELLOW));
        Utility.sendMessage(t);
    }

    @Inject(
            method = "method_33175",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    private static void referenceError(Identifier id, Collection<Object> collection, CallbackInfo ci) {
        Text t = new LiteralText("\n")
                .append(new LiteralText("- Couldn't load tag ").formatted(Formatting.RED))
                .append(new LiteralText(id.toString()).formatted(Formatting.AQUA))
                .append(new LiteralText(" as it is missing following references: ").formatted(Formatting.RED))
                .append(new LiteralText(collection.stream().map(Objects::toString).collect(Collectors.joining(","))).formatted(Formatting.YELLOW));
        Utility.sendMessage(t);
    }
}
