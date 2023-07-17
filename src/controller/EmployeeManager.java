package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Predicate;

import model.person.Employee;
import view.Validation;

public class EmployeeManager {
    protected ArrayList<Employee> employees= new ArrayList<>();
 
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
            LocalDate dateOfBirth, String email, int dayWork, String role) {
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
//        if (String.valueOf(salary) != null) {
//            emp.setSalary(salary);
//            updated = true;
//        }

        return updated;
    }

    // ------------------------------------------------------------------------------
    public boolean deleteEmployee(String id, String name, String phone, String email) {
        ArrayList<Employee> employeesDeleted = search(p -> p.getId().equalsIgnoreCase(id)
                || p.getName().equalsIgnoreCase(name)
                || p.getPhone().equalsIgnoreCase(phone)
                || p.getEmail().equalsIgnoreCase(email));

                for (Employee employee : employeesDeleted) {
                    employees.remove(employee);
                }
        //         for(Customer cus: customersDeleted ) {
        //     customers.remove(cus);
        // }
        return !employeesDeleted.isEmpty();
       
    }
    //-----------------------------------------------------------------------------
    public void loadEmployeesFromFile(String filename) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 9) {
                String id = Validation.checkValue(data[0], Validation.REGEX_ID_NV);
                String name = Validation.checkValue(data[1], Validation.REGEX_NAME);
                String phone = Validation.checkValue(data[2], Validation.REGEX_NUMBER);
                String address = Validation.checkValue(data[3], Validation.REGEX_ADDRESS);
                String genderStr = Validation.checkValue(data[4], "(?i)male|female");
                boolean gender;
                if (genderStr != null && genderStr.equalsIgnoreCase("male")) {
                    gender = true;
                } else {
                    gender = false;
                }
                String dateOfBirthStr = data[5];
                LocalDate dateOfBirth = null;
                if (dateOfBirthStr != null) {
                    dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern(Validation.DATE_FORMAT));
                }
                String email = Validation.checkValue(data[6], Validation.REGEX_EMAIL);
                String dayWorkStr = Validation.checkValue(data[7], Validation.REGEX_DAYWORK);
                int dayWork = Integer.parseInt(dayWorkStr);
                String role = Validation.checkValue(data[8], Validation.REGEX_ROLE);
                employees.add(new Employee(id, name, phone, address, gender, dateOfBirth, email, dayWork, role));
            } else {
                System.out.println("Invalid file data");
            }
        }
    } catch (Exception e) {
        System.out.println("Error occurred while loading customers from file: " + filename);
        e.printStackTrace();
    }
    }
    //-------------------------------------------------------------------------
    public void saveFileAndExit(String fileName){
        try (FileWriter writer = new FileWriter(fileName)) {
        for (Employee employee : employees) {
            writer.write(employee.toString()+"\n");
        }
        System.out.println("Data written to file "+fileName+" successfully.");
        } catch (Exception e) {
        System.out.println("Error occurred while writing data to file");
        }
    }
}

