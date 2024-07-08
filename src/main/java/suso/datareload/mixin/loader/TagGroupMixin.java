package suso.datareload.mixin.loader;

import net.minecraft.registry.tag.TagGroupLoader;
import net.minecraft.resource.Resource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import suso.datareload.Utility;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(TagGroupLoader.class)
public class TagGroupMixin {

    @ModifyArg(
            method = "loadTags",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;[Ljava/lang/Object;)V",
                    remap = false,
                    ordinal = 0
            ),
            index = 1
    )
    public Object[] jsonError(Object[] args) {
        Object identifier2 = args[0];
        Object identifier = args[1];
        String packId = (String)args[2];
        Exception var17 = (Exception)args[3];
        Text t = Text.literal("\n")
                .append(Utility.strToText("- Couldn't read tag list ", Formatting.RED))
                .append(Utility.strToText(identifier2.toString(), Formatting.AQUA))
                .append(Utility.strToText(" from ", Formatting.RED))
                .append(Utility.strToText(identifier.toString(), Formatting.YELLOW))
                .append(Utility.strToText(" in data pack ", Formatting.RED))
                .append(Utility.strToText(packId, Formatting.YELLOW))
                .append(Utility.strToText("\n "))
                .append(Utility.strToText(Utility.removeEx(var17.getMessage())));
        Utility.sendMessage(t);
        return args;
    }

    @Inject(
            method = "method_33175",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    private static void referenceError(Identifier id, Collection<Object> collection, CallbackInfo ci) {
        Text t = Text.literal("\n")
                .append(Utility.strToText("- Couldn't load tag ", Formatting.RED))
                .append(Utility.strToText(id.toString(), Formatting.AQUA))
                .append(Utility.strToText(" as it is missing following references: ", Formatting.RED))
                .append(Utility.strToText(collection.stream().map(Objects::toString).collect(Collectors.joining(",")), Formatting.YELLOW));
        Utility.sendMessage(t);
    }
}
