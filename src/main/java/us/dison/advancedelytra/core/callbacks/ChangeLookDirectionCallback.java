package us.dison.advancedelytra.core.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ChangeLookDirectionCallback {
    Event<ChangeLookDirectionCallback> EVENT = EventFactory.createArrayBacked(ChangeLookDirectionCallback.class,
        (listeners) -> (x, y) -> {
            for (ChangeLookDirectionCallback listener : listeners) {
                double[] modified = listener.ChangeLookDirection(x, y);
                x = modified[0];
                y = modified[1];
            }
            return new double[]{x, y};
        }
    );

    double[] ChangeLookDirection(double x, double y);
}
