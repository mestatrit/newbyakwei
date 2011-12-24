package com.hk.svr;

import java.util.List;
import com.hk.bean.Employee;

public interface EmployeeService {
	void createEmployee(int companyId, long userId, byte level);

	List<Employee> getEmployeeList(int companyId, int begin, int size);

	Employee getEmployee(int companyId, long userId);

	void deleteEmployee(int companyId, long userId);
}