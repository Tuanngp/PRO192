package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import model.Order;
import model.person.Customer;
import view.Validation;
public class CustomerManager {
    public ArrayList<Customer> customers = new ArrayList<>();

    public CustomerManager() {
        
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
                String id = Validation.checkValue(data[0], Validation.REGEX_ID_KH);
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
                String rank = Validation.checkValue(data[7], Validation.REGEX_RANK);
                Customer customer = new Customer(id, name, phone, address, gender, dateOfBirth, email, rank);
                this.customers.add(customer);
            } else {
                System.out.println("Invalid file data");
            }
        }
    } catch (Exception e) {
        System.err.println("Error occurred while loading customers from file: " + filename);
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
    
    public void addCustomers(Customer customer) {
        // if(customer != null && !isDupplication(customer.getId())) {
            customers.add(customer);
        // }
    }
//    ------------------------------------------------------------------------------
    public boolean isDupplication(String id) {
        return !search(p -> p.getId().equals(id)).isEmpty();
    }
//    ------------------------------------------------------------------------------
    public ArrayList<Customer> search(Predicate<Customer> predicate) {
        ArrayList<Customer> rs = new ArrayList<>();
        for (Customer customer : customers) {
            if (predicate.test(customer)) {
                rs.add(customer);
            }
        }
        return rs;
    }
    
    public Customer searchCustomerByID(String id) { 
        return this.customers.stream().filter(p -> p.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
//    ------------------------------------------------------------------------------
    public boolean updateCustomer(Customer cus, String id, String name, String phone, String address, String genderStr, LocalDate dateOfBirth, String email, String rank) {
        boolean updated = false;

        if(!id.trim().isEmpty()) {
            cus.setId(id);
            updated = true;
        }
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
        if(!rank.trim().isEmpty()) {
            cus.setRank(rank);
            updated = true;
        }
        return updated;
    }
//    ------------------------------------------------------------------------------
    public boolean deleteCustomer(String id, String name, String phone, String email) {
        ArrayList<Customer> customersDeleted = search(p -> p.getId().equalsIgnoreCase(id) 
                                                        || p.getName().equalsIgnoreCase(name)
                                                        || p.getPhone().equalsIgnoreCase(phone)
                                                        || p.getEmail().equalsIgnoreCase(email)) ;
        for(Customer cus: customersDeleted ) {
            customers.remove(cus);
        }
        return !customersDeleted.isEmpty();
    }
//    ------------------------------------------------------------------------------
    public void saveFileAndExit(String fileName) {
        Set<String> existingData = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingData.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading data from file");
            return;
        }

        try (FileWriter writer = new FileWriter(fileName, true)) {
            for (Customer customer: customers) {
                String customerString = customer.toString();
                if (!existingData.contains(customerString)) {
                    writer.write(customerString + "\n");
                }
            }
            System.out.println("Data written to file " + fileName + " successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while writing data to file");
        }
    }
}
