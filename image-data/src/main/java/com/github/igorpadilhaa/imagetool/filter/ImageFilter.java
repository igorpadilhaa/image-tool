package com.github.igorpadilhaa.imagetool.filter;

import com.github.igorpadilhaa.imagetool.Image;

/**
 * Image filter represents a operation that can
 * be applied to an image, changing it's original content.
 */
@FunctionalInterface
public interface ImageFilter {

    /**
     * Apply the filter's transformation in the
     * receiving image. This method should mutate
     * the paramenter image.
     * 
     * @param img image to apply filter
     */
    public void apply(Image img);

}
