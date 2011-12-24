package com.hk.bean;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;

@Table(name = "userlastcheckin")
public class UserLastCheckIn {

	@Id
	private long userId;

	@Column
	private String data;

	private List<Long> logIdList;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<Long> getLogIdList() {
		if (this.logIdList == null) {
			this.buildLogIdList();
		}
		return this.logIdList;
	}

	private void buildLogIdList() {
		logIdList = new ArrayList<Long>();
		if (DataUtil.isEmpty(data)) {
			return;
		}
		String[] s = data.split(",");
		for (int i = 0; i < s.length; i++) {
			logIdList.add(Long.valueOf(s[i]));
		}
	}

	public void addLogId(long logId) {
		if (logIdList == null) {
			this.buildLogIdList();
		}
		logIdList.add(0, logId);
	}

	/**
	 * 把list值转变为存储到data的值
	 * 
	 * @param max 最多保留list中的数量
	 *            2010-4-10
	 */
	public void toData(int max) {
		if (logIdList.size() == 0) {
			this.data = null;
			return;
		}
		List<Long> olist = null;
		if (logIdList.size() >= max) {
			olist = logIdList.subList(0, max);
		}
		else {
			olist = logIdList.subList(0, logIdList.size());
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : olist) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		this.data = sb.toString();
	}

	public long getLastLogId() {
		buildLogIdList();
		if (this.logIdList.size() > 0) {
			return this.logIdList.get(0);
		}
		return 0;
	}
}