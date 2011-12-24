package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 例如有5个
 * 我有一个已经看了3次了
 * 我再看，虽然随机到了这个广告，但是看我已经看了3次了，这个时候自动显示另外一个
 * 相当于你要记录广告每天的展示人数和pv
 * 一天有多少人看了他的广告
 * 一天有多少人次看了他的广告，人次是按照1个人不能看一个广告三次计算的
 * 例如一个广告，展示1000次，在火酷总共展示了3天展示完毕
 * 第一天，我看了5次
 * 这个时候，有效是3次
 * 对于广告厂商，我也是仅仅看了他3次
 * 
 * @author akwei
 */
@Table(name = "hkadview")
public class HkAdView {

	@Id
	private long oid;

	/**
	 * 广告id
	 */
	@Column
	private long adoid;

	/**
	 * 唯一用户识别
	 */
	@Column
	private String viewerId;

	/**
	 * ip地址
	 */
	@Column
	private String ip;

	/**
	 * 日期
	 */
	@Column
	private int udate;

	@Column
	private int ucount;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getAdoid() {
		return adoid;
	}

	public void setAdoid(long adoid) {
		this.adoid = adoid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getUdate() {
		return udate;
	}

	public void setUdate(int udate) {
		this.udate = udate;
	}

	public String getViewerId() {
		return viewerId;
	}

	public void setViewerId(String viewerId) {
		this.viewerId = viewerId;
	}

	public int getUcount() {
		return ucount;
	}

	public void setUcount(int ucount) {
		this.ucount = ucount;
	}

	public void addUcount(int amount) {
		this.setUcount(this.getUcount() + amount);
	}
}