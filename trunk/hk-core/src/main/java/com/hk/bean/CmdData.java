package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 关键词对象
 * 
 * @author akwei
 */
@Table(name = "cmddata", id = "cmdid")
public class CmdData {
	/**
	 * 不限时间
	 */
	public static final byte ENDFLG_N = 0;

	/**
	 * 限制时间
	 */
	public static final byte ENDFLG_Y = 1;

	/**
	 * 系统指令
	 */
	public static final int OTYPE_SYS = 0;

	/**
	 * 宝箱
	 */
	public static final int OTYPE_BOX = 1;

	/**
	 * 企业活动
	 */
	public static final int OTYPE_CMPACT = 2;

	private long cmdId;

	/**
	 * 关键词名称
	 */
	private String name;

	/**
	 * 使用关键词的数据id 0:系统指令
	 */
	private long oid;

	/**
	 * 使用关键词的数据类型 0:系统指令 1:宝箱
	 */
	private int otype;

	/**
	 * 到期时间
	 */
	private Date endTime;

	/**
	 * 期限类型 0:不限时 (endTime可为null) 1:限制时间(endTime 不能为空)
	 */
	private byte endflg;

	public long getCmdId() {
		return cmdId;
	}

	public void setCmdId(long cmdId) {
		this.cmdId = cmdId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public int getOtype() {
		return otype;
	}

	public void setOtype(int otype) {
		this.otype = otype;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public byte getEndflg() {
		return endflg;
	}

	public void setEndflg(byte endflg) {
		this.endflg = endflg;
	}

	public int validate() {
		String s = DataUtil.toTextRow(name);
		if (s == null || (s.length() < 2 && s.length() > 10)) {
			return Err.CMDDATA_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}