package org.avj.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.avj.dao.EmployeeCarDAO;
import org.avj.dao.utils.DBConnector;
import org.avj.pojo.*;

public class EmployeeCarDAOImpl implements EmployeeCarDAO {

    @Override
    public void create(EmployeeCar e) {
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement prSt = connection.prepareStatement(SQLQuery.CREATE.QUERY)){
        prSt.setString(1,e.getReg_num());
        prSt.setString(2,e.getBrand_model());
        prSt.setInt(3,e.getEmployee_id());
        prSt.executeQuery().next();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<EmployeeCar> readAll() {
        List<EmployeeCar> list = new LinkedList<>();
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement prSt = connection.prepareStatement(SQLQuery.READ_ALL.QUERY)){
            ResultSet rs = prSt.executeQuery();
            while (rs.next()){
                EmployeeCar ec = new EmployeeCar();
                ec.setCar_id(rs.getInt("car_id"));
                ec.setReg_num(rs.getString("reg_num"));
                ec.setReg_num(rs.getString("brand_model"));
                ec.setCar_id(rs.getInt("employee_id"));
                list.add(ec);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public EmployeeCar readById(Integer key) {
        EmployeeCar ec = null;
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement prSt = connection.prepareStatement(SQLQuery.READ_BY_ID.QUERY)){
            prSt.setInt(1,key);
            ResultSet rs = prSt.executeQuery();
            while (rs.next()){
                ec = new EmployeeCar();
                ec.setCar_id(rs.getInt("car_id"));
                ec.setReg_num(rs.getString("reg_num"));
                ec.setReg_num(rs.getString("brand_model"));
                ec.setCar_id(rs.getInt("employee_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ec;
    }

    @Override
    public boolean update(EmployeeCar e) {
        boolean result = false;
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement prSt = connection.prepareStatement(SQLQuery.UPDATE.QUERY)){
            prSt.setString(1,e.getReg_num());
            prSt.setString(2,e.getBrand_model());
            prSt.setInt(3, e.getEmployee_id());
            prSt.setInt(4, e.getCar_id());
            result = prSt.executeQuery().next();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public void deleteById(Integer key) {
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement prSt = connection.prepareStatement(SQLQuery.DELETE.QUERY)){
            prSt.setInt(1,key);
            ResultSet rs = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    enum SQLQuery {
        CREATE("INSERT INTO employee_car (reg_num, brand_model, employee_id) VALUES (?,?,?) RETURNING car_id;"),
        READ_ALL("SELECT * FROM employee_cars;"),
        READ_BY_ID("SELECT * FROM employee_cars AS ec WHERE ec.car_id=(?);"),
        UPDATE("UPDATE employee_car SET reg_num=(?), brand_model=(?), employee_id=(?) WHERE car_id=(?) RETURNING car_id;"),
        DELETE("DELETE FROM employee_car WHERE car_id = (?) RETURNING car_id;");
        final String QUERY;
        SQLQuery(String query){
            this.QUERY = query;
        }
    }
}
