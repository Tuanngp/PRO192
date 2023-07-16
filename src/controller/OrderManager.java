package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;
import model.Order;
import model.person.Customer;
import model.room.CoupleRoom;
import model.room.Room;
import model.room.SingleRoom;
import view.Validation;

public class OrderManager {
    private ArrayList<Order> orders;

    // -------------------------------------------------
    public OrderManager() {
        orders = new ArrayList<>();
    }

    // -----------------------------------------------------------
    public boolean addOrder(Order order) {
        if (order != null) {
            orders.add(order);
            return true;
        } else {
            return false;
        }
    }

    // ----------------------------------------------------------
    public boolean displayAllOrder() {
        for (Order order : orders) {
            if (order.getRoom().getStatus()) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------
    public ArrayList<Order> getOrders() {
        return orders;
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

    // -------------------------------------------------------
    public boolean updateOrder(Order order, Room room, int dayRent) {
        boolean updated = false;
        if (dayRent > 0 && dayRent != order.getDayRent()) {
            order.setDayRent(dayRent);
            updated = true;
        }

        if (room.getStatus() == false) {
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
    public void sortOrder() {
        Collections.sort(orders, Comparator.comparingInt(order -> order.getDayRent()));
    }
    // -----------------------------------------------------------

    public ArrayList<Order> readOrdersFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("order.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 14) {
                    String roomID = data[0];
                    String roomType = data[1];
                    float price = Float.parseFloat(data[2]);
                    boolean status = Boolean.parseBoolean(data[3]);
                    String customerID = Validation.checkValue(data[4].substring(2), Validation.REGEX_ID);
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
                    String customerDOBStr = Validation.checkValue(data[9], Validation.DATE_FORMAT);
                    LocalDate customerDOB = null;
                    if (customerDOBStr != null) {
                        customerDOB = LocalDate.parse(customerDOBStr,
                                DateTimeFormatter.ofPattern(Validation.DATE_FORMAT));
                    }
                    String customerEmail = Validation.checkValue(data[10], Validation.REGEX_EMAIL);
                    String customerRank = Validation.checkValue(data[11], Validation.REGEX_RANK);
                    int orderID = Integer.parseInt(data[12]);
                    int dayRent = Integer.parseInt(data[13]);

                    Customer customer = new Customer(customerID, customerName, customerPhone, customerAddress,
                            customerGender, customerDOB, customerEmail, customerRank);
                    Room room;
                    if (roomType.equalsIgnoreCase("Single Room")) {
                        room = new SingleRoom(roomID);
                    } else if (roomType.equalsIgnoreCase("Couple Room")) {
                        room = new CoupleRoom(roomID);
                    } else {
                        room = new Room(roomID, roomType, price, status);
                    }

                    Order order = new Order(room, customer, orderID, dayRent);
                    orders.add(order);
                } else {
                    System.out.println("Invalid file data");
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurred while reading orders from file: " + filename);
            e.printStackTrace();
        }

        return orders;
    }
}
