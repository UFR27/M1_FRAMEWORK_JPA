package fr.pantheonsorbonne.ufr27.miage.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity

@NamedQueries({ @NamedQuery(name = "findAllEmployees", query = "select i from Employee i"),
		@NamedQuery(name = "countEmployees", query = "select count(i) from Employee i"),
		@NamedQuery(name = "findEmployeeByName", query = "select i from Employee i where i.name = :name"),
		@NamedQuery(name = "deleteEmployeeByName", query = "delete from Employee i where i.name = :name"),
		@NamedQuery(name = "deleteAllEmployee", query = "delete from Employee") })

public class Employee {
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne
	private Department department;

	public Employee() {
	}

	public Employee(String name, Department department) {
		this.name = name;
		this.department = department;
	}

	public Employee(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department.getName() + "]";
	}

}
