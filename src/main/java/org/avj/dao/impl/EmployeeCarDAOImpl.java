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
    public void create(EmployeeCar car) {
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement prSt = connection.prepareStatement(SQLQuery.CREATE.QUERY, new String[]{"car_id"})){
        prSt.setString(1,car.getRegNum());
        prSt.setString(2,car.getBrandModel());
        prSt.setInt(3,car.getEmployeeId());
        prSt.executeUpdate();
        ResultSet result = prSt.getGeneratedKeys();
        if (result.next()) {
            car.setCarId(result.getInt("car_id"));
        }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void createAll(List<EmployeeCar> cars) {
        try(Connection connection = DBConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLQuery.CREATE.QUERY, new String[]{"car_id"})) {
            for (EmployeeCar car : cars) {
                statement.setString(1, car.getRegNum());
                statement.setString(2, car.getBrandModel());
                statement.setInt(3, car.getEmployeeId());
                statement.addBatch();
            }
            statement.executeBatch();
            ResultSet resultSet = statement.getGeneratedKeys();
            for (EmployeeCar car : cars) {
                resultSet.next();
                car.setCarId(resultSet.getInt("car_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                ec.setCarId(rs.getInt("car_id"));
                ec.setRegNum(rs.getString("reg_num"));
                ec.setRegNum(rs.getString("brand_model"));
                ec.setCarId(rs.getInt("employee_id"));
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
                ec.setCarId(rs.getInt("car_id"));
                ec.setRegNum(rs.getString("reg_num"));
                ec.setRegNum(rs.getString("brand_model"));
                ec.setCarId(rs.getInt("employee_id"));
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
            prSt.setString(1,e.getRegNum());
            prSt.setString(2,e.getBrandModel());
            prSt.setInt(3, e.getEmployeeId());
            prSt.setInt(4, e.getCarId());
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
        CREATE("INSERT INTO employee_car (reg_num, brand_model, employee_id) VALUES (?,?,?);"),
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
