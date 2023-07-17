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
            for (Order order : orders) {
                String orderString = order.toString();
                if (!existingData.contains(orderString)) {
                    writer.write(orderString + "\n");
                }
            }
            System.out.println("Data written to file " + fileName + " successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while writing data to file");
        }
    }
}
