package com.github.igorpadilhaa.imagetool.filter;

import com.github.igorpadilhaa.imagetool.Image;
import com.github.igorpadilhaa.imagetool.Color;

public class SobelFilter implements ImageFilter {

    final int[][] xWeights = {
            { 1, 0, -1 },
            { 2, 0, -2 },
            { 1, 0, -1 }
    };

    final int[][] yWeights = {
            { 1, 2, 1 },
            { 0, 0, 0 },
            { -1, -2, -1 },
    };

    @Override
    public void apply(Image img) {
        int[] pixels = img.pixels();
        int[] buffer = new int[pixels.length];

        float max = Integer.MIN_VALUE;

        for (int y = 0; y < img.height(); y++) {
            for (int x = 0; x < img.width(); x++) {

                int magX = 0;
                int magY = 0;

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int xi = x + i;
                        int yi = y + j;

                        if (xi < 0 || xi >= img.width() || yi < 0 || yi >= img.height())
                            continue;

                        int val = Color.fromARGB(pixels[yi * img.width() + xi]).luminosity();
                        int xg = val * xWeights[1 + i][1 + j];
                        int yg = val * yWeights[1 + i][1 + j];

                        magX += xg;
                        magY += yg;
                    }
                }

                int gradient = (int) Math.sqrt(magX * magX + magY * magY);
                if (gradient > max)
                    max = gradient;

                buffer[y * img.width() + x] = gradient;
            }
        }

        for (int i = 0; i < buffer.length; i++) {
            int gradient = (int)(buffer[i] / max * 255);

            pixels[i] = 0xFF << (8 * 3) | gradient << (8 * 2) | gradient << (8 * 1) | gradient;
        }
    }

}
