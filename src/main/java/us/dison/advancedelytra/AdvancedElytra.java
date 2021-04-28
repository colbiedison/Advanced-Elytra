package us.dison.advancedelytra;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancedElytra {
    public static Logger Logger = LogManager.getLogger("Cameratest");

    public static AdvancedElytra instance;

    public CameraSystem cameraSystem;

    public void onInitializeClient() {
        cameraSystem = new CameraSystem();
    }
}
