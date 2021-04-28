package us.dison.advancedelytra.core.utils;

import net.minecraft.util.math.Vec2f;

public class Vec2fUtils {
    public static Vec2f rotate(Vec2f vec, float degrees) {
        double radians = Math.toRadians(degrees);
        float sin = (float) Math.sin(radians);
        float cos = (float) Math.cos(radians);

        return new Vec2f((cos * vec.x) - (sin * vec.y), (sin * vec.x) + (cos * vec.y));
    }
}
