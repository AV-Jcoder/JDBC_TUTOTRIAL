package org.avj;

import org.avj.dao.DAO;
import org.avj.dao.impl.EmployeeDAOImpl;
import org.avj.pojo.Employee;
import java.util.List;


public class TestQuery {
    public static void main(String[] args) {

            DAO<Employee,Integer> dao = new EmployeeDAOImpl();

            List<Employee> list = dao.readAll();
            for (Employee e: list) {
                System.out.println(e);
            }

            Employee emp  = dao.readById(3);
            emp.setSalary(26_001);

            boolean res = dao.update(emp);
            System.out.println(res);

    }
}
