package com.hk.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;

/**
 * 用户当天报到的足迹数据，只保留10个
 * 
 * @author akwei
 */
@Table(name = "userdatecheckincmp")
public class UserDateCheckInCmp {

	@Id
	private long userId;

	@Column
	private String data;

	@Column
	private Date uptime;

	private List<Long> companyIdList;

	public void addCompanyId(long companyId) {
		if (companyIdList == null) {
			this.initCompanyIdList();
		}
		if (!companyIdList.contains(companyId)) {
			companyIdList.add(0, companyId);
		}
	}

	/**
	 * 最多保存 max个数据
	 * 
	 * @param max
	 *            2010-4-26
	 */
	public void toData(int max) {
		List<Long> list = null;
		list = DataUtil.subList(companyIdList, 0, max);
		StringBuilder sb = new StringBuilder();
		for (Long l : list) {
			sb.append(l).append(",");
		}
		if (list.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		this.data = sb.toString();
		this.initCompanyIdList();
	}

	public void clearCompanyIdList() {
		this.data = null;
		if (companyIdList != null) {
			companyIdList.clear();
		}
	}

	private void initCompanyIdList() {
		if (companyIdList != null) {
			companyIdList.clear();
		}
		companyIdList = new ArrayList<Long>();
		if (data != null) {
			String[] v = data.split(",");
			for (int i = 0; i < v.length; i++) {
				if (v[i].length() > 0) {
					try {
						companyIdList.add(Long.valueOf(v[i]));
					}
					catch (Exception e) {// 转换异常忽略
					}
				}
			}
		}
	}

	public int getCompanyIdListSize() {
		if (companyIdList == null) {
			this.initCompanyIdList();
		}
		return companyIdList.size();
	}

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

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	/**
	 * 最后报到的足迹的时间是否与当前报到的时间是用同一天
	 * 
	 * @return
	 *         2010-4-26
	 */
	public boolean isInOneDate() {
		if (this.uptime == null) {
			this.uptime = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.uptime);
		Calendar now = Calendar.getInstance();
		if (cal.get(Calendar.DATE) == now.get(Calendar.DATE)) {// 如果上次报到时间是同一天，就检查报到的足迹数量
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		UserDateCheckInCmp o = new UserDateCheckInCmp();
		o.setUserId(1);
		o.setUptime(new Date());
		o.setData("1,2,3,4,5,6");
		P.println(o.getCompanyIdListSize());
		o.addCompanyId(1);
		P.println(o.getCompanyIdListSize());
		o.addCompanyId(7);
		o.addCompanyId(8);
		o.addCompanyId(9);
		P.println(o.getCompanyIdListSize());
		o.addCompanyId(10);
		o.addCompanyId(11);
		P.println(o.getCompanyIdListSize());
		o.toData(5);
		P.println(o.getCompanyIdListSize());
	}
}