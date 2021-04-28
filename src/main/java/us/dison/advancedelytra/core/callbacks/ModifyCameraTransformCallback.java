package us.dison.advancedelytra.core.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.Camera;
import us.dison.advancedelytra.core.structures.Transform;

public interface ModifyCameraTransformCallback
{
    Event<ModifyCameraTransformCallback> EVENT = EventFactory.createArrayBacked(ModifyCameraTransformCallback.class,
            (listeners) -> (camera, transform) -> {
                for(ModifyCameraTransformCallback listener : listeners) {
                    transform = listener.ModifyCameraTransform(camera, transform);
                }

                return transform;
            }
    );

    Transform ModifyCameraTransform(Camera camera, Transform transform);
}