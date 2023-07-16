package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.OrderManager;
import model.Order;

public class OrderManagement extends Menu <String>{
    static String[] menu = {"Add Room Order","Display All Room Order","Update Order's Customer.",
                            "Search Room Order","Release Room",
                            "Sort room order by day rented","Exit."};
    private Scanner sc = new Scanner(System.in);
    private OrderManager orderManager = new OrderManager();
    public OrderManagement(){
        super("Order Management System", menu);
    }
    @Override 
    public void execute(String n ){
        switch(n){
            case "1":
                addOrder(); 
                break;
            case "2": 
                displayAllOrder(); 
                break;
            case "3": 
                orderUpdatingAll(); 
                break;
            case "4": 
                orderSearching(); 
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
        Order order = null;
        if (orderManager.addOrder(order)) 
            System.out.println("Customer rent room successfully!!");
        else 
            System.out.println("Failed in renting room!!");
    }

    public void displayAllOrder() {
        if(!orderManager.getOrders().isEmpty()) {
                orderManager.displayAllOrder();
        } else {
            System.out.println("Empty list, Try again please.");
        }
    }
    //--------------------------------------------------------------------------
    public void orderUpdatingAll() {
        if(orderManager.updateOrder(null, null)) {
            System.out.println("Update successfull.");
        } else {
            System.err.println("Update failed.");
        }
    }
    //--------------------------------------------------------------------------
    public void orderSearching(){
        String[] mSearch ={"By Customer ID","By Customer Name","Type Of Room", "Exit."};
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
                        val = Validation.getString("Enter name you want to search", Validation.REGEX_NAME);
                        rs = orderManager.search(p->p.getCustomer().getName().equalsIgnoreCase(val));
                        break;
                    case "3":
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

        Order order = new Order();
        if (confirmation.equalsIgnoreCase("yes")) {
            if(orderManager.deleteOrder(order)) {
                System.out.println("Order ");
            }
        }
        //     System.out.println("Order delected successfully!!");
        // else 
        //     System.out.println("Customer " + id +" doesn't exitst!");
    }
    //--------------------------------------------------------------------------
    public void sortOrderByDayRent() {
        System.out.println("List customer after sort: ");
        orderManager.sortOrder();
        displayAllOrder();
    } 
  }
