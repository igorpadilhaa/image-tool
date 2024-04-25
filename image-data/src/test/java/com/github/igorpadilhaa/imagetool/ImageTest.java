package com.github.igorpadilhaa.imagetool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImageTest {

	final static String IMAGE_PATH = "/windows-xp.jpeg";

	@Test
	public void shouldLoadImage() {
		Assertions.assertDoesNotThrow(() -> {
			Image image = Image.load(ImageTest.class.getResourceAsStream(IMAGE_PATH));

			Assertions.assertNotNull(image.pixels());
		});
	}

}
