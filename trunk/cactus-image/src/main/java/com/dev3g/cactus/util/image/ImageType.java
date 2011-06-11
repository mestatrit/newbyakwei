package com.dev3g.cactus.util.image;

public enum ImageType {
	JPEG(0), BMP(1), PNG(2), GIF(3);

	int value;

	public int getValue() {
		return value;
	}

	private ImageType(int value) {
		this.value = value;
	}
}