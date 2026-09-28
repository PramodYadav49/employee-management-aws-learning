package com.employeemanagement.employee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeServiceTest {

	private EmployeeService service;

	@BeforeEach
	void setUp() {
		service = new EmployeeService(new InMemoryEmployeeRepository());
	}

	private Employee employee(String email) {
		Employee employee = new Employee(null, "John", "Doe", email, "555-0100", "Engineering",
				75000.0, LocalDate.of(2020, 1, 15));
		return employee;
	}

	@Test
	void createAssignsIdAndStoresEmployee() {
		Employee created = service.create(employee("john@example.com"));

		assertThat(created.getId()).isNotNull();
		assertThat(service.getById(created.getId())).isEqualTo(created);
	}

	@Test
	void createRejectsDuplicateEmail() {
		service.create(employee("john@example.com"));

		assertThatThrownBy(() -> service.create(employee("JOHN@example.com")))
				.isInstanceOf(EmailAlreadyExistsException.class);
	}

	@Test
	void updateKeepsSameId() {
		Employee created = service.create(employee("john@example.com"));

		Employee updated = service.update(created.getId(), employee("john.new@example.com"));

		assertThat(updated.getId()).isEqualTo(created.getId());
		assertThat(service.getById(created.getId()).getEmail()).isEqualTo("john.new@example.com");
	}

	@Test
	void updateAllowsKeepingOwnEmail() {
		Employee created = service.create(employee("john@example.com"));

		Employee updated = service.update(created.getId(), employee("john@example.com"));

		assertThat(updated.getId()).isEqualTo(created.getId());
	}

	@Test
	void getByIdThrowsWhenMissing() {
		assertThatThrownBy(() -> service.getById(99L))
				.isInstanceOf(EmployeeNotFoundException.class);
	}

	@Test
	void deleteRemovesEmployee() {
		Employee created = service.create(employee("john@example.com"));

		service.delete(created.getId());

		assertThat(service.getAll()).isEmpty();
		assertThatThrownBy(() -> service.getById(created.getId()))
				.isInstanceOf(EmployeeNotFoundException.class);
	}
}
