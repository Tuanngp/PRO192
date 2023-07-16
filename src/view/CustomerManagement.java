package view;

import controller.CustomerManager;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Customer;

public class CustomerManagement extends Menu<String>{
    static String[] customersMenu = {"Display All Customer","Add Customer", "Search Customer", "Update Customer", "Delete Customer", "Exit"};
    CustomerManager customerManager = new CustomerManager();
    public CustomerManagement() {
        super("Customer Management System", customersMenu );
    }
   
    @Override
    public void execute(String selected) {
        switch(selected) {
            case "1":
                displayCustomers();
                break;
            case "2":
                addCustomer();
                break;
            case "3":
                searchCustomer();
                break;
            case "4":
                updateCustomer();
                break;
            case "5":
                deleteCustomer();
                break;
            case "6":
                System.out.println("Exit Customer Management System!");
                break;
            default:
                System.out.println("[ERROR] Invalid input! Please try again.");
                break;
        }
    }
    
    public void displayCustomers() {
        System.out.println("List all customers: ");
        customerManager.displayCustomers();
        System.out.println("Total: " + customerManager.getListCustomers().size() + " Customers");
    }
    public void displayCustomers(ArrayList<Customer> customers) {
        System.out.println("List all customers: ");
        customerManager.displayCustomers(customers);
        System.out.println("Total: " + customerManager.getListCustomers().size() + " Customers");
    }
    
    public void addCustomer() {
        String id = Validation.getString("(*)Enter customer's id: ", Validation.REGEX_ID);
        String name = Validation.getString("(*)Enter customer's name: ", Validation.REGEX_NAME);
        String phone = Validation.getString("(*)Enter customer's phone:", Validation.REGEX_NUMBER);
        String address = Validation.getString("Enter Customer Address: ", Validation.REGEX_ADDRESS);
        Boolean gender = Boolean.parseBoolean(Validation.getString("Enter Customer Gender (true=male|false=female): ", Validation.REGEX_GENDER));
        LocalDate dateOfBirth = Validation.getDate("Enter customer's date of birth: ");
        String email = Validation.getString("Enter customer's email: ", Validation.REGEX_EMAIL);
        int dayRent = Validation.getDayWork("(*)Enter the number of rental days: ");
        Customer customer = new Customer(id, name, phone, address, gender, dateOfBirth, email, dayRent);
        if(customerManager.addCustomer(customer)) { 
            System.out.println("Customer " + id + " has been added successfully.");
        } else {
            System.out.println("[Error] Unable to add customer.");
        }
    }
    
    public void searchCustomer() {
        String[] searchOptions = {"Search Customer by ID", "Search Customer by Name", "Search Customer by Phone", "Search Customer by Email", "Exit"};
        Menu searchMenu = new Menu("Searching Customer",searchOptions) {
            @Override
            public void execute(String selected) {
                ArrayList<Customer> rs = null;
                switch(selected){
                    case "1":
                        String val = Validation.getString("Enter ID's Customer you want to search", Validation.REGEX_ID);
                        rs = customerManager.search(p -> p.getId().equalsIgnoreCase(val));
                        break;
                    case "2":
                        val = Validation.getString("Enter Name's Customer you want to search", Validation.REGEX_NAME);
                        rs = customerManager.search(p->p.getName().equalsIgnoreCase(val));
                        break;
                    case "3":
                        val = Validation.getString("Enter Phone's Customer you want to search");
                        rs = customerManager.search(p->p.getPhone().equals(val));
                        break;
                    case "4":
                        val = Validation.getString("Enter Email's Customer you want to search");
                        rs = customerManager.search(p->p.getEmail().equals(val));
                        break;
                    case "5":
                        System.out.println("Exit Searching Menu!");
                    default: 
                        System.out.println("[ERROR] Invalid input! Please try again.");
                        break;
                }
                    if(rs.isEmpty()) {
                        System.out.println("Empty list! No search can be perfomred.");
                    } else {
                        System.out.println(rs);
                    }
            }
        };
        searchMenu.run();
    }
    
    public void updateCustomer() {
        System.out.println("Updating Customer");
        System.out.println("Input information you want to update (Enter to pass): ");
        // get Customer to Update 
        String id = Validation.getString("Enter ID's Customer you want to update: ", Validation.REGEX_ID);
        Customer customer = customerManager.search(p -> p.getId().equalsIgnoreCase(id)).get(0);
        // Enter new information
        String idNew = Validation.getString("Enter new ID customer: ", Validation.REGEX_ID);
        String name = Validation.getString("Enter new name's customer: ",Validation.REGEX_NAME);
        String phone = Validation.getString("Enter new phone's customer: ", Validation.REGEX_NUMBER);
        LocalDate dateOfBirthStr = Validation.getDate("Enter new date of birth(dd/MM/yyyy): ");
        String address = Validation.getString("Enter new address: ",Validation.REGEX_ADDRESS);
        String genderStr = Validation.getString("Enter new gender((true = male;false = female)",Validation.REGEX_GENDER);
        String email = Validation.getString("Enter new email: ", Validation.REGEX_EMAIL);
        int dayRent = Validation.getDayWork("Enter new Day Rent: ");
        // check Update 
        if(customerManager.updateCustomer(customer, idNew, name, phone, address, genderStr, dateOfBirthStr, email, dayRent)) {
            System.out.println("Customer " + customer.getId() + " has been updated successfully.");
        } else {
            System.out.println("[ERROR] Unable to update customer.");
        }
    }

    public void deleteCustomer() {
        String[] deleteOptions = {"Delete Customer by ID", "Delete Customer by Name", "Delete Customer by Phone", "Delete Customer by Email", "Exit"};
        Menu deleteMenu = new Menu("Deleting Customer", deleteOptions) {
            @Override
            public void execute(String selected) {
                switch(selected) {
                    case "1":
                        String id = Validation.getString("Enter Customer ID: ", Validation.REGEX_ID);
                        if(customerManager.deleteCustomer(id, null, null, null)) {
                            System.out.println("Customer " + id + " has been deteted successfully." );
                        } else {
                            System.out.println("[ERROR] Unable to delete customer.");
                        }
                        break;
                    case "2":
                        String name = Validation.getString("Enter Customer Name: ", Validation.REGEX_NAME);
                        if(customerManager.deleteCustomer(null, name, null, null)) {
                            System.out.println("Customer " + name + " has been deteted successfully." );
                        } else {
                            System.out.println("[ERROR] Unable to delete customer.");
                        }
                        break;
                    case "3":
                        String phone = Validation.getString("Enter Customer Phone: ", Validation.REGEX_NUMBER);
                        if(customerManager.deleteCustomer(null, null, phone, null)) {
                            System.out.println("Customer have Phone number: " + phone + " has been deteted successfully." );
                        } else {
                            System.out.println("[ERROR] Unable to delete customer.");
                        }
                        break;
                    case "4":
                        String email = Validation.getString("Enter Customer Email: ", Validation.REGEX_EMAIL);
                        if(customerManager.deleteCustomer(null, null, null, email)) {
                            System.out.println("Customer have email: " + email + " has been deteted successfully." );
                        } else {
                            System.out.println("[ERROR] Unable to delete customer.");
                        }
                        break;
                }
            }
        };
    }
    
}
