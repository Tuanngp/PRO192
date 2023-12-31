package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import model.Order;
import model.person.Customer;
import model.room.Room;
import view.Validation;

public class OrderManager {
    private ArrayList<Order> orders;
    RoomManager roomManager = new RoomManager();
    CustomerManager customerManager = new CustomerManager();
    // -------------------------------------------------
    public OrderManager() {
        orders = new ArrayList<>();
    }
    // --------------------------------------------------------
    public ArrayList<Order> getOrders() {
        ArrayList<Order> getOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getRoom().getStatus()) {
                getOrders.add(order);
            }
        }
        return getOrders;
    }
    //-----------------------------------------------------------
    public ArrayList<Order> getFullOrders(){
        return orders;
    }
    // -----------------------------------------------------------
    public void loadOrdersFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 14) {
                    // get information 
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
                    if (dateOfBirthStr != null) {
                        dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern(Validation.DATE_FORMAT));
                    }
                    String customerEmail = Validation.checkValue(data[10], Validation.REGEX_EMAIL);
                    String customerRank = Validation.checkValue(data[11], Validation.REGEX_RANK);
                    int orderID = Integer.parseInt(data[12]);
                    int dayRent = Integer.parseInt(data[13]);
                    
                    // reference object 
                    Customer customer = new Customer(customerID, customerName, customerPhone, customerAddress,customerGender, dateOfBirth, customerEmail, customerRank);
                    customerManager.addCustomers(customer);
                    Room room = roomManager.searchRoomByID(roomID);
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
    // -------------------------------------------------------------
    public void displayAllOrder() {
        for (Order order : orders) {
            if (order.getRoom().getStatus()) {
                System.out.println(order.toString());
            }
        }
    }
    // -----------------------------------------------------------
    public void historyOrder() {
        orders.forEach(p -> System.out.println(p));
    }
    // ----------------------------------------------------------
    public boolean addOrder(Order order, Customer customer,Room room) {
    if (order != null && room != null && customer!=null) {
        order.setCustomer(customer);
        order.setRoom(room);
        orders.add(order);
        return true;
    } else {
        return false;
    }
}
    // -------------------------------------------------
    public boolean isDupplication(int id) {
        return !search(p -> p.getOrderID() == id).isEmpty();
    }
    // ---------------------------------------------------------
    public ArrayList<Order> search(Predicate<Order> p) {
        ArrayList<Order> rs = new ArrayList<>();
        for (Order s : orders) {
            if (p.test(s)) {
                rs.add(s);
            }
        }
        return rs;
    }
    
    public Order searchOrderByID(int orderId) {
        return orders.stream().filter(p -> p.getOrderID() == orderId).findFirst().orElse(null);
    }
    // -------------------------------------------------------
    public boolean updateOrder(Order order, Room room, int dayRent) {
        boolean updated = false;
        if (dayRent > 0 && dayRent != order.getDayRent()) {
            order.setDayRent(dayRent);
            updated = true;
        }

        if (room != null) {
            order.setRoom(room);
            order.getRoom().setStatus(true);
            updated = true;
        }
        return updated;
    }
    // --------------------------------------------------------
    public boolean deleteOrder(Order order) {
        if (order.getCustomer() == null || order.getRoom() == null) {
            return false;
        } else {
            orders.remove(order);
            return true;
        }
    }

    // -----------------------------------------------------------
    public void sortOrderByDayRent() {
        Collections.sort(orders, Comparator.comparingInt(order -> order.getDayRent()));
    }

    public void sortOrderById() {
        Collections.sort(orders, Comparator.comparingInt(order -> order.getOrderID()));
    }
    // -----------------------------------------------------------
    public void saveFileAndExit(String fileName) {
        
        try (FileWriter writer = new FileWriter(fileName)) {
            String orderStr="";
            for (Order order : orders) {
                orderStr+=order.getOrderID()+","+order.getCustomer().getId()+","+order.getCustomer().getName()+","+order.getCustomer().getPhone()+","+order.getCustomer().getAddress()+","+order.getCustomer().isGender()+","+order.getCustomer().getDateOfBirthStr()
                +","+order.getCustomer().getEmail()+","+order.getCustomer().getRank()+","+order.getRoom().getRoomID()+","+order.getRoom().getRoomType()+","+order.getRoom().getPrice()+","+order.getRoom().getStatus()
                +","+order.getDayRent()+"\n";
                
            }
            writer.write(orderStr);
            System.out.println("Data written to file " + fileName + " successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while writing data to file");
        }
    }
}
