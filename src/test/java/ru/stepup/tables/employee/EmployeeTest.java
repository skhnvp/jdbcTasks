package ru.stepup.tables.employee;

import org.junit.jupiter.api.*;
import ru.stepup.BaseTest;
import ru.stepup.dbMethods.*;
import ru.stepup.dto.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EmployeeTest extends BaseTest {

    @BeforeAll
    static void doTestData(){
        DoTestData.doData();
    }

    @AfterAll
    static void rollback(){
        DBRollback.rollback();
    }

    @Test
    @Order(1)
    void updateAnnDepartment() {
        String name = "Ann";
        int department = 3;
        List<Employee> emps = GetAllEmpByName.get(name);

        Assertions.assertEquals(1, emps.stream().filter(emp -> emp.getName().equals(name)).count());
        Optional<Integer> annId = emps.stream().filter(emp -> emp.getName().equals(name)).map(Employee::getId).findFirst();

        if (!annId.isEmpty()) {
            String updateAnnSql = "UPDATE Employee SET departmentid = ? WHERE id = ?";

            try (Connection conn = DriverManager.getConnection(getProperty("db.url"));
                 PreparedStatement prepSt = conn.prepareStatement(updateAnnSql)) {

                prepSt.setInt(1, department);
                prepSt.setInt(2, annId.get());
                prepSt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        List<Employee> checkAnnsDep = GetAllEmpByName.get(name);
        Assertions.assertEquals(1, checkAnnsDep.stream().filter(emp -> emp.getName().equals(name)).count());
        Assertions.assertEquals(3, checkAnnsDep.get(0).getDepartmentId());
    }

    @Test
    @Order(2)
    void firstCharToUpperCase() {
        List<Employee> emps = GetAllEmp.get();

        List<Employee> lowerCaseEmps = emps.stream().filter(emp -> Character.isLowerCase(emp.getName().charAt(0))).collect(Collectors.toList());

        if (!lowerCaseEmps.isEmpty()) {
            System.out.println("Были исправлены следующие имена:");

            String updateEmpsSql = "UPDATE Employee SET name = ? WHERE id = ?";

            try (Connection conn = DriverManager.getConnection(getProperty("db.url"));
                 PreparedStatement prepSt = conn.prepareStatement(updateEmpsSql)) {

                for (Employee lowerCaseEmp : lowerCaseEmps) {
                    System.out.println(Character.toUpperCase(lowerCaseEmp.getName().charAt(0)) + lowerCaseEmp.getName().substring(1));

                    prepSt.setString(1, Character.toUpperCase(lowerCaseEmp.getName().charAt(0)) + lowerCaseEmp.getName().substring(1));
                    prepSt.setInt(2, lowerCaseEmp.getId());
                    prepSt.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        Assertions.assertEquals(new ArrayList<>(), GetAllEmp.get().stream().filter(emp -> Character.isLowerCase(emp.getName().charAt(0))).collect(Collectors.toList()));
    }

    @Test
    @Order(3)
    void empsSum() {
        int sizeITDep = GetAllEmpByDep.get(2).size();

        System.out.println("Кол-во сотрудников: " + sizeITDep);

        Assertions.assertEquals(2, sizeITDep);
    }

    @Test
    @Order(4)
    void whenDepIsDeleted() {
        /**
        * «При удалении отдела (Department) информация о всех сотрудниках, работающих в этом отделе, должна быть удалена».
         **/

        int depId = 2;
        List<Employee> EmpsBeforeDel = GetAllEmp.get();
        System.out.println(EmpsBeforeDel);

        String updateAnnSql = "DELETE FROM Department WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(getProperty("db.url"));
             PreparedStatement prepSt = conn.prepareStatement(updateAnnSql)) {

            prepSt.setInt(1, depId);
            prepSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Employee> EmpsAfterDel = GetAllEmp.get();
        System.out.println(EmpsAfterDel);


        Assertions.assertTrue(EmpsAfterDel.stream().noneMatch(emp -> emp.getDepartmentId() == depId),
                "Не должно быть сотрудников с departmentId: " + depId);
    }
}