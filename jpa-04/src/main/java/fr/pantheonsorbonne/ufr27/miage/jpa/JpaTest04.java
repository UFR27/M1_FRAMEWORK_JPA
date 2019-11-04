package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.util.Collection;
import java.util.List;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

import fr.pantheonsorbonne.ufr27.miage.jpa.domain.Department;
import fr.pantheonsorbonne.ufr27.miage.jpa.domain.Department_;
import fr.pantheonsorbonne.ufr27.miage.jpa.domain.Employee;
import fr.pantheonsorbonne.ufr27.miage.jpa.domain.Employee_;

public class JpaTest04 {

	@Inject
	private EntityManager manager;

	public static void main(String[] args) throws ClassNotFoundException {

		SeContainerInitializer initializer = SeContainerInitializer.newInstance();

		try (SeContainer container = initializer.addPackages(true, JpaTest04.class.getPackage()).initialize()) {
			JpaTest04 jpa = container.select(JpaTest04.class).get();
			EntityTransaction tx = jpa.manager.getTransaction();
			tx.begin();
			try {
				jpa.createEmployees();
			} catch (Exception e) {
				e.printStackTrace();
			}
			tx.commit();

			jpa.listEmployees();

			// TODO: get the total salary of Php Guys

			double getTotalEmployeeSalary = jpa.getTotalSalary();
			double totalPhpGuysSalary = jpa.getPhpGuysCumulatedSalary();
			int countJavaGuys = jpa.countJavaGuys();
			jpa.giveRaiseToJavaGuys(totalPhpGuysSalary / countJavaGuys);
			jpa.fireAllPhpGuys();
			double getTotalEmployeeSalaryAfterRaise = jpa.getTotalSalary();

			System.out.println(
					" total salary is unchanged:" + getTotalEmployeeSalary + " vs " + getTotalEmployeeSalaryAfterRaise);

		}

	}

	private double getTotalSalary() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int countJavaGuys() {
		// TODO Auto-generated method stub
		return 0;
	}

	private void firePhpGuys() {
		// TODO Auto-generated method stub

	}

	private void createEmployees() {

		// get the main criteria building block
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		// create the query with the expected return type
		CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
		// table from which to return data
		Root<Employee> root = query.from(Employee.class);

		// joint with DepartmentTable
		Join<Employee, Department> join = root.join(Employee_.department);

		// different predicates for different requests
		Predicate predicateJava = builder.equal(join.get(Department_.NAME), "java");
		Predicate predicatePhp = builder.equal(join.get(Department_.NAME), "php");

		TypedQuery<Employee> getEmployeesByDepNameJava = manager.createQuery(query.where(predicateJava));
		List<Employee> javaEmployees = getEmployeesByDepNameJava.getResultList();
		int numOfEmployeesJava = javaEmployees.size();
		if (numOfEmployeesJava == 0) {
			Department department = new Department("java");
			manager.persist(department);

			manager.persist(new Employee("Jakab Gipsz", department, 100));
			manager.persist(new Employee("Captain Nemo", department, 120));
		}

		TypedQuery<Employee> getEmployeesByDepNamePhp = manager.createQuery(query.where(predicatePhp));

		List<Employee> phpEmployees = getEmployeesByDepNamePhp.getResultList();
		int numOfEmployeesPhp = phpEmployees.size();
		if (numOfEmployeesPhp == 0) {

			Department departmentPhp = new Department("php");
			manager.persist(departmentPhp);

			manager.persist(new Employee("Goofie", departmentPhp, 80));
			manager.persist(new Employee("Dummy", departmentPhp, 70));

		}

	}

	private void listEmployees() {
		// get the main criteria building block
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		// create the query with the expected return type
		CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
		//

		List<Employee> employees = manager.createQuery(query).getResultList();

		for (Employee e : employees) {
			System.out.println(e);
		}

	}

	private void fireAllPhpGuys() {
		
	}

	private double getPhpGuysCumulatedSalary() {
		return 0;
	}

	private void giveRaiseToJavaGuys(double amountPerCoder) {

	}

}
