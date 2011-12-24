package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

@Table(name = "boxprize", id = "prizeid")
public class BoxPrize {

	public static final byte SIGNAL_N = 0;

	public static final byte SIGNAL_Y = 1;

	@Id
	private long prizeId;

	@Column
	private long boxId;

	@Column
	private String name;

	@Column
	private String tip;

	@Column
	private int pcount;

	@Column
	private int remain;

	@Column
	private String path;

	@Column
	private byte signal;// 是否支持暗号

	/**
	 * 虚拟物品(道具)
	 */
	@Column
	private long eid;

	public void setEid(long eid) {
		this.eid = eid;
	}

	public long getEid() {
		return eid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(long prizeId) {
		this.prizeId = prizeId;
	}

	public long getBoxId() {
		return boxId;
	}

	public void setBoxId(long boxId) {
		this.boxId = boxId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public int getPcount() {
		return pcount;
	}

	public void setPcount(int pcount) {
		this.pcount = pcount;
	}

	public byte getSignal() {
		return signal;
	}

	public void setSignal(byte signal) {
		this.signal = signal;
	}

	public int getRemain() {
		return remain;
	}

	public void setRemain(int remain) {
		this.remain = remain;
	}

	public static byte getSIGNAL_N() {
		return SIGNAL_N;
	}

	public static byte getSIGNAL_Y() {
		return SIGNAL_Y;
	}

	public boolean isUseSignal() {
		if (this.signal == SIGNAL_Y) {
			return true;
		}
		return false;
	}

	public int validate() {
		if (getPcount() <= 0) {
			return Err.BOXPRIZE_COUNT_ERROR;
		}
		if (DataUtil.isEmpty(getName())
				|| DataUtil.toText(getName()).length() > 15) {
			return Err.BOXPRIZE_NAME_LENGTH_TOO_LONG;
		}
		if (DataUtil.isEmpty(tip)) {
			return Err.BOXPRIZE_TIP_ERROR;
		}
		if (getTip().length() > 50) {
			return Err.BOXPRIZE_TIP_LENGTH_TOO_LONG;
		}
		return Err.SUCCESS;
	}

	public String getH_0Pic() {
		return ImageConfig.getPhotoPicH_0Url(this.path);
	}

	public String getH_1Pic() {
		return ImageConfig.getPhotoPicH_1Url(this.path);
	}

	public String getH_2Pic() {
		return ImageConfig.getPhotoPicH_2Url(this.path);
	}
}