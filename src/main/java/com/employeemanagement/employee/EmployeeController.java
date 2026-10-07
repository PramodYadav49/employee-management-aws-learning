package com.employeemanagement.employee;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService service;

	public EmployeeController(EmployeeService service) {
		this.service = service;
	}

	@GetMapping
	public List<Employee> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Employee getById(@PathVariable Long id) {
		return service.getById(id);
	}

	@PostMapping
	public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) {
		Employee created = service.create(employee);
		return ResponseEntity.created(URI.create("/api/employees/" + created.getId())).body(created);
	}

	@PutMapping("/{id}")
	public Employee update(@PathVariable Long id, @Valid @RequestBody Employee employee) {
		return service.update(id, employee);
	}

	@DeleteMapping("/{id}")
	public Map<String, Object> delete(@PathVariable Long id) {
		service.delete(id);
		return Map.of("message", "Employee deleted successfully", "id", id);
	}

	@GetMapping("/testing")
	public String test() {
		return "test";
	}

	@GetMapping("/dev")
	public String dev() {
		return "dev branch";
	}
}
