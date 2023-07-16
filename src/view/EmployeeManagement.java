package view;

import java.time.LocalDate;
import java.util.ArrayList;

import controller.EmployeeManager;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import model.person.Employee;

public class EmployeeManagement extends Menu<String> {
    static String[] EmployeeMenu = { "Display All Employee", "Add Employee", "Search Employee", "Update Employee",
            "Delete Employee", "Exit" };
    EmployeeManager employeeManager = new EmployeeManager();

    public EmployeeManagement() {
        super("Employee Management System", EmployeeMenu);
        try{
        employeeManager.loadEmployeesFromFile("employee.txt");
        }
        catch(IOException e){
            e.printStackTrace();;
        }
    }

    @Override
    public void execute(String selected) {
        switch (selected) {
            case "1":
                displayEmployees();
                break;
            case "2":
                addEmployee();
                break;
            case "3":
                searchEmployee();
                break;
            case "4":
                updateEmployee();
                break;
            case "5":
                deleteEmployee();
                break;
            case "6":
                employeeManager.saveFileAndExit("employee_output.txt");
                System.out.println("Exit Employee Management System!");
                return;
            default:
                System.out.println("[ERROR] Invalid input! Please try again.");
                break;
        }
    }

    public void displayEmployees() {
        System.out.println("List all Employees: ");
        employeeManager.displayEmployees();
        System.out.println("Total: " + employeeManager.getListEmployees().size() + " Employees");
    }

    public void displayEmployees(ArrayList<Employee> Employees) {
        System.out.println("List all Employees: ");
        employeeManager.displayEmployees(Employees);
        System.out.println("Total: " + employeeManager.getListEmployees().size() + " Employees");
    }

    public void addEmployee() {
        String id = Validation.getString("(*)Enter Employee's id: ", Validation.REGEX_ID);
        String name = Validation.getString("(*)Enter Employee's name: ", Validation.REGEX_NAME);
        String phone = Validation.getString("(*)Enter Employee's phone:", Validation.REGEX_NUMBER);
        String address = Validation.getString("Enter Employee Address: ", Validation.REGEX_ADDRESS);
        Boolean gender = Boolean.parseBoolean(
                Validation.getString("Enter Employee Gender (true=male|false=female): ", Validation.REGEX_GENDER));
        String dateOfBirthStr = Validation.getString("Enter Employee's date of birth:", Validation.DATE_FORMAT);
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern(Validation.DATE_FORMAT));
        String email = Validation.getString("Enter Employee's email: ", Validation.REGEX_EMAIL);
        int dayWork = Validation.getDayWork("Enter Employee's DayWork:");
        String role = Validation.getString("Enter Employee's Role:", Validation.REGEX_ROLE);
        Employee employee = new Employee(id, name, phone, address, gender, dateOfBirth, email, dayWork, role);
        if (employeeManager.addEmployee(employee)) {
            System.out.println("Employee " + id + " has been added successfully.");
        } else {
            System.out.println("[Error] Unable to add Employee.");
        }
    }

    public void searchEmployee() {
        String[] searchOptions = { "Search Employee by ID", "Search Employee by Name", "Search Employee by Phone",
                "Search Employee by Email", "Exit" };
        Menu searchMenu = new Menu("Searching Employee", searchOptions) {
            @Override
            public void execute(String selected) {
                ArrayList<Employee> rs = null;
                switch (selected) {
                    case "1":
                        String val = Validation.getString("Enter ID's Employee you want to search", Validation.REGEX_ID);
                        rs = employeeManager.search(p -> p.getId().equalsIgnoreCase(val));
                        break;
                    case "2":
                        val = Validation.getString("Enter Name's Employee you want to search", Validation.REGEX_NAME);
                        rs = employeeManager.search(p -> p.getName().equalsIgnoreCase(val));
                        break;
                    case "3":
                        val = Validation.getString("Enter Phone's Employee you want to search");
                        rs = employeeManager.search(p -> p.getPhone().equals(val));
                        break;
                    case "4":
                        val = Validation.getString("Enter Email's Employee you want to search");
                        rs = employeeManager.search(p -> p.getEmail().equalsIgnoreCase(val));
                        break;
                    case "5":
                        System.out.println("Exit Searching Menu!");
                        break;
                    default:
                        System.out.println("[ERROR] Invalid input! Please try again.");
                        break;
                }
                if (rs.isEmpty()) {
                    System.err.println("Empty list! No search can be perfomred.");
                } else {
                    System.out.println(rs);
                }
            }
        };
        searchMenu.run();
    }

    public void updateEmployee() {
        System.out.println("Updating Employee");
        System.out.println("Input information you want to update (Enter to pass): ");
        // get Employee to Update
        String id = Validation.getString("Enter ID's Employee you want to update: ", Validation.REGEX_ID);
        Employee employee = employeeManager.search(p -> p.getId().equalsIgnoreCase(id)).get(0);
        // Enter new information
        String idNew = Validation.getString("Enter new ID Employee: ", Validation.REGEX_ID);
        String name = Validation.getString("Enter new name's Employee: ", Validation.REGEX_NAME);
        String phone = Validation.getString("Enter new phone's Employee: ", Validation.REGEX_NUMBER);
        LocalDate dateOfBirthStr = Validation.getLocalDate(Validation.getDate("Enter new date of birth(dd/MM/yyyy): "));
        String address = Validation.getString("Enter new address: ", Validation.REGEX_ADDRESS);
        String genderStr = Validation.getString("Enter new gender((true = male;false = female)",
                Validation.REGEX_GENDER);
        String email = Validation.getString("Enter new email: ", Validation.REGEX_EMAIL);
        int dayWork = Validation.getDayWork("Enter Employee's DayWork:");
        String role = Validation.getString("Enter Employee's Role:", Validation.REGEX_ROLE);
        // check Update
        if (employeeManager.updateEmployee(employee, idNew, name, phone, address, genderStr, dateOfBirthStr, email,
                dayWork, role)) {
            System.out.println("Employee " + employee.getId() + " has been updated successfully.");
        } else {
            System.out.println("[ERROR] Unable to update Employee.");
        }
    }

    public void deleteEmployee() {
        String[] deleteOptions = { "Delete Employee by ID", "Delete Employee by Name", "Delete Employee by Phone",
                "Delete Employee by Email", "Exit" };
        Menu deleteMenu = new Menu("Deleting Employee", deleteOptions) {
            @Override
            public void execute(String selected) {
                switch (selected) {
                    case "1":
                        String id = Validation.getString("Enter Employee ID: ", Validation.REGEX_ID);
                        if (employeeManager.deleteEmployee(id, null, null, null)) {
                            System.out.println("Employee " + id + " has been deteted successfully.");
                        } else {
                            System.out.println("[ERROR] Unable to delete Employee.");
                        }
                        break;
                    case "2":
                        String name = Validation.getString("Enter Employee Name: ", Validation.REGEX_NAME);
                        if (employeeManager.deleteEmployee(null, name, null, null)) {
                            System.out.println("Employee " + name + " has been deteted successfully.");
                        } else {
                            System.out.println("[ERROR] Unable to delete Employee.");
                        }
                        break;
                    case "3":
                        String phone = Validation.getString("Enter Employee Phone: ", Validation.REGEX_NUMBER);
                        if (employeeManager.deleteEmployee(null, null, phone, null)) {
                            System.out.println(
                                    "Employee have Phone number: " + phone + " has been deteted successfully.");
                        } else {
                            System.out.println("[ERROR] Unable to delete Employee.");
                        }
                        break;
                    case "4":
                        String email = Validation.getString("Enter Employee Email: ", Validation.REGEX_EMAIL);
                        if (employeeManager.deleteEmployee(null, null, null, email)) {
                            System.out.println("Employee have email: " + email + " has been deteted successfully.");
                        } else {
                            System.out.println("[ERROR] Unable to delete Employee.");
                        }
                        break;
                }
            }
        };
        deleteMenu.run();
    }
}
