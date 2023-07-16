package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Predicate;
import model.Order;
import model.person.Customer;
import model.room.Room;
import view.RoomManagement;

public class OrderManager {
    private ArrayList<Order> orders = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private RoomManagement roomManagement = new RoomManagement();
    // -------------------------------------------------
    public OrderManager() {
        
    }

// -----------------------------------------------------------
    public boolean addOrder(Order order) {
        if(order != null) {
            orders.add(order);
            return true;
        } else {
            return false;
        }
    }

// ----------------------------------------------------------
    public boolean displayAllOrder() {
        for(Order order : orders) {
            if(order.getRoom().getStatus()) {
                System.out.println(order);
                return true;
            }
        }
        return false;
    }
// ------------------------------------------------------------
    public ArrayList<Order> getOrders() {
        return orders;
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
    public boolean updateOrder(Customer customer, Room room) {
        boolean updated = false;
        
        return updated;
    }
// --------------------------------------------------------
    public boolean deleteOrder(Order order) {
        if(order.getCustomer()==null || order.getRoom()==null) {
            return false;
        } else {
            orders.remove(order);
            return true;
        }
    }

// -----------------------------------------------------------
    public void sortOrder() {
        Collections.sort(orders, Comparator.comparingInt(order -> order.getCustomer().getDayRent()));
    }
}
