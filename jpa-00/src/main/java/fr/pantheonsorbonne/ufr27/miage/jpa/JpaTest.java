package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.util.List;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import fr.pantheonsorbonne.ufr27.miage.jpa.domain.Department;
import fr.pantheonsorbonne.ufr27.miage.jpa.domain.Employee;

public class JpaTest {

	@Inject
	private EntityManager manager;

	
	
	
	public static void main(String[] args) throws ClassNotFoundException {

		
		SeContainerInitializer initializer = SeContainerInitializer.newInstance();

		try (SeContainer container = initializer.addPackages(true,JpaTest.class.getPackage()).initialize()) {
			JpaTest jpa = container.select(JpaTest.class).get();
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
		int numOfEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();
		if (numOfEmployees == 0) {
			Department department = new Department("java");
			manager.persist(department);

			
			manager.persist(new Employee("Jakab Gipsz", department));
			manager.persist(new Employee("Captain Nemo", department));

		}
	}

	private void listEmployees() {
		List<Employee> resultList = manager.createQuery("Select a From Employee a", Employee.class).getResultList();
		System.out.println("num of employess:" + resultList.size());
		for (Employee next : resultList) {
			System.out.println("next employee: " + next);
		}
	}

}
