package controller;

import java.io.BufferedReader;
import java.io.FileReader;
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

public class OrderManager {
    private ArrayList<Order> orders;

    // -------------------------------------------------
    public OrderManager() {
        readOrdersFromFile();
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
        return !search(p-> p.getOrderID()==id).isEmpty();
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

    public ArrayList<Order> readOrdersFromFile() {
        ArrayList<Order> orders = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("order.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String roomID = data[0];
                String roomType = data[1];
                float price = Float.parseFloat(data[2]);
                boolean status = Boolean.parseBoolean(data[3]);
                String customerID = data[4];
                String customerName = data[5];
                String customerPhone = data[6];
                String customerAddress = data[7];
                boolean customerGender = data[8].equalsIgnoreCase("male");
                LocalDate customerDOB = LocalDate.parse(data[9], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String customerEmail = data[10];
                String customerRank = data[11];
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
                    // Handle other room types if needed
                    room = new Room(roomID, roomType, price, status);
                }

                Order order = new Order(room, customer, orderID, dayRent);

                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
}

