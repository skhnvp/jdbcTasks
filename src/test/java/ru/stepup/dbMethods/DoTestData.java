package ru.stepup.dbMethods;

import ru.stepup.BaseTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DoTestData extends BaseTest {
    public static void doData() {
        try (Connection conn = DriverManager.getConnection(getProperty("db.url"))) {
            Statement stm = conn.createStatement();

            stm.executeUpdate("DROP TABLE Employee IF EXISTS");
            stm.executeUpdate("CREATE TABLE Employee(ID INT PRIMARY KEY, NAME VARCHAR(255), DepartmentID INT)");
            stm.executeUpdate("INSERT INTO Employee VALUES(1,'pete',1)");
            stm.executeUpdate("INSERT INTO Employee VALUES(2,'Ann',1)");
            stm.executeUpdate("INSERT INTO Employee VALUES(3,'liz',2)");
            stm.executeUpdate("INSERT INTO Employee VALUES(4,'Tom',2)");
            stm.executeUpdate("INSERT INTO Employee VALUES(5,'todd',3)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
