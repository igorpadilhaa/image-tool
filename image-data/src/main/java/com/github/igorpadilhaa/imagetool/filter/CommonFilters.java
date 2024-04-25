package com.github.igorpadilhaa.imagetool.filter;

import com.github.igorpadilhaa.imagetool.Image;

public enum CommonFilters implements ImageFilter {
    GRAYSCALE(new GrayscaleFilter()),
    SOBEL(new SobelFilter());

    private final ImageFilter filter;

    private CommonFilters(ImageFilter filter) {
        this.filter = filter;
    }

    @Override
    public void apply(Image img) {
        filter.apply(img);
    }    
}
