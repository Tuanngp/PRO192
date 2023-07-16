package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Predicate;

import model.person.Customer;
import view.Validation;
public class CustomerManager {
    ArrayList<Customer> customers;

    public CustomerManager() {
        customers = new ArrayList<>();
    }
    
    public ArrayList<Customer> getListCustomers() {
        return customers;
    }
    
    public void loadCustomersFromFile(String filename) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 8) {
                String id = Validation.checkValue(data[0].substring(2),Validation.REGEX_ID);
                String name = Validation.checkValue(data[1],Validation.REGEX_NAME);
                String phone = Validation.checkValue(data[2], Validation.REGEX_NUMBER);
                String address = Validation.checkValue(data[3], Validation.REGEX_ADDRESS);
                String genderStr = Validation.checkValue(data[4],"(i?)male|female");
                boolean gender;
                if (genderStr.equalsIgnoreCase("male")) gender = true;
                else gender = false;
                String dateOfBirthStr = Validation.checkValue(data[5], Validation.DATE_FORMAT);
                LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern(Validation.DATE_FORMAT));
                String email = Validation.checkValue(data[6], Validation.REGEX_EMAIL);
                String rank = Validation.checkValue(data[7],Validation.REGEX_RANK);
                customers.add(new Customer(id, name, phone, address, gender, dateOfBirth, email, rank));
                }
            else {
                System.out.println("Invalid file data");
            }
          }
        }catch (Exception e) {
        System.out.println("Error occurred while loading customers from file: " + filename);
        e.printStackTrace();
        }
    }
    
    public void displayCustomers() {
        customers.forEach(p -> System.out.println(p));
    }
    
    public void displayCustomers(ArrayList<Customer> customers) {
        customers.forEach(p -> System.out.println(p));
    }
    
    public boolean addCustomer(Customer customer) {
        if(customer != null && !isDupplication(customer.getId())) {
            customers.add(customer);
            return true;
        }
        return false;
    }
    
//    ------------------------------------------------------------------------------
    public boolean isDupplication(String id) {
        return !search(p -> p.getId().equals(id)).isEmpty();
    }
//    ------------------------------------------------------------------------------
    public ArrayList<Customer> search(Predicate<Customer> p) {
        ArrayList<Customer> customerSearched = new ArrayList<>();
        for(Customer cus : customers) customerSearched.add(cus);
        return customerSearched;
    }
//    ------------------------------------------------------------------------------
    public boolean updateCustomer(Customer cus, String id, String name, String phone, String address, String genderStr, LocalDate dateOfBirth, String email, String rank) {
        boolean updated = false;
        if (!name.trim().isEmpty()) {
            cus.setName(name);
            updated = true;
        }
        if (!phone.trim().isEmpty()) {
            cus.setPhone(phone);
            updated = true;
        }
        if (!address.trim().isEmpty()) {
            cus.setAddress(address);
            updated = true;
        }
        if (genderStr != null) {
            Boolean gender = Boolean.parseBoolean(genderStr);
            cus.setGender(gender);
            updated = true;
        }
        if (dateOfBirth != null) {
            cus.setDateOfBirth(dateOfBirth);
            updated = true;
        }
        if (!email.trim().isEmpty()) {
            cus.setEmail(email);
            updated = true;
        }
        return updated;
    }
//    ------------------------------------------------------------------------------
    public boolean deleteCustomer(String id, String name, String phone, String email) {
        ArrayList<Customer> customersDeleted = search(p -> p.getId().equalsIgnoreCase(id) 
                                                                && p.getName().equalsIgnoreCase(name)
                                                                && p.getPhone().equalsIgnoreCase(phone)
                                                                && p.getEmail().equalsIgnoreCase(email)) ;
        
        return !customersDeleted.isEmpty();
    }
//    ------------------------------------------------------------------------------
}
