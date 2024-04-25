package com.github.igorpadilhaa.imagetool;

public class Color {

    private int data;

    public static Color fromARGB(int argb) {
        Color color = new Color();
        color.data = argb;

        return color;
    }

    public static Color fromARGB(float[] argb) {
        Color color = new Color();
        
        int a, r, g, b;
        a = (int)(argb[0] * 255);
        r = (int)(argb[1] * 255);
        g = (int)(argb[2] * 255);
        b = (int)(argb[3] * 255);

        color.data = a | r | g | b;
        return color;
    }

    public String toString() {
        String rgbStr = "rgba(%d, %d, %d, %d)";

        int r, g, b, a;
        a = (data >> (8 * 3)) & 0xFF;
        r = (data >> (8 * 2)) & 0xFF;
        g = (data >> (8 * 1)) & 0xFF;
        b = (data >> (8 * 0)) & 0xFF;

        return rgbStr.formatted(r, g, b, a);
    }

    public float[] splitARGB() {
        float r, g, b, a;
        
        a = ((data >> (8 * 3)) & 0XFF) / 255f;
        r = ((data >> (8 * 2)) & 0XFF) / 255f;
        g = ((data >> (8 * 1)) & 0XFF) / 255f;
        b = ((data >> (8 * 0)) & 0XFF) / 255f;

        return new float[]{a, r, g, b};
    }

    public int luminosity() {
        int r, g, b;
        // 0.2989, 0.5870, 0.1140.
        r = (int) ((data >> 2 * 8 & 0xFF) * 0.2989f);
        g = (int) ((data >> 1 * 8 & 0xFF) * 0.587f);
        b = (int) ((data >> 0 * 8 & 0xFF) * 0.114f);

        int l = (r + g + b) & 0xFF;
        return l;
    }
}
