package suso.datareload.client;

import net.fabricmc.api.ClientModInitializer;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class DatareloadClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("Suso's data reload is installed! Use with F3+Y");
    }
}
