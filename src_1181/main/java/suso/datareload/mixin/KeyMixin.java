package suso.datareload.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderParseException;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
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
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(
                        new LiteralText("F3 + Y = Reload datapacks ")
                                .append(new LiteralText("[Fabric mod by Suso]").formatted(Formatting.AQUA)));
                return;

            case 'Y':
                MinecraftClient client = MinecraftClient.getInstance();
                Objects.requireNonNull(client.getNetworkHandler()).sendPacket(new ChatMessageC2SPacket("/reload"));
                cir.setReturnValue(true);

        }
    }
}
