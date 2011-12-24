package com.hk.bean;

import com.hk.svr.equipment.EquipmentMsg;

/**
 * 报到后的状态值
 * 
 * @author akwei
 */
public class CheckInResult {

	/**
	 * 报到是否成功
	 */
	private boolean checkInSuccess;

	/**
	 * 2此报到时间间隔太短
	 */
	private boolean checkInTimeTooShort;

	private EquipmentMsg equipmentMsg;

	public void setEquipmentMsg(EquipmentMsg equipmentMsg) {
		this.equipmentMsg = equipmentMsg;
	}

	public EquipmentMsg getEquipmentMsg() {
		return equipmentMsg;
	}

	public void setCheckInTimeTooShort(boolean checkInTimeTooShort) {
		this.checkInTimeTooShort = checkInTimeTooShort;
	}

	public boolean isCheckInTimeTooShort() {
		return checkInTimeTooShort;
	}

	public boolean isCheckInSuccess() {
		return checkInSuccess;
	}

	public void setCheckInSuccess(boolean checkInSuccess) {
		this.checkInSuccess = checkInSuccess;
	}
}