package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.domain.Department;
import fr.pantheonsorbonne.ufr27.miage.domain.Employee;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class EmployeeDAOImpl implements EmployeeDAO {

    @Inject
    EntityManager manager;

    @Override
    @Transactional
    public void createBaseEmployees() {
        long numOfEmployees = ((Number) manager.createNativeQuery("Select COUNT(*) FROM Employee").getSingleResult()).longValue();
        if (numOfEmployees == 0) {
            Department department = new Department("java");
            manager.persist(department);


            manager.persist(new Employee("Jakab Gipsz", department));
            manager.persist(new Employee("Captain Nemo", department));

        }
    }

    @Override
    @Transactional
    public List<Employee> listEmployees() {
        return manager.createNativeQuery("SELECT * FROM Employee", Employee.class).getResultList();

    }

}
