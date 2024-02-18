package suso.datareload.mixin;

import com.mojang.datafixers.DataFixer;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.ApiServices;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import suso.datareload.Utility;

import java.net.Proxy;

@Mixin(MinecraftServer.class)
public abstract class ServerMixin {
    @Shadow public abstract ServerCommandSource getCommandSource();

    @Inject(at = @At("RETURN"), method = "<init>")
    public void getServer(Thread serverThread, LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, Proxy proxy, DataFixer dataFixer, ApiServices apiServices, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {
        Utility.server = this.getCommandSource().getServer();
        System.out.println("[Data Reload] Got server: " + Utility.server.getClass().getSimpleName());
    }
}
