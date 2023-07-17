package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Predicate;

import model.Bill;
import model.Order;
import model.person.Customer;
import model.room.Room;
import view.Validation;

public class BillManager {
    ArrayList<Bill> bills=new ArrayList<>();
    OrderManager ord = new OrderManager();
    RoomManager roomManager=new RoomManager();
    ArrayList<Order> orders=new ArrayList<>();
    
    public void addBill(){
        readOrdersFromFile("order.txt");
        for (Order order : orders){
            Bill bill=new Bill(order);
            bills.add(bill);
        }
    }
    
    public void displayAllBills(){
        for (Bill bill : bills){
            System.out.println(bill.toString());
        }
    }
    
    public ArrayList<Bill> getAllBills(){
        return bills;
    }

    public ArrayList<Bill> search (Predicate<Bill>predicate){
        ArrayList<Bill> billList=new ArrayList<>();
        for (Bill bill : bills) {
            if(predicate.test(bill)){
                billList.add(bill);
            }
        }
        return billList;
    }

    public void deleteBillById(int id){
        for (Bill bill : bills) {
            if(bill.getOrderId()==id) bills.remove(bill);
        }
    }

    public void readOrdersFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 14) {
                    String roomID = data[0];
                    String roomType = data[1];
                    float price = Float.parseFloat(data[2]);
                    boolean status = Boolean.parseBoolean(data[3]);
                    String customerID = Validation.checkValue(data[4], Validation.REGEX_ID_KH);
                    String customerName = Validation.checkValue(data[5], Validation.REGEX_NAME);
                    String customerPhone = Validation.checkValue(data[6], Validation.REGEX_NUMBER);
                    String customerAddress = Validation.checkValue(data[7], Validation.REGEX_ADDRESS);
                    String customerGenderStr = Validation.checkValue(data[8], "(?i)male|female");
                    boolean customerGender;
                    if (customerGenderStr != null && customerGenderStr.equalsIgnoreCase("male")) {
                        customerGender = true;
                    } else {
                        customerGender = false;
                    }
                    String dateOfBirthStr = data[9];
                    LocalDate dateOfBirth = null;
                            if (dateOfBirthStr != null) {
                                dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern(Validation.DATE_FORMAT));
                               }
                    String customerEmail = Validation.checkValue(data[10], Validation.REGEX_EMAIL);
                    String customerRank = Validation.checkValue(data[11], Validation.REGEX_RANK);
                    int orderID = Integer.parseInt(data[12]);
                    int dayRent = Integer.parseInt(data[13]);
                    
                    Customer customer = new Customer(customerID, customerName, customerPhone, customerAddress,
                            customerGender, dateOfBirth, customerEmail, customerRank);
                    Room room = roomManager.searchRoom(p->p.getRoomID().equalsIgnoreCase(roomID)).get(0);
                        room.setStatus(status);
                    roomManager.orderRoomv(roomID);
                    Order order = new Order(room, customer, orderID, dayRent);
                    order.setCustomer(customer);
                    order.setRoom(room);
                    orders.add(order);
                } else {
                    System.out.println("Invalid file data");
                }

            }
        } catch (Exception e) {
            System.out.println("Error occurred while reading orders from file: " + filename);
            e.printStackTrace();
        }
    }
}
