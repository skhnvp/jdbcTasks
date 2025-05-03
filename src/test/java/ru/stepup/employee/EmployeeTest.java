package ru.stepup.employee;

import org.junit.jupiter.api.Test;
import ru.stepup.BaseTest;

import java.sql.*;

public class EmployeeTest extends BaseTest {

    @Test
    void findAnn() {
        //String sql = "SELECT * FROM Employee e WHERE e.NAME = ?";
        String sql = "SELECT * FROM Employee e";

        try (Connection conn = DriverManager.getConnection(getProperty("db.url"))){
            if (conn != null) {
                PreparedStatement prepSt = conn.prepareStatement(sql);
                //prepSt.setString(1, "Ann");
                ResultSet rs = prepSt.executeQuery();

                while (rs.next()) {
                    System.out.println(rs.getString( "id") + " " + rs.getString("Name"));
                }

            } else {
                throw new RuntimeException("Подключение не создано");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
