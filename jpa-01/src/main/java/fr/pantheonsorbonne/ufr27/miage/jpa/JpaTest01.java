package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.util.List;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import fr.pantheonsorbonne.ufr27.miage.jpa.domain.Department;
import fr.pantheonsorbonne.ufr27.miage.jpa.domain.Employee;

public class JpaTest01 {

	@Inject
	private EntityManager manager;

	
	
	
	public static void main(String[] args) throws ClassNotFoundException {

		
		SeContainerInitializer initializer = SeContainerInitializer.newInstance();

		try (SeContainer container = initializer.addPackages(true,JpaTest01.class.getPackage()).initialize()) {
			JpaTest01 jpa = container.select(JpaTest01.class).get();
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
		long numOfEmployees = ((Number) manager.createNativeQuery("Select COUNT(*) FROM EMPLOYEE").getSingleResult()).longValue();
		if (numOfEmployees == 0) {
			Department department = new Department("java");
			manager.persist(department);

			
			manager.persist(new Employee("Jakab Gipsz", department));
			manager.persist(new Employee("Captain Nemo", department));

		}
	}

	private void listEmployees() {
		List<Employee> resultList = manager.createNativeQuery("SELECT * FROM EMPLOYEE",Employee.class).getResultList();
		System.out.println("num of employess:" + resultList.size());
		for (Object next : resultList) {
			System.out.println("next employee: " + next);
		}
	}

}
