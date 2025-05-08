package ru.stepup.unitTests.classes;

import org.junit.jupiter.api.*;
import ru.stepup.BaseTest;
import ru.stepup.Department;
import ru.stepup.Employee;
import ru.stepup.Service;
import ru.stepup.dbTests.dbMethods.*;

import ru.stepup.dbTests.dto.EmployeeDTO;

import java.util.List;
import java.util.Objects;

public class ServiceTest extends BaseTest {

    @BeforeAll
    static void doTestData(){
        DoTestData.doData();
    }

    @AfterEach
    void rollback(){
        DBRollback.rollback();
    }

    @Test
    @Order(1)
    void addDepartmentTest() {
        int depId = 4;
        String depName = "StepUP";

        Service.addDepartment(new Department(depId, depName));

        List<Department> deps = GetAllDeps.get();

        Assertions.assertTrue(deps.stream().anyMatch(d -> d.getDepartmentID() == depId),
                "Должен быть департамент с id: " + depId);
    }

    @Test
    @Order(2)
    void removeDepartmentTest() {
        int depId = 1;
        String depName = "Accounting";

        Service.removeDepartment(new Department(depId, depName));

        List<Department> deps = GetAllDeps.get();
        List<Employee> emps = GetAllEmp.get();

        Assertions.assertTrue(deps.stream().noneMatch(d -> d.getDepartmentID() == depId),
                "Не должно быть департаментов с departmentId: " + depId);
        Assertions.assertTrue(emps.stream().noneMatch(e -> e.getDepartmentId() == depId),
                "Не должно быть сотрудников с departmentId: " + depId);
    }

    @Test
    @Order(3)
    void addEmployeeTest() {
        int empId = 10;
        String empName = "Alex";
        int depId = 1;

        Service.addEmployee(new ru.stepup.Employee(empId, empName, depId));

        List<Employee> emps = GetAllEmp.get();

        Assertions.assertTrue(emps.stream().anyMatch(e -> e.getEmployeeId() == empId && Objects.equals(e.getName(), empName) && e.getDepartmentId() == depId),
                "Должен быть найден сотрудник с EmployeeId: " + empId + ", Name: " + empName + ", DepartmentId: " + depId);
    }

    @Test
    @Order(4)
    void removeEmployeeTest() {
        int empId = 11;
        String empName = "Ben";
        int depId = 2;

        Employee emp = new Employee(empId, empName, depId);

        Service.addEmployee(emp);

        List<Employee> empsBeforeDeleting = GetAllEmp.get();

        Assertions.assertTrue(empsBeforeDeleting.stream().anyMatch(e -> e.getEmployeeId() == empId && Objects.equals(e.getName(), empName) && e.getDepartmentId() == depId),
                "Должен быть найден сотрудник с EmployeeId: " + empId + ", Name: " + empName + ", DepartmentId: " + depId);

        Service.removeEmployee(emp);

        List<Employee> empsAfterDeleting = GetAllEmp.get();

        Assertions.assertTrue(empsAfterDeleting.stream().noneMatch(e -> e.getEmployeeId() == empId && Objects.equals(e.getName(), empName) && e.getDepartmentId() == depId),
                "Должен быть найден сотрудник с EmployeeId: " + empId + ", Name: " + empName + ", DepartmentId: " + depId);
    }
}
