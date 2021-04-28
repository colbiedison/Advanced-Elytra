package us.dison.advancedelytra.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import us.dison.advancedelytra.AdvancedElytra;

@Environment(EnvType.CLIENT)
public class FabricClientModInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        if (AdvancedElytra.instance == null) {
            AdvancedElytra.instance = new AdvancedElytra();
        }

        AdvancedElytra.instance.onInitializeClient();
    }
}
