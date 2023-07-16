package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.CustomerManager;
import controller.OrderManager;
import controller.RoomManager;
import model.Order;
import model.person.Customer;
import model.room.Room;

public class OrderManagement extends Menu <String> {
    static String[] menu = {"Display All Room Order","Add Room Order", "Update Order's Customer.",
                            "Search Room Order","Release Room",
                            "Sort room order by day rented","Exit."};
    private Scanner sc = new Scanner(System.in);
    private OrderManager orderManager = new OrderManager();
    private CustomerManager customerManager = new CustomerManager();
    private RoomManagement roomManagement = new RoomManagement();
    private RoomManager roomManager = new RoomManager();
    public OrderManagement() {
        super("Order Management System", menu);
    }
    @Override 
    public void execute(String n ){
        switch(n){
            case "1":
                displayAllOrder(); 
                break;
            case "2": 
                addOrder(); 
                break;
            case "3": 
                updateOrder(); 
                break;
            case "4": 
                searchOrder(); 
                break;
            case "5":
                deleteOrder(); 
                break;
            case "6":
                sortOrderByDayRent();
                break;
            case "7": 
                System.out.println("Back to main menu.");
                break;
            default: 
                System.out.println("[ERROR] Invalid input! Please try again.");
        }
    }
    //--------------------------------------------------------------------------
    public void addOrder(){
        String id = Validation.getString("(*)Enter customer's id: ", Validation.REGEX_ID);
        Customer customer = customerManager.search(p -> p.getId().equalsIgnoreCase(id)).get(0);
        Order order = null;
        if(customer == null) {
            Customer customer1 = CustomerManagement.getCustomer(id);
            Room room = roomManagement.getRoom();
            order = new Order(customer1, room);
        } else {
            Room room = roomManagement.getRoom();
            order = new Order(customer, room);
            // Room 
        }
        if(orderManager.addOrder(order)) {
            System.out.println("Order room successful.");
        } else {
            System.out.println("Unable to order room");
        }
    }
    // ------------------------------------------------------------------------------
    public void displayAllOrder() {
        if(!orderManager.getOrders().isEmpty()) {
                orderManager.displayAllOrder();
        } else {
            System.out.println("Empty list, Try again please.");
        }
    }
    //--------------------------------------------------------------------------
    public void updateOrder() {
        String[] updateOrder = {"Update Room", "Update day rent", "Exit"};
        Menu menuUpdate = new Menu("Updating order", updateOrder) {

            @Override
            public void execute(String selected) {
                int orderID = Integer.parseInt(Validation.getString("Enter ID's order: ", Validation.REGEX_NUMBER));
                Order order = orderManager.search(p -> p.getOrderID() == orderID).get(0);
                switch(selected) {
                    case "1":
                        String roomID = Validation.getString("Enter new Room ID: ", Validation.REGEX_NUMBER);
                        Room newRoom = roomManager.searchRoom(p -> p.getRoomID().equals(roomID)).get(0);
                        if(orderManager.updateOrder(order, newRoom, -1)) {
                            System.out.println("Update successfull.");
                        } else {
                            System.out.println("Update falure.");
                        }
                        break;
                    case "2":
                        int dayRent = Integer.parseInt(Validation.getString("Enter new day rent: ", Validation.REGEX_NUMBER));
                        if(orderManager.updateOrder(order, null, dayRent)) {
                            System.out.println("Update successfull.");
                        } else {
                            System.out.println("Update falure.");
                        }
                        break;
                    case "3":
                        System.out.println("Exit updating order.");
                }
            }
        };
    }
    //--------------------------------------------------------------------------
    public void searchOrder(){
        String[] mSearch ={"By Order ID","By Customer Name", "By Customer ID", "Type Of Room", "Exit."};
        Menu m = new Menu("Order Searching System!!!",mSearch) {
            @Override
            public void execute(final String n){
                ArrayList<Order> rs = null;
                switch(n){
                    case "1":
                        String val = Validation.getString("Enter id you want to search", Validation.REGEX_ID);
                        rs = orderManager.search(p->p.getCustomer().getId().equalsIgnoreCase(val));
                        break;
                    case "2":
                        val = Validation.getString("Enter name's customer you want to search", Validation.REGEX_NAME);
                        rs = orderManager.search(p->p.getCustomer().getName().equalsIgnoreCase(val));
                        break;
                    case "3":
                        val = Validation.getString("Enter ID's customer you want to search", Validation.REGEX_NAME);
                        rs = orderManager.search(p->p.getCustomer().getId().equalsIgnoreCase(val));
                        break;
                    case "4":
                        val = Validation.getString("Enter type of room you want to search",Validation.ROOM_TYPE);
                        rs = orderManager.search(p->p.getRoom().getRoomType().equalsIgnoreCase(val));
                        break;
                    default: 
                        System.out.println("[ERROR] Invalid input! Please try again.");
                        break;
                }
                    System.out.println(rs);
            }
            };
                m.run();
        }
    //--------------------------------------------------------------------------
    public void deleteOrder(){
        String id = Validation.getString("Enter customer's id to delete order:", Validation.REGEX_ID);
        String confirmation = Validation.getString("Are you sure you want to delete order? (Yes/No): ", Validation.REGEX_CONFIRM);

        Order order = null;
        if (confirmation.equalsIgnoreCase("yes")) {
            if(orderManager.deleteOrder(order)) {
                System.out.println("Order " + id + " has been deleted successfully.");
            } else {
                System.out.println("Order " + id + " doesn't exist!");
            }
        }
    }
    //--------------------------------------------------------------------------
    public void sortOrderByDayRent() {
        System.out.println("List order after sort: ");
        orderManager.sortOrder();
        displayAllOrder();
    } 
  }
