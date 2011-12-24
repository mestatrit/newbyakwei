package com.hk.web.company.action;

import com.hk.bean.Employee;

public class EmployeeVo {
	private Employee employee;

	private String levelData;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getLevelData() {
		return levelData;
	}

	public void setLevelData(String levelData) {
		this.levelData = levelData;
	}
}