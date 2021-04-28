package us.dison.advancedelytra.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import us.dison.advancedelytra.core.callbacks.CameraUpdateCallback;
import us.dison.advancedelytra.core.callbacks.ModifyCameraTransformCallback;
import us.dison.advancedelytra.core.structures.Transform;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow abstract float getYaw();
    @Shadow abstract float getPitch();
    @Shadow abstract Vec3d getPos();
    @Shadow abstract void setRotation(float yaw, float pitch);

    @Inject(at = @At("RETURN"), method = "update")
    private void OnCameraUpdate(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        Transform cameraTransform = new Transform(getPos(), new Vec3d(getPitch(), getYaw(), 0d));

        CameraUpdateCallback.EVENT.invoker().OnCameraUpdate((Camera)(Object)this, cameraTransform, tickDelta);

        cameraTransform = ModifyCameraTransformCallback.EVENT.invoker().ModifyCameraTransform((Camera)(Object)this, cameraTransform);

        setRotation((float)cameraTransform.eulerRot.y, (float)cameraTransform.eulerRot.x);
    }
}
