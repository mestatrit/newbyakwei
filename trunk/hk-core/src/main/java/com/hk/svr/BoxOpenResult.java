package com.hk.svr;

import com.hk.bean.UserBoxPrize;
import com.hk.bean.UserEquipment;
import com.hk.svr.pub.Err;

/**
 * 开箱结果
 * 
 * @author akwei
 */
public class BoxOpenResult {

	private UserBoxPrize userBoxPrize;

	private int errorCode;

	private UserEquipment userEquipment;

	public void setUserEquipment(UserEquipment userEquipment) {
		this.userEquipment = userEquipment;
	}

	public UserEquipment getUserEquipment() {
		return userEquipment;
	}

	public UserBoxPrize getUserBoxPrize() {
		return userBoxPrize;
	}

	public void setUserBoxPrize(UserBoxPrize userBoxPrize) {
		this.userBoxPrize = userBoxPrize;
	}

	/**
	 * @return {@link Err.BOX_STOP} 作废 {@link Err.BOX_PAUSE} 暂停
	 *         {@link Err.BOX_OPENOUT} 没有剩余 {@link Err.BOX_EXPIRED} 过期
	 *         {@link Err.BOX_OUT_OF_LIMIT} 超过开箱限制
	 */
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}