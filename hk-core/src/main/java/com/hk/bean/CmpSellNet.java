package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 企业销售网点，有后台录入
 * 
 * @author akwei
 */
@Table(name = "cmpsellnet")
public class CmpSellNet {

	@Id
	private long oid;

	@Column
	private String name;

	@Column
	private String addr;

	@Column
	private String tel;

	@Column
	private long companyId;

	@Column
	private int orderflg;

	@Column
	private long kindId;

	/**
	 * 用,分隔的坐标。例:x,y
	 */
	@Column
	private String mapData;

	public void setMapData(String mapData) {
		this.mapData = mapData;
	}

	public String getMapData() {
		return mapData;
	}

	private CmpSellNetKind cmpSellNetKind;

	public void setCmpSellNetKind(CmpSellNetKind cmpSellNetKind) {
		this.cmpSellNetKind = cmpSellNetKind;
	}

	public CmpSellNetKind getCmpSellNetKind() {
		return cmpSellNetKind;
	}

	public void setKindId(long kindId) {
		this.kindId = kindId;
	}

	public long getKindId() {
		return kindId;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int validate() {
		String s = DataUtil.toText(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 50) {
			return Err.CMPSELLNET_NAME_ERROR;
		}
		s = DataUtil.toText(this.addr);
		if (DataUtil.isEmpty(s) || s.length() > 200) {
			return Err.CMPSELLNET_ADDR_ERROR;
		}
		s = DataUtil.toText(this.tel);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.CMPSELLNET_TEL_ERROR;
		}
		return Err.SUCCESS;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public double getMarkerX() {
		if (DataUtil.isEmpty(this.mapData)) {
			return 0;
		}
		String[] t = this.mapData.split(",");
		if (t.length == 1) {
			return 0;
		}
		try {
			return Double.valueOf(t[0]);
		}
		catch (NumberFormatException e) {
			return 0;
		}
	}

	public double getMarkerY() {
		if (DataUtil.isEmpty(this.mapData)) {
			return 0;
		}
		String[] t = this.mapData.split(",");
		if (t.length == 1) {
			return 0;
		}
		try {
			return Double.valueOf(t[1]);
		}
		catch (NumberFormatException e) {
			return 0;
		}
	}
}