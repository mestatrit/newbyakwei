package com.hk.web.hk4.venue.action;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户预约的信息
 * 
 * @author akwei
 */
public class ReserveInfo {

	/**
	 * 预约的服务的ids用,分隔。例：1,2,3,4
	 */
	private String svrIdData;

	/**
	 * 预约的人员id
	 */
	private long actorId;

	public ReserveInfo() {
	}

	/**
	 * 把一个表示该对象的字符串传入，根据字符串生成对象并赋值
	 * 
	 * @param data
	 */
	public ReserveInfo(String data) {
		String[] t = data.split(";");
		if (t.length < 2) {
			return;
		}
		try {
			this.actorId = Long.valueOf(t[0]);
		}
		catch (NumberFormatException e) {
		}
		String[] svrIds = t[1].split(",");
		List<Long> idList = new ArrayList<Long>();
		if (svrIds != null) {
			for (String id : svrIds) {
				try {
					idList.add(Long.valueOf(id));
				}
				catch (NumberFormatException e) {
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (long id : idList) {
			sb.append(id).append(",");
		}
		this.svrIdData = sb.toString();
	}

	public void addSvrId(long svrId) {
		if (this.svrIdData == null) {
			this.svrIdData = svrId + ",";
		}
		else {
			this.svrIdData += svrId + ",";
		}
	}

	public String getSvrIdData() {
		return svrIdData;
	}

	public void setSvrIdData(String svrIdData) {
		this.svrIdData = svrIdData;
	}

	public long getActorId() {
		return actorId;
	}

	public void setActorId(long actorId) {
		this.actorId = actorId;
	}

	@Override
	public String toString() {
		if (this.svrIdData == null) {
			this.svrIdData = "";
		}
		return this.actorId + ";" + this.svrIdData;
	}
}