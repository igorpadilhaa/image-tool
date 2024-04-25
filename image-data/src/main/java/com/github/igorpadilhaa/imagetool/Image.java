package com.github.igorpadilhaa.imagetool;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Image {

	private final int width, height;
	private final int[] pixels;

	private Image(int[] pixels, int width, int height) {
        if (pixels.length != width * height) {
            throw new IllegalArgumentException("length of pixels should match dimensions (width * heigth)");
        }
		
        this.pixels = pixels;
		this.width = width;
		this.height = height;
	}

	public static Image create(int width, int height) {
        return create(width, height, new int[width * height]);
	}

    public static Image create(int width, int height, int[] pixels) {
		return new Image(pixels, width, height);
    }

	public static Image load(InputStream data) throws ImageLoadException {
		try {
			BufferedImage image = ImageIO.read(data);
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			
			return new Image(pixels, image.getWidth(), image.getHeight());
			
		} catch (Exception e) {
			throw new ImageLoadException("failed to load image", e);
		}
	}

	public static class ImageLoadException extends Exception {
		
		private ImageLoadException(String message, Throwable cause) {
			super(message, cause);
		}
		
	}

	public static void save(Image image, Path path) throws ImageWriteException {
		String fileExtension = fileExtension(path);

		BufferedImage img = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0, 0, image.width(), image.height(), image.pixels(), 0, image.width());

		if (fileExtension.equals("")) {
			throw new IllegalArgumentException("invalid file extension on file '%s'".formatted(path));
		}

		try (OutputStream outStream = Files.newOutputStream(path, StandardOpenOption.CREATE)) {
			ImageIO.write(img, fileExtension, outStream);

		} catch(IOException e) {
			throw new ImageWriteException("failed to write to file '%s'".formatted(path), e);
		}
	}

	private static String fileExtension(Path filePath) {
		String fileName = filePath.getName(filePath.getNameCount() - 1).toString();
		
		int index = fileName.lastIndexOf('.');
		if (index == -1)
			return "";

		return fileName.substring(index + 1);
	}

	public static class ImageWriteException extends Exception {
		
		ImageWriteException(String message, Throwable cause) {
			super(message, cause);
		}

	}
	
	public int[] pixels() {
		return pixels;
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}

	public Image copy() {
        int[] buffer = Arrays.copyOf(pixels, pixels.length);
        return new Image(buffer, width, height);
	}
}
