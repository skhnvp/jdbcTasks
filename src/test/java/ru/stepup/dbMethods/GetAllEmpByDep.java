package ru.stepup.dbMethods;

import ru.stepup.BaseTest;
import ru.stepup.dto.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetAllEmpByDep extends BaseTest {
    public static List<Employee> get(int departmentId) {
        String selectAnnSql = "SELECT * FROM Employee e WHERE e.departmentId = ?";
        List<Employee> emps = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(getProperty("db.url"));
                PreparedStatement prepSt = conn.prepareStatement(selectAnnSql)){
            prepSt.setInt(1, departmentId);

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
