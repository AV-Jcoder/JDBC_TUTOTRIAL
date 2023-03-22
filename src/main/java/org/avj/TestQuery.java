package org.avj;

import org.avj.dao.DAO;
import org.avj.dao.impl.EmployeeCarDAOImpl;
import org.avj.dao.impl.EmployeeDAOImpl;
import org.avj.pojo.Employee;
import org.avj.pojo.EmployeeCar;

import java.util.LinkedList;
import java.util.List;


public class TestQuery {
    public static void main(String[] args) {

//            DAO<Employee,Integer> dao = new EmployeeDAOImpl();
//
//            List<Employee> list = dao.readAll();
//            for (Employee e: list) {
//                System.out.println(e);
//            }
//
//            Employee emp  = dao.readById(3);
//            emp.setSalary(26_001);
//
//            boolean res = dao.update(emp);
//            System.out.println(res);

        // Test butch
        EmployeeCarDAOImpl employeeCarDAO = new EmployeeCarDAOImpl();

        List<EmployeeCar> cars = new LinkedList<>();
        cars.add(new EmployeeCar("e444eee","opel",1));
        cars.add(new EmployeeCar("w555www","opel",1));
        cars.add(new EmployeeCar("z666zzz","opel",1));
        cars.add(new EmployeeCar("h777hhh","opel",1));

        employeeCarDAO.createAll(cars);

        for (EmployeeCar car : cars) {
            System.out.println(car.getCarId());
        }
    }
}
