package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.util.Collection;
import java.util.List;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import fr.pantheonsorbonne.ufr27.miage.jpa.domain.Department;
import fr.pantheonsorbonne.ufr27.miage.jpa.domain.Employee;

public class JpaTest02 {

	@Inject
	private EntityManager manager;

	public static void main(String[] args) throws ClassNotFoundException {

		SeContainerInitializer initializer = SeContainerInitializer.newInstance();

		try (SeContainer container = initializer.addPackages(true, JpaTest02.class.getPackage()).initialize()) {
			JpaTest02 jpa = container.select(JpaTest02.class).get();
			EntityTransaction tx = jpa.manager.getTransaction();
			tx.begin();
			try {
				jpa.createEmployees();
			} catch (Exception e) {
				e.printStackTrace();
			}
			tx.commit();

			jpa.listEmployees();

			System.out.println(".. done");

		}

	}

	private void createEmployees() {
		long numOfEmployees = manager.createNamedQuery("countEmployees", Long.class).getSingleResult();
		if (numOfEmployees > 0) {
			int res = manager.createNamedQuery("deleteAllEmployee").executeUpdate();
			System.out.println("Deleted  " + res + " Employees");

			int dep = manager.createNamedQuery("deleteAllDepartments").executeUpdate();
			System.out.println("Deleted  " + dep + " Department(s)");
		}

		Department department = new Department("java");
		manager.persist(department);
		manager.persist(new Employee("Jakab Gipsz", department));
		manager.persist(new Employee("Captain Nemo", department));

	}

	private void listEmployees() {
		Collection<Employee> employees = manager.createNamedQuery("findAllEmployees", Employee.class).getResultList();
		for (Employee e : employees) {
			System.out.println(e);
		}

	}

}
