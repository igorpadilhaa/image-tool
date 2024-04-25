package com.github.igorpadilhaa.imagetool.filter;

import com.github.igorpadilhaa.imagetool.Color;
import com.github.igorpadilhaa.imagetool.Image;

public class GrayscaleFilter implements ImageFilter {

    public void apply(Image img) {
        int[] pixels = img.pixels();
        for (int i = 0; i < pixels.length; i++) {
            Color color = Color.fromARGB(pixels[i]);
            int lum = color.luminosity();

            int grayscale = (0xFF << (8 * 3)) | (lum << (8 * 2)) | (lum << (8 * 1)) | lum;
            pixels[i] = grayscale;
        }
    }

}
