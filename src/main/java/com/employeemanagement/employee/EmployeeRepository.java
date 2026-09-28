package com.employeemanagement.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

	Employee save(Employee employee);

	Optional<Employee> findById(Long id);

	List<Employee> findAll();

	Optional<Employee> findByEmail(String email);

	boolean existsById(Long id);

	void deleteById(Long id);
}
