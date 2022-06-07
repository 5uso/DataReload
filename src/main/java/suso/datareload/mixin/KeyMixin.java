package suso.datareload.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Keyboard.class)
public class KeyMixin {
    @Inject(at = @At("RETURN"), method = "processF3", cancellable = true)
    public void inputReload(int key, CallbackInfoReturnable<Boolean> cir) {
        switch (key) {

            case 'Q':
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(MutableText.of(new LiteralTextContent("F3 + Y = Reload datapacks")));
                return;

            case 'Y':
                MinecraftClient client = MinecraftClient.getInstance();
                Objects.requireNonNull(client.player).sendCommand("reload", MutableText.of(new LiteralTextContent("")));
                cir.setReturnValue(true);

        }
    }
}
