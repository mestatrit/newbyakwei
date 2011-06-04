package com.dev3g.cactus.util.image;

public class ImageParam {

	private OriginInfo originInfo;

	private int quality;

	private double sharp0;

	private double sharp1;

	private boolean clearExif;

	public ImageParam(OriginInfo originInfo) {
		this.originInfo = originInfo;
	}

	public OriginInfo getOriginInfo() {
		return originInfo;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public double getSharp0() {
		return sharp0;
	}

	public void setSharp0(double sharp0) {
		this.sharp0 = sharp0;
	}

	public double getSharp1() {
		return sharp1;
	}

	public void setSharp1(double sharp1) {
		this.sharp1 = sharp1;
	}

	public boolean isClearExif() {
		return clearExif;
	}

	public void setClearExif(boolean clearExif) {
		this.clearExif = clearExif;
	}
}