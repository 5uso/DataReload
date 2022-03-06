package suso.datareload.mixin.loader;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import suso.datareload.Utility;

@Mixin(LootConditionManager.class)
public class LootConditionMixin {
    @Inject(
            method = "method_22560",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private static void error(ImmutableMap.Builder<Identifier, LootCondition> builder, Identifier id, JsonElement json, CallbackInfo ci, Exception var4) {
        Text t = new LiteralText("\n")
                .append(new LiteralText("- Couldn't parse loot table ").formatted(Formatting.RED))
                .append(new LiteralText(id.toString()).formatted(Formatting.AQUA))
                .append(new LiteralText("\n "))
                .append(new LiteralText(var4.getMessage()));
        Utility.sendMessage(t);
    }
}
