package us.dison.advancedelytra.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import us.dison.advancedelytra.core.callbacks.ChangeLookDirectionCallback;

@Mixin(Mouse.class)
public class MouseMixin {
    @ModifyArgs(method = "updateMouse()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"
            )
    )
    private void changeLookDirection(Args args) {
        double x = args.get(0);
        double y = args.get(1);

        double[] xy = ChangeLookDirectionCallback.EVENT.invoker().ChangeLookDirection(x, y);

        args.set(0, xy[0]);
        args.set(1, xy[1]);
    }
}
