package com.employeemanagement.employee;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryEmployeeRepository implements EmployeeRepository {

	private final Map<Long, Employee> employees = new ConcurrentHashMap<>();

	private final AtomicLong idGenerator = new AtomicLong(0);

	@Override
	public Employee save(Employee employee) {
		if (employee.getId() == null) {
			employee.setId(idGenerator.incrementAndGet());
		} else {
			idGenerator.updateAndGet(current -> Math.max(current, employee.getId()));
		}
		employees.put(employee.getId(), employee);
		return employee;
	}

	@Override
	public Optional<Employee> findById(Long id) {
		return Optional.ofNullable(employees.get(id));
	}

	@Override
	public List<Employee> findAll() {
		return employees.values().stream()
				.sorted(Comparator.comparing(Employee::getId))
				.toList();
	}

	@Override
	public Optional<Employee> findByEmail(String email) {
		if (email == null) {
			return Optional.empty();
		}
		return employees.values().stream()
				.filter(employee -> email.equalsIgnoreCase(employee.getEmail()))
				.findFirst();
	}

	@Override
	public boolean existsById(Long id) {
		return employees.containsKey(id);
	}

	@Override
	public void deleteById(Long id) {
		employees.remove(id);
	}
}
