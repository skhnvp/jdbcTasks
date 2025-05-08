package ru.stepup.dbTests.dbMethods;

import ru.stepup.BaseTest;
import ru.stepup.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetAllDeps extends BaseTest {
    public static List<Department> get() {
        String selectEmpsSql = "SELECT * FROM Department";
        List<Department> deps = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(getProperty("test.db.url"));
             PreparedStatement prepSt = conn.prepareStatement(selectEmpsSql)) {

            try (ResultSet rs = prepSt.executeQuery()) {
                while (rs.next()) {
                    deps.add(new Department(rs.getInt("id"), rs.getString("Name")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return deps;
    }


}
