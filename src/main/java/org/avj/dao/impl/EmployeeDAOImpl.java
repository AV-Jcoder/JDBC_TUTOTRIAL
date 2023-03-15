package org.avj.dao.impl;

import org.avj.dao.EmployeeDAO;
import org.avj.dao.utils.DBConnector;
import org.avj.pojo.Employee;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public void create(Employee employee) {
        String sqlQuery = "INSERT INTO employee (name,phone,email,post,salary) VALUES (?,?,?,?,?);";
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement prepSt = connection.prepareStatement(sqlQuery)){
            prepSt.setString(1,employee.getName());
            prepSt.setString(2,employee.getPhone());
            prepSt.setString(3,employee.getEmail());
            prepSt.setString(4,employee.getPost());
            prepSt.setLong(5,employee.getSalary());
            int result = prepSt.executeUpdate();
            System.out.printf("CREATED %d.\n",result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> readAll() {
        List<Employee> list = null;
        String sqlQuery = "SELECT * FROM employee;";
        try(Connection connection = DBConnector.getConnection();
            Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sqlQuery);
            list = new LinkedList<Employee>();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setPost(rs.getString("post"));
                employee.setSalary(rs.getLong("salary"));
                list.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Employee readById(Integer id) {
        Employee employee = null;
        String sqlQuery = "SELECT * FROM employee WHERE id=(?);";
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement prepSt = connection.prepareStatement(sqlQuery)){
            prepSt.setInt(1,id);
            ResultSet rs = prepSt.executeQuery();
            employee = new Employee();
            if (rs.next()) {
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setPost(rs.getString("post"));
                employee.setSalary(rs.getLong("salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public boolean update(Employee employee) {
        boolean result = false;
        int id = employee.getId();
        String name = employee.getName();
        String phone = employee.getPhone();
        String email = employee.getEmail();
        String post = employee.getPost();
        long salary = employee.getSalary();
        String sqlQuery = "UPDATE employee SET name=(?),phone=(?),email=(?),post=(?),salary=(?) WHERE id = (?);";
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement pst = connection.prepareStatement(sqlQuery)) {
            pst.setString(1,name);
            pst.setString(2,phone);
            pst.setString(3,email);
            pst.setString(4,post);
            pst.setLong(5,salary);
            pst.setInt(6,id);
            result = pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        String sqlQuery = "DELETE FROM employee WHERE id = (?);";
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement pst = connection.prepareStatement(sqlQuery)) {
            pst.setInt(1,id);
            int rs = pst.executeUpdate();
            System.out.printf("DELETED %d.\n",rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
