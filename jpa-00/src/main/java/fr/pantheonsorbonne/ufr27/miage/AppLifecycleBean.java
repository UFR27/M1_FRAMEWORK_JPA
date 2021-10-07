package fr.pantheonsorbonne.ufr27.miage;

import fr.pantheonsorbonne.ufr27.miage.dao.EmployeeDAO;
import fr.pantheonsorbonne.ufr27.miage.domain.Employee;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class AppLifecycleBean {

    @Inject
    EntityManager manager;

    @Inject
    EmployeeDAO dao;

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    void onStart(@Observes StartupEvent ev) {


        dao.createBaseEmployees();
        List<Employee> employees = dao.listEmployees();
        System.out.println("num of employess:" + employees.size());
        for (Employee next : employees) {
            System.out.println("next employee: " + next);
        }

        System.out.println(".. done");


    }


}

