package us.dison.advancedelytra;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import us.dison.advancedelytra.core.callbacks.CameraUpdateCallback;
import us.dison.advancedelytra.core.callbacks.ChangeLookDirectionCallback;
import us.dison.advancedelytra.core.callbacks.ModifyCameraTransformCallback;
import us.dison.advancedelytra.core.structures.Transform;
import us.dison.advancedelytra.core.utils.MathUtils;
import us.dison.advancedelytra.core.utils.Vec2fUtils;

public final class CameraSystem implements CameraUpdateCallback, ModifyCameraTransformCallback, ChangeLookDirectionCallback {
    public static double rollOffset;
    public static double yawOffset;

    private static double prevStrafingRollOffset;
    private static Entity focusedEntity;

    private static final double rollSensitivity = 1.25d;
    private static final double pitchSensitivity = 1.25d;

    private static Transform offsetTransform = new Transform();

    public CameraSystem() {
        CameraUpdateCallback.EVENT.register(this);
        ModifyCameraTransformCallback.EVENT.register(this);
        ChangeLookDirectionCallback.EVENT.register(this);

        AdvancedElytra.Logger.info("Cameratest is ready");
    }

    @Override
    public void OnCameraUpdate(Camera camera, Transform cameraTransform, float tickDelta) {
        offsetTransform.position = new Vec3d(0d, 0d, 0d);
        offsetTransform.eulerRot = new Vec3d(0d, 0d, 0d);

        focusedEntity = camera.getFocusedEntity();

        Vec3d velocity = focusedEntity.getVelocity();
        Vec2f relativeXZVelocity = Vec2fUtils.rotate(
                new Vec2f((float)velocity.x, (float)velocity.z),
                360f - (float)cameraTransform.eulerRot.y
            );

        double strafingRollOffset = -relativeXZVelocity.x * 20d;

        prevStrafingRollOffset = strafingRollOffset = MathUtils.lerp(prevStrafingRollOffset, strafingRollOffset, tickDelta);

//        offsetTransform.position = new Vec3d(0d, 0d, 0d);
//        offsetTransform.eulerRot = new Vec3d(0d, 0d, strafingRollOffset);

        if (focusedEntity instanceof PlayerEntity) {
            offsetTransform.eulerRot = new Vec3d(0d, 0d,
                    ((PlayerEntity) focusedEntity).isFallFlying() ?
                            -offsetTransform.eulerRot.z :
                            offsetTransform.eulerRot.z
            );
        }
        offsetTransform.eulerRot = new Vec3d(0d, 0d, offsetTransform.eulerRot.z + rollOffset);


        yawOffset += (
                Math.sin(Math.toRadians(rollOffset)) * 2*((relativeXZVelocity.y/1.5) * (Math.abs(relativeXZVelocity.y)/1.5))
                * -Math.cos(Math.toRadians(focusedEntity.getPitch(1f)*2f + 180f))
        );
    }

    @Override
    public Transform ModifyCameraTransform(Camera camera, Transform transform) {
        return new Transform(
            transform.position.add(offsetTransform.position),
            transform.eulerRot.add(offsetTransform.eulerRot)
        );
    }

    @Override
    public double[] ChangeLookDirection(double x, double y) {
        if (focusedEntity instanceof PlayerEntity) {
            if (((PlayerEntity) focusedEntity).isFallFlying()) {
                x *= rollSensitivity;
                y *= pitchSensitivity;

                rollOffset += x/16d;

                double cpeHeadPitch = ((ClientPlayerEntity) focusedEntity).getPitch(1f);

                x = y * Math.sin(Math.toRadians(-rollOffset));
                y = y * Math.cos(Math.toRadians(-rollOffset));

                rollOffset += -x/6d * Math.sin(Math.toRadians(cpeHeadPitch));

                x += yawOffset;
//                System.out.println(Math.cos(Math.toRadians(focusedEntity.getPitch(1f)*2f + 180f)));

                if (
                        focusedEntity.getPitch(1.0f) + y > 90.0f ||
                        focusedEntity.getPitch(1.0f) + y < -90.0f
                ) {
                    double cpeHeadYaw = ((ClientPlayerEntity) focusedEntity).getHeadYaw();
                    double cpeYaw = ((ClientPlayerEntity) focusedEntity).getYaw(1f);

                    float newYaw = (float) ((cpeHeadYaw + 180f) % 360f);

                    ((ClientPlayerEntity) focusedEntity).prevYaw = newYaw;
                    ((ClientPlayerEntity) focusedEntity).prevHeadYaw = newYaw;
//
                    ((ClientPlayerEntity) focusedEntity).yaw = newYaw;
                    ((ClientPlayerEntity) focusedEntity).headYaw = newYaw;

                    rollOffset += 180d;

                }

                yawOffset = 0d;

                // Clamp rollOffset between 0 and 360
                rollOffset = rollOffset < 0 ?
                        rollOffset + 360 :
                        rollOffset > 360 ?
                                rollOffset - 360 :
                                rollOffset
                ;

            } else {
                rollOffset = 0;
            }
        }


        return new double[]{x, y};
    }
}
