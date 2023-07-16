
package view;

import java.util.ArrayList;
import java.util.Random;

import controller.CustomerManager;
import controller.OrderManager;
import controller.RoomManager;
import java.io.IOException;
import model.Order;
import model.person.Customer;
import model.room.Room;

public class OrderManagement extends Menu<String> {
    static String[] menu = { "Display All Room Order", "Add Room Order", "Update Order's Customer.",
                            "Search Room Order", "Release Room", "Sort room order by day rented", "Exit." };
    private OrderManager orderManager = new OrderManager();
    private CustomerManager customerManager = new CustomerManager();
    private RoomManagement roomManagement = new RoomManagement();
    private RoomManager roomManager = new RoomManager();

    public OrderManagement() {
        super("Order Management System", menu);
        try {
            orderManager.readOrdersFromFile("order.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(String selected) {
        switch(selected) {
            case "1":
                displayAllOrders(); 
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
                sortOrdersByDayRent();
                break;
            case "7":
                System.out.println("Back to main menu.");
                break;
            default:
            System.out.println("[ERROR] Invalid input! Please try again.");
        }
    }
    
    // ------------------------------------------------------------------------------
    private void displayAllOrders() {
        if (orderManager.getOrders().isEmpty()) {
            System.out.println("No orders found.");
        } else {
            orderManager.displayAllOrder();
        }
    }
    // --------------------------------------------------------------------------
    public void addOrder() {
        String customerId = Validation.getString("(*) Enter customer's ID: ", Validation.REGEX_ID);
        Customer customer = customerManager.search(p -> p.getId().equalsIgnoreCase(customerId)).get(0);

        if (customer == null) {
            System.out.println("Customer with ID " + customerId + " does not exist. Please create the customer first.");
            return;
        }

        Room room = roomManagement.getRoom();
        int dayRent = Integer.parseInt(Validation.getString("Enter the number of days to rent: ", Validation.REGEX_NUMBER));
        int randomNumber = generateUniqueOrderNumber();
        Order order = new Order(room, customer, randomNumber, dayRent);

        if (orderManager.addOrder(order)) {
            System.out.println("Order placed successfully.");
        } else {
            System.out.println("Failed to place the order.");
        }
    }
    // --------------------------------------------------------------------------
    public void updateOrder() {
        int orderId = Integer.parseInt(Validation.getString("Enter the order ID: ", Validation.REGEX_NUMBER));
        Order order = orderManager.search(p -> p.getOrderID() == orderId).get(0);

        if (order == null) {
            System.out.println("Order with ID " + orderId + " does not exist.");
        }

        String[] updateOptions = {"Update Room", "Update Day Rent", "Exit"};
        Menu updateMenu = new Menu("Updating Order", updateOptions) {
            @Override
            public void execute(String selected) {
                switch (selected) {
                    case "1":
                        String roomID = Validation.getString("Enter new Room ID: ", Validation.REGEX_NUMBER);
                        Room newRoom = roomManager.searchRoom(p -> p.getRoomID().equals(roomID)).get(0);
                        if (orderManager.updateOrder(order, newRoom, -1)) {
                            System.out.println("Order updated successfully.");
                        } else {
                            System.out.println("Failed to update the order.");
                        }
                        break;
                    case "2":
                        int dayRent = Integer.parseInt(Validation.getString("Enter new day rent: ", Validation.REGEX_NUMBER));
                        if (orderManager.updateOrder(order, null, dayRent)) {
                            System.out.println("Order updated successfully.");
                        } else {
                            System.out.println("Failed to update the order.");
                        }
                        break;
                    case "3":
                        System.out.println("Exiting order update.");
                        break;
                    default:
                        System.out.println("[ERROR] Invalid input! Please try again.");
                        break;
                }
            }
        };
        updateMenu.run();
    }
    //--------------------------------------------------------------------------
        private void searchOrder() {
            String[] searchOptions = {"By Order ID", "By Customer Name", "By Customer ID", "By Room Type", "Exit"};
            Menu searchMenu = new Menu("Order Searching System!!!", searchOptions) {
                @Override
                public void execute(String option) {
                    ArrayList<Order> result = null;
                    switch (option) {
                        case "1":
                            String orderId = Validation.getString("Enter the order ID: ", Validation.REGEX_NUMBER);
                            result = orderManager.search(p -> String.valueOf(p.getOrderID()).equalsIgnoreCase(orderId));
                            break;
                        case "2":
                            String customerName = Validation.getString("Enter the customer name: ", Validation.REGEX_NAME);
                            result = orderManager.search(p -> p.getCustomer().getName().equalsIgnoreCase(customerName));
                            break;
                        case "3":
                            String customerId = Validation.getString("Enter the customer ID: ", Validation.REGEX_ID);
                            result = orderManager.search(p -> p.getCustomer().getId().equalsIgnoreCase(customerId));
                            break;
                        case "4":
                            String roomType = Validation.getString("Enter the room type: ", Validation.ROOM_TYPE);
                            result = orderManager.search(p -> p.getRoom().getRoomType().equalsIgnoreCase(roomType));
                            break;
                        case "5":
                            System.out.println("Exiting order search.");
                            break;
                        default:
                            System.out.println("[ERROR] Invalid input! Please try again.");
                            break;
                    }
                    if (result != null && !result.isEmpty()) {
                        System.out.println(result);
                    } else {
                        System.out.println("No matching orders found.");
                    }
                }
            };
            searchMenu.run();
        }
        
    // --------------------------------------------------------------------------
    public void deleteOrder() {
        String id = Validation.getString("Enter customer's id to delete order:", Validation.REGEX_ID);
        String confirmation = Validation.getString("Are you sure you want to delete order? (Yes/No): ", Validation.REGEX_CONFIRM);
        if (confirmation.equalsIgnoreCase("yes")) {
            Order order = orderManager.search(p -> p.getCustomer().getId().equalsIgnoreCase(id)).get(0);
            if (order != null) {
                if (orderManager.deleteOrder(order)) {
                    System.out.println("Order " + order.getOrderID() + " has been deleted successfully.");
                } else {
                    System.out.println("Failed to delete the order.");
                }
            }
        }
    }

    //--------------------------------------------------------------------------
    public void sortOrdersByDayRent() {
        System.out.println("List of orders after sorting by day rent:");
        orderManager.sortOrder();
        displayAllOrders();
    }
    // -----------------------------------------------------------------
    public int generateUniqueOrderNumber() {
        Random random = new Random();
        int randomNumber;
        do {
            randomNumber = 1000 + random.nextInt(9000);
        } while (orderManager.isDupplication(randomNumber));
        return randomNumber;
    }
}
