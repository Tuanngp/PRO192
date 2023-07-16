package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

import model.person.Employee;

public class EmployeeManager {
    ArrayList<Employee> employees;

    public EmployeeManager() {
        employees = new ArrayList<>();
    }

    public ArrayList<Employee> getListEmployees() {
        return employees;
    }

    public void displayEmployees() {
        employees.forEach(p -> System.out.println(p));

    }

    public void displayEmployees(ArrayList<Employee> employees) {
        employees.forEach(p -> System.out.println(p));
    }

    public boolean addEmployee(Employee employee) {
        if (employee != null && !isDupplication(employee.getId())) {
            employees.add(employee);
            return true;
        }
        return false;
    }

    // ---------------------------------------------------
    public boolean isDupplication(String id) {
        return !search(p -> p.getId().equals(id)).isEmpty();
    }

    // ------------------------------------------------------------------------------
    public ArrayList<Employee> search(Predicate<Employee> predicate) {
        ArrayList<Employee> rs = new ArrayList<>();
        for (Employee employee : employees) {
            if (predicate.test(employee)) {
                rs.add(employee);
            }
        }
        return rs;
    }

    // ------------------------------------------------------------------------------
    public boolean updateEmployee(Employee emp, String id, String name, String phone, String address, String genderStr,
            LocalDate dateOfBirth, String email, int dayWork, String role, float salary) {
        boolean updated = false;
        if (!name.trim().isEmpty()) {
            emp.setName(name);
            updated = true;
        }
        if (!phone.trim().isEmpty()) {
            emp.setPhone(phone);
            updated = true;
        }
        if (!address.trim().isEmpty()) {
            emp.setAddress(address);
            updated = true;
        }
        if (genderStr != null) {
            Boolean gender = Boolean.parseBoolean(genderStr);
            emp.setGender(gender);
            updated = true;
        }
        if (dateOfBirth != null) {
            emp.setDateOfBirth(dateOfBirth);
            updated = true;
        }
        if (!email.trim().isEmpty()) {
            emp.setEmail(email);
            updated = true;
        }
        if (String.valueOf(dayWork) != null) {
            emp.setDayWork(dayWork);
            updated = true;
        }
        if (!role.trim().isEmpty()) {
            emp.setRole(role);
            updated = true;
        }
        if (String.valueOf(salary) != null) {
            emp.setSalary(salary);
            updated = true;
        }

        return updated;
    }

    // ------------------------------------------------------------------------------
    public boolean deleteEmployee(String id, String name, String phone, String email) {
        ArrayList<Employee> employeesDeleted = search(p -> p.getId().equalsIgnoreCase(id)
                && p.getName().equalsIgnoreCase(name)
                && p.getPhone().equalsIgnoreCase(phone)
                && p.getEmail().equalsIgnoreCase(email));

        return !employeesDeleted.isEmpty();
    }
}
