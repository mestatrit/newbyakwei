package com.hk.svr.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Employee;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private QueryManager manager;

	public void createEmployee(int companyId, long userId, byte level) {
		Query query = manager.createQuery();
		if (this.getEmployee(companyId, userId) == null) {
			query.addField("companyid", companyId);
			query.addField("userid", userId);
			query.addField("level", level);
			query.insert(Employee.class);
		}
	}

	public void deleteEmployee(int companyId, long userId) {
		Query query = manager.createQuery();
		query.setTable(Employee.class);
		query.where("companyid=? and userid=?").setParam(companyId).setParam(
				userId);
		query.delete();
	}

	public Employee getEmployee(int companyId, long userId) {
		Query query = manager.createQuery();
		query.setTable(Employee.class);
		query.where("companyid=? and userid=?").setParam(companyId).setParam(
				userId);
		return query.getObject(Employee.class);
	}

	public List<Employee> getEmployeeList(int companyId, int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(Employee.class);
		query.where("companyid=?").setParam(companyId);
		return query.list(begin, size, Employee.class);
	}
}