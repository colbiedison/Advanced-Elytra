package us.dison.advancedelytra.core.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.Camera;
import us.dison.advancedelytra.core.structures.Transform;

public interface CameraUpdateCallback {

    Event<CameraUpdateCallback> EVENT = EventFactory.createArrayBacked(CameraUpdateCallback.class,
        (listeners) -> (camera, transform, tickDelta) -> {
            for (CameraUpdateCallback listener : listeners) {
                listener.OnCameraUpdate(camera, transform, tickDelta);
            }
        }
    );

    void OnCameraUpdate(Camera camera, Transform transform, float tickDelta);
}
