package com.github.igorpadilhaa.imagetool.transform;

import com.github.igorpadilhaa.imagetool.Image;

@FunctionalInterface
public interface ImageTransform {

    public Image apply(Image image);

}
