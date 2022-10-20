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
        int numOfEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();
        if (numOfEmployees == 0) {

            Department javadep = new Department("java");
            manager.persist(javadep);
            manager.persist(new Employee("Java Guy 1", javadep,100));
            manager.persist(new Employee("Java Guy 2", javadep,120));

            Department phpdep = new Department("php");
            manager.persist(phpdep);
            manager.persist(new Employee("Php Guy 1", phpdep,70));
            manager.persist(new Employee("Php Guy 2", phpdep,80));

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
