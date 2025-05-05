package ru.stepup.dbMethods;

import ru.stepup.BaseTest;
import ru.stepup.dto.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetAllEmpByName extends BaseTest {
    public static List<Employee> get(String name) {
        String selectAnnSql = "SELECT * FROM Employee e WHERE e.NAME = ?";
        List<Employee> emps = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(getProperty("db.url"));
                PreparedStatement prepSt = conn.prepareStatement(selectAnnSql)){
            prepSt.setString(1, name);

            try (ResultSet rs = prepSt.executeQuery()) {
                while (rs.next()) {
                    emps.add(new Employee(rs.getInt( "id"),rs.getString("Name"),rs.getInt("departmentId")));
                }
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

        return emps;
    }
}
