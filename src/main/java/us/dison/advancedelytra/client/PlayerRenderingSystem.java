package us.dison.advancedelytra.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import us.dison.advancedelytra.CameraSystem;
import us.dison.advancedelytra.core.utils.MathUtils;

public class PlayerRenderingSystem {
    public static Args render(Args args) {
        AbstractClientPlayerEntity abstractClientPlayerEntity = args.get(0);
        MatrixStack matrixStack = args.get(3);

        float yaw = MathUtils.clampRotate(abstractClientPlayerEntity.getYaw(1f))-180f;
        float pitch = abstractClientPlayerEntity.getPitch(1f);
        float roll = (float) CameraSystem.rollOffset;

        float x = yaw < 0 ? -1-(Math.abs(-90-yaw) / -90) : 1-(Math.abs(90-yaw) / 90);
        float y = pitch / -90;
        float z = -((( 1 - (Math.abs(yaw) / 180f)) * 2) - 1);

        Vector3f aim = new Vector3f(x,y,z);
        aim.normalize();
//        System.out.println(aim);

//        while (yawFactor < 0f) { yawFactor += 1f; }
//        while (yawFactor > 1f) { yawFactor -= 1f; }

//        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((float) ((roll) * -Math.sin(Math.toRadians(yaw))))); // 0, -90 (horizontal, east +x)
//        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float) (roll * Math.cos(Math.toRadians(pitch+90))))); // 90, * (up)
//        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float) ((roll) * Math.cos(Math.toRadians(yaw))))); // 0, 0 (horizonal, south -z)

        matrixStack.multiply(aim.getDegreesQuaternion(roll));

//        matrixStack.multiply(new Quaternion(0f,0f,1f,15f));

        args.set(3, matrixStack);

        return args;
    }
}
