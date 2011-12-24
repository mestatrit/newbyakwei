package com.hk.bean;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;

@Table(name = "userrecentlaba", id = "userid")
public class UserRecentLaba {

	@Id
	private long userId;

	@Column
	private String labaData;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLabaData() {
		return labaData;
	}

	public void setLabaData(String labaData) {
		this.labaData = labaData;
	}

	public List<Long> getLabaIdList() {
		List<Long> list = new ArrayList<Long>();
		if (!DataUtil.isEmpty(labaData)) {
			String[] t = labaData.split(",");
			for (int i = 0; i < t.length; i++) {
				if (t[i].equals("")) {
					continue;
				}
				list.add(Long.parseLong(t[i]));
			}
		}
		return list;
	}

	public long getLastLabaId() {
		if (!DataUtil.isEmpty(labaData)) {
			String[] t = labaData.split(",");
			if (t.length == 0) {
				return 0;
			}
			if (t[0].length() == 0) {
				return 0;
			}
			return Long.parseLong(t[0]);
		}
		return 0;
	}
}