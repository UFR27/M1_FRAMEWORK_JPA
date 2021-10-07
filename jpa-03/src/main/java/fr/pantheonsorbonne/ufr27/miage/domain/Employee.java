package fr.pantheonsorbonne.ufr27.miage.domain;



import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "findAllEmployees", query = "select i from Employee i"),
        @NamedQuery(name = "countEmployees", query = "select count(i) from Employee i"),
        @NamedQuery(name = "findEmployeeByName", query = "select i from Employee i where i.name = :name"),
        @NamedQuery(name = "deleteEmployeeByName", query = "delete from Employee i where i.name = :name"),
        @NamedQuery(name = "deleteAllEmployee", query = "delete from Employee")})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private double salary;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    private Department department;

    public Employee() {
    }

    public Employee(String name, Department department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
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
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", department=" + department +
                '}';
    }
}
