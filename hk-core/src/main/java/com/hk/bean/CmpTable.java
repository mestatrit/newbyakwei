package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmptable", id = "tableid")
public class CmpTable {
	/**
	 * 不允许网络预订
	 */
	public static final byte NETORDERFLG_N = 0;

	/**
	 * 允许网络预订
	 */
	public static final byte NETORDERFLG_Y = 1;

	public static final byte FREEFLG_FREE = 0;

	public static final byte FREEFLG_INUSE = 1;

	/**
	 * id
	 */
	private long tableId;

	/**
	 * 分类id
	 */
	private long sortId;

	/**
	 * 餐厅id
	 */
	private long companyId;

	/**
	 * 桌号
	 */
	private String tableNum;

	/**
	 * 描述
	 */
	private String intro;

	/**
	 * 理想就餐人数
	 */
	private int bestPersonNum;

	/**
	 * 最多就餐人数
	 */
	private int mostPersonNum;

	/**
	 * 最低消费
	 */
	private double leastPrice;

	/**
	 * 责任人
	 */
	private String opname;

	/**
	 * 网络预订标识
	 */
	private byte netOrderflg;

	/**
	 * 是否正在使用
	 */
	private byte freeflg;

	private boolean booked;

	private long setId;

	private int orderflg;

	private CmpTableSort cmpTableSort;

	private CmpTablePhotoSet cmpTablePhotoSet;

	public void setCmpTablePhotoSet(CmpTablePhotoSet cmpTablePhotoSet) {
		this.cmpTablePhotoSet = cmpTablePhotoSet;
	}

	public CmpTablePhotoSet getCmpTablePhotoSet() {
		return cmpTablePhotoSet;
	}

	public void setCmpTableSort(CmpTableSort cmpTableSort) {
		this.cmpTableSort = cmpTableSort;
	}

	public CmpTableSort getCmpTableSort() {
		return cmpTableSort;
	}

	public long getTableId() {
		return tableId;
	}

	public void setTableId(long tableId) {
		this.tableId = tableId;
	}

	public String getIntro() {
		return intro;
	}

	public String getSimpleIntro() {
		if (this.intro != null) {
			String s = DataUtil.toTextRow(this.intro);
			return DataUtil.limitLength(s, 10);
		}
		return null;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getBestPersonNum() {
		return bestPersonNum;
	}

	public void setBestPersonNum(int bestPersonNum) {
		this.bestPersonNum = bestPersonNum;
	}

	public int getMostPersonNum() {
		return mostPersonNum;
	}

	public void setMostPersonNum(int mostPersonNum) {
		this.mostPersonNum = mostPersonNum;
	}

	public double getLeastPrice() {
		return leastPrice;
	}

	public void setLeastPrice(double leastPrice) {
		this.leastPrice = leastPrice;
	}

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}

	public byte getNetOrderflg() {
		return netOrderflg;
	}

	public void setNetOrderflg(byte netOrderflg) {
		this.netOrderflg = netOrderflg;
	}

	public long getSortId() {
		return sortId;
	}

	public void setSortId(long sortId) {
		this.sortId = sortId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public boolean isFree() {
		if (this.freeflg == FREEFLG_FREE) {
			return true;
		}
		return false;
	}

	public byte getFreeflg() {
		return freeflg;
	}

	public void setFreeflg(byte freeflg) {
		this.freeflg = freeflg;
	}

	public String getTableNum() {
		return tableNum;
	}

	public void setTableNum(String tableNum) {
		this.tableNum = tableNum;
	}

	public int validate() {
		String s = DataUtil.toTextRow(intro);
		if (s != null && s.length() > 200) {
			return Err.CMPTABLE_INTRO_ERROR;
		}
		if (this.bestPersonNum <= 0) {
			return Err.CMPTABLE_BESTPERSONNUM_ERROR;
		}
		if (this.mostPersonNum <= 0) {
			return Err.CMPTABLE_MOSTPERSONNUM_ERROR;
		}
		s = DataUtil.toTextRow(this.opname);
		if (s != null && s.length() > 15) {
			return Err.CMPTABLE_OPNAME_ERROR;
		}
		if (this.netOrderflg != NETORDERFLG_N
				&& this.netOrderflg != NETORDERFLG_Y) {
			return Err.CMPTABLE_NETORDERFLG_ERROR;
		}
		if (this.sortId <= 0) {
			return Err.CMPTABLE_SORTID_ERROR;
		}
		return Err.SUCCESS;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

	public long getSetId() {
		return setId;
	}

	public void setSetId(long setId) {
		this.setId = setId;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public boolean isCanNetBooked() {
		if (this.netOrderflg == NETORDERFLG_Y) {
			return true;
		}
		return false;
	}
}