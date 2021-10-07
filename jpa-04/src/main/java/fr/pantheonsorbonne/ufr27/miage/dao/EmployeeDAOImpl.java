package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.domain.Department;
import fr.pantheonsorbonne.ufr27.miage.domain.Department_;
import fr.pantheonsorbonne.ufr27.miage.domain.Employee;
import fr.pantheonsorbonne.ufr27.miage.domain.Employee_;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class EmployeeDAOImpl implements EmployeeDAO {

    @Inject
    EntityManager manager;

    @Override
    @Transactional
    public void createBaseEmployees() {
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

    @Override
    @Transactional
    public List<Employee> listEmployees() {
        // get the main criteria building block
        CriteriaBuilder builder = manager.getCriteriaBuilder();


        // create the query with the expected return type
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        return manager.createQuery(query).getResultList();

    }

    @Override
    @Transactional
    public void fireAllPhpGuys() {
        {
            Query query = manager.createQuery("SELECT e from Employee  e where e.department.name=:p");
            query.setParameter("p", "php");
            for (Employee e : (List<Employee>) query.getResultList()) {
                manager.remove(e);
            }
        }



    }

    @Override
    public double getTotalSalary() {
        return 0;
    }

    @Override
    public double getPhpGuysCumulatedSalary() {
        return 0;
    }

    @Override
    public int countJavaGuys() {
        return 0;
    }

    @Override
    public void giveRaiseToJavaGuys(double v) {

    }


}
