package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "regcode", id = "codeid")
public class RegCode {
	public static final byte OBJTYPE_USER = 0;

	public static final byte OBJTYPE_COMPANY = 1;

	private long codeId;

	private long objId;

	private String name;

	private byte objType;

	private Date createTime;

	public boolean isUsrCode() {
		if (this.objType == 0) {
			return true;
		}
		return false;
	}

	public String getEnc_name() {
		return DataUtil.urlEncoder(name);
	}

	public long getCodeId() {
		return codeId;
	}

	public void setCodeId(long codeId) {
		this.codeId = codeId;
	}

	public long getObjId() {
		return objId;
	}

	public void setObjId(long objId) {
		this.objId = objId;
	}

	public byte getObjType() {
		return objType;
	}

	public void setObjType(byte objType) {
		this.objType = objType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		if (DataUtil.isEmpty(name)) {
			return Err.REGCODE_NAME_ERROR;
		}
		if (!this.name.startsWith("hk")) {
			return Err.REGCODE_NAME_ERROR;
		}
		if (this.name.length() > 10 || this.name.length() <= 2) {
			return Err.REGCODE_NAME_ERROR;
		}
		if (!DataUtil.isNumberOrCharOrChinese(name)) {
			return Err.REGCODE_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}