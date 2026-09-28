package com.employeemanagement.employee;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class Employee {

	private Long id;

	@NotBlank(message = "firstName is required")
	private String firstName;

	@NotBlank(message = "lastName is required")
	private String lastName;

	@NotBlank(message = "email is required")
	@Email(message = "email must be a valid email address")
	private String email;

	private String phone;

	private String department;

	@PositiveOrZero(message = "salary must be greater than or equal to 0")
	private Double salary;

	@NotNull(message = "hireDate is required")
	private LocalDate hireDate;

	public Employee() {
	}

	public Employee(Long id, String firstName, String lastName, String email, String phone,
			String department, Double salary, LocalDate hireDate) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.department = department;
		this.salary = salary;
		this.hireDate = hireDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Employee employee)) {
			return false;
		}
		return Objects.equals(id, employee.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Employee{id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", phone=" + phone + ", department=" + department
				+ ", salary=" + salary + ", hireDate=" + hireDate + "}";
	}
}
