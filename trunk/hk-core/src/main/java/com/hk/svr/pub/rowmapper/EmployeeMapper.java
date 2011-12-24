package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Employee;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class EmployeeMapper extends HkRowMapper<Employee> {
	@Override
	public Class<Employee> getMapperClass() {
		return Employee.class;
	}

	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee o = new Employee();
		o.setCompanyId(rs.getInt("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setLevel(rs.getByte("level"));
		return o;
	}
}