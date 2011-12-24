package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户最后被道具剥削的点数，以备回复使用
 * 
 * @author akwei
 */
@Table(name = "userequpoint")
public class UserEquPoint {

	@Id
	private long userId;

	@Column
	private int points;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}