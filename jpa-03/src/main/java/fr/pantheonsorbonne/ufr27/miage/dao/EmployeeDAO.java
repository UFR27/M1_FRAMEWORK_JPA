package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.domain.Employee;

import javax.transaction.Transactional;
import java.util.List;

public interface EmployeeDAO {

    @Transactional
    void createBaseEmployees();

    @Transactional
    List<Employee> listEmployees();

    @Transactional
    public void fireAllPhpGuys();
}
