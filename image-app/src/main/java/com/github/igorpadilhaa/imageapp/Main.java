package com.github.igorpadilhaa.imageapp;

import com.github.igorpadilhaa.imagetool.Image;

public class Main {

	public static void main(String[] args) {
        try {
            Image image = Image.load(Main.class.getResourceAsStream("/windows-xp.jpeg"));
            ImageDisplay display = new ImageDisplay(image);
            display.showUI();
        
        } catch (Image.ImageLoadException e) {
            System.err.println("failed to open image");
            e.printStackTrace();    
        }
    }

}
