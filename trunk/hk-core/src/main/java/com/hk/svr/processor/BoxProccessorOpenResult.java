package com.hk.svr.processor;

import com.hk.svr.BoxOpenResult;

public class BoxProccessorOpenResult {

	private BoxOpenResult boxOpenResult;

	private boolean noEnoughPoints;

	private int errorCode;

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return 成功返回 {@link Err.SUCCESS},失败返回 {@link Err.OPENBOX_CITY_ERROR}
	 *         2010-4-26
	 */
	public int getErrorCode() {
		return errorCode;
	}

	public BoxOpenResult getBoxOpenResult() {
		return boxOpenResult;
	}

	public void setBoxOpenResult(BoxOpenResult boxOpenResult) {
		this.boxOpenResult = boxOpenResult;
	}

	public boolean isNoEnoughPoints() {
		return noEnoughPoints;
	}

	public void setNoEnoughPoints(boolean noEnoughPoints) {
		this.noEnoughPoints = noEnoughPoints;
	}
}