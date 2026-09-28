package com.employeemanagement.employee;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	private final EmployeeRepository repository;

	public EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}

	public List<Employee> getAll() {
		return repository.findAll();
	}

	public Employee getById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	public Employee create(Employee employee) {
		checkEmailAvailable(employee.getEmail(), null);
		employee.setId(null);
		return repository.save(employee);
	}

	public Employee update(Long id, Employee employee) {
		getById(id);
		checkEmailAvailable(employee.getEmail(), id);
		employee.setId(id);
		return repository.save(employee);
	}

	public void delete(Long id) {
		getById(id);
		repository.deleteById(id);
	}

	private void checkEmailAvailable(String email, Long currentId) {
		repository.findByEmail(email)
				.filter(existing -> !existing.getId().equals(currentId))
				.ifPresent(existing -> {
					throw new EmailAlreadyExistsException(email);
				});
	}
}
