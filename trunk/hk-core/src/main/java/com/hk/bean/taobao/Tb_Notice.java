package com.hk.bean.taobao;

import java.util.Date;
import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.JsonUtil;

@Table(name = "tb_notice")
public class Tb_Notice {

	/**
	 * 未读
	 */
	public static final byte READFLG_N = 0;

	/**
	 * 已读
	 */
	public static final byte READFLG_Y = 1;

	/**
	 * xx点评了你推荐的商品xx
	 */
	public static final int NTYPE_CREATE_ITEMCMT = 1;

	/**
	 * xx回复了你的点评
	 */
	public static final int NTYPE_CREATE_REPLYCMT = 2;

	/**
	 * xx回答了你的问题
	 */
	public static final int NTYPE_CREATE_ANSWER = 3;

	/**
	 * 把你的答案设为最佳答案
	 */
	public static final int NTYPE_SELECT_BEST_ANSWER = 4;

	/**
	 * xx关注了你
	 */
	public static final int NTYPE_FOLLOW_YOU = 5;

	@Id
	private long noticeid;

	@Column
	private long userid;

	@Column
	private int ntype;

	/**
	 * json格数数据
	 */
	@Column
	private String data;

	@Column
	private byte readflg;

	/**
	 * 相关的对象id，主要是为了删除通知时使用。例如点评的你推荐的商品，ref_oid=点评id;加某人为好友，ref_oid=好友id;
	 */
	@Column
	private long ref_oid;

	@Column
	private Date create_time;

	public long getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(long noticeid) {
		this.noticeid = noticeid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public int getNtype() {
		return ntype;
	}

	public void setNtype(int ntype) {
		this.ntype = ntype;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public byte getReadflg() {
		return readflg;
	}

	public void setReadflg(byte readflg) {
		this.readflg = readflg;
	}

	public long getRef_oid() {
		return ref_oid;
	}

	public void setRef_oid(long refOid) {
		ref_oid = refOid;
	}

	public boolean isReaded() {
		if (this.readflg == READFLG_Y) {
			return true;
		}
		return false;
	}

	private Map<String, String> map;

	public Map<String, String> getDataMap() {
		if (map == null) {
			map = JsonUtil.getMapFromJson(this.data);
		}
		return map;
	}
}