package suso.datareload.mixin.loader;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import suso.datareload.Utility;

@Mixin(LootManager.class)
public class LootMixin {
    @Inject(
            method = "method_20711",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private static void error(ImmutableMap.Builder<Identifier, LootTable> builder, Identifier id, JsonElement json, CallbackInfo ci, Exception var4) {
        Text t = MutableText.of(new LiteralTextContent("\n"))
                .append(Utility.strToText("- Couldn't parse loot table ", Formatting.RED))
                .append(Utility.strToText(id.toString(), Formatting.AQUA))
                .append(Utility.strToText("\n "))
                .append(Utility.strToText(var4.getMessage()));
        Utility.sendMessage(t);
    }
}
