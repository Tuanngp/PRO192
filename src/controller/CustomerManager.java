package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

import model.person.Customer;
import model.room.Room;

/**
 *
 * @author PC
 */
public class CustomerManager {
    ArrayList<Customer> customers;

    public CustomerManager() {
        customers = new ArrayList<>();
    }
    
    public ArrayList<Customer> getListCustomers() {
        return customers;
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
    public boolean updateCustomer(Customer cus, String id, String name, String phone, String address, String genderStr, LocalDate dateOfBirth, String email, int dayRent) {
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
        if (dayRent > 0 && dayRent<32) {
            cus.setDayRent(dayRent);
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
