package com.hk.frame.web.view;

public class ShowMode {

	private int modeId;

	private String name;

	private boolean touchMode;

	private boolean showBigImg;

	private boolean showImg;

	public void setShowImg(boolean showImg) {
		this.showImg = showImg;
	}

	public boolean isShowImg() {
		return showImg;
	}

	public void setShowBigImg(boolean showBigImg) {
		this.showBigImg = showBigImg;
	}

	public boolean isShowBigImg() {
		return showBigImg;
	}

	public boolean isTouchMode() {
		return touchMode;
	}

	public void setTouchMode(boolean touchMode) {
		this.touchMode = touchMode;
	}

	public int getModeId() {
		return modeId;
	}

	public void setModeId(int modeId) {
		this.modeId = modeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}