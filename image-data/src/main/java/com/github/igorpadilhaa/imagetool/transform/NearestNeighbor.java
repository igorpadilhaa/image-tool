package com.github.igorpadilhaa.imagetool.transform;

import com.github.igorpadilhaa.imagetool.Image;

public class NearestNeighbor {

    public Image scale(Image image, float scale) {
        if (scale < 0)
            throw new IllegalArgumentException("scale must be a value greater or equal than zero");
        
        if (image == null)
            throw new IllegalArgumentException("invalid image value, image must be non-null");

        Image newImage = Image.create((int)(image.width() * scale), (int)(image.height() * scale));
        
        int[] original = image.pixels();
        int[] buffer = newImage.pixels();

        for (int y = 0; y < newImage.height(); y++) {
            for (int x = 0; x < newImage.width(); x++) {
                int color = 0xffff00ff;

                int ox, oy;
                ox = (int)(x/scale);
                oy = (int)(y/scale);
                color = original[oy * image.width() + ox];

                buffer[y * newImage.width() + x] = color;
            }
        }

        return newImage;
    }

    public Image rotate(Image image, float degrees) {
        int[] pixels = image.pixels();
        int[] buffer = new int[0];

        float angle = (float)Math.toRadians(degrees);

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                int color = pixels[y * image.width() + x];

                
              //  int ty = (int)(Math.tan(angle)* x);

            //    buffer[ty * image.height() + tx] = color;
            }
        }

        return Image.create(image.height(), image.width(), buffer);
    }
}
