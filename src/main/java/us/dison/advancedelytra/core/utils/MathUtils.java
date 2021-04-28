package us.dison.advancedelytra.core.utils;

public class MathUtils {
    public static float clamp01(float value) { return Math.min(Math.max(value, 0f), 1f); }
    public static double clamp01(double value) { return Math.min(Math.max(value, 0d), 1d); }

    public static float lerp(float a, float b, float time) { return a + (b - a) * clamp01(time); }
    public static double lerp(double a, double b, double time) { return a + (b - a) * clamp01(time); }

    public static float clampRotate(float value) {
        while (value > 360f) { value -= 360f; }
        while (value < 0f) { value += 360f; }
        return value;
    }
}
