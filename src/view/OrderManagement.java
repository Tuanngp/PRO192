//package view;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//import controller.CustomerManager;
//import controller.OrderManager;
//import controller.RoomManager;
//import model.Order;
//import model.person.Customer;
//import model.room.Room;
//
//public class OrderManagement extends Menu<String> {
//    static String[] menu = { "Display All Room Order", "Add Room Order", "Update Order's Customer.",
//            "Search Room Order", "Release Room",
//            "Sort room order by day rented", "Exit." };
//    private OrderManager orderManager = new OrderManager();
//    private CustomerManager customerManager = new CustomerManager();
//    private RoomManagement roomManagement = new RoomManagement();
//    private RoomManager roomManager = new RoomManager();
//
//    public OrderManagement() {
//        super("Order Management System", menu);
//        try {
//            orderManager.readOrdersFromFile("order.txt");
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
//
//    @Override
//    public void execute(String n) {
//        switch (n) {
//            case "1":
//                displayAllOrders(); 
//                break;
//            case "2":
//                addOrder();
//                break;
//            case "3":
//                updateOrder();
//                break;
//            case "4":
//                searchOrder();
//                break;
//            case "5":
//                deleteOrder();
//                break;
//            case "6":
//                sortOrdersByDayRent();
//                break;
//            case "7":
//                System.out.println("Back to main menu.");
//                break;
//            default:
//                System.out.println("[ERROR] Invalid input! Please try again.");
//        }
//    }
//
//    //--------------------------------------------------------------------------
//
//    private void addOrder() {
//        String customerId = Validation.getString("(*) Enter customer's ID: ", Validation.REGEX_ID);
//        Customer customer = customerManager.search(p -> p.getId().equalsIgnoreCase(customerId)).get(0);
//
//        if (customer == null) {
//            System.out.println("Customer with ID " + customerId + " does not exist. Please create the customer first.");
//            customer = CustomerManagement.getCustomer(customerId);
//        }
//
//        Room room = roomManagement.getRoom();
//        int dayRent = Integer.parseInt(Validation.getString("Enter the number of days to rent: ", Validation.REGEX_NUMBER));
//        int randomNumber = generateUniqueOrderNumber();
//        Order order = new Order(room, customer, randomNumber, dayRent);
//
//        if (orderManager.addOrder(order)) {
//            System.out.println("Order placed successfully.");
//
//
//    // --------------------------------------------------------------------------
//    public void addOrder() {
//        String id = Validation.getString("(*)Enter customer's id: ", Validation.REGEX_ID);
//        Customer customer = customerManager.search(p -> p.getId().equalsIgnoreCase(id)).get(0);
//        Order order = null;
//        if (customer == null) {
//            Customer customer1 = CustomerManagement.getCustomer(id);
//            Room room = roomManagement.getRoom();
//            int dayRent = Integer
//                    .parseInt(Validation.getString("Enter the number of day rent: ", Validation.REGEX_NUMBER));
//            order = new Order(room, customer, 0, dayRent);
//        } else {
//            Room room = roomManagement.getRoom();
//            int dayRent = Integer
//                    .parseInt(Validation.getString("Enter the number of day rent: ", Validation.REGEX_NUMBER));
//            order = new Order(room, customer, 0, dayRent);
//            // Room
//        }
//        if (orderManager.addOrder(order)) {
//            System.out.println("Order room successful.");
//        } else {
//            System.out.println("Failed to place the order.");
//        }
//    }
//
//    // ------------------------------------------------------------------------------
//
//    public void displayAllOrders() {
//        if(orderManager.getOrders().isEmpty()) {
//            System.out.println("No orders found.");
//
//    public void displayAllOrder() {
//        if (!orderManager.getOrders().isEmpty()) {
//            orderManager.displayAllOrder();
//        } else {
//            orderManager.displayAllOrder();
//        }
//    }
//    //--------------------------------------------------------------------------
//    private void updateOrder() {
//        int orderId = Integer.parseInt(Validation.getString("Enter the order ID: ", Validation.REGEX_NUMBER));
//        Order order = orderManager.search(p -> p.getOrderID() == orderId).get(0);
//
//    // --------------------------------------------------------------------------
//    public void updateOrder() {
//        String[] updateOrder = { "Update Room", "Update day rent", "Exit" };
//        Menu menuUpdate = new Menu("Updating order", updateOrder) {
//
//        if (order == null) {
//            System.out.println("Order with ID " + orderId + " does not exist.");
//            return;
//        }
//
//        String[] updateOptions = {"Update Room", "Update Day Rent", "Exit"};
//        Menu updateMenu = new Menu("Updating Order", updateOptions) {
//            @Override
//            public void execute(String selected) {
//                int orderID = Integer.parseInt(Validation.getString("Enter ID's order: ", Validation.REGEX_NUMBER));
//                Order order = orderManager.search(p -> p.getOrderID() == orderID).get(0);
//                switch (selected) {
//                    case "1":
//                        String roomID = Validation.getString("Enter new Room ID: ", Validation.REGEX_NUMBER);
//                        Room newRoom = roomManager.searchRoom(p -> p.getRoomID().equals(roomID)).get(0);
//                        if (orderManager.updateOrder(order, newRoom, -1)) {
//                            System.out.println("Order updated successfully.");
//                            System.out.println("Update successfull.");
//                        } else {
//                            System.out.println("Failed to update the order.");
//                        }
//                        break;
//                    case "2":
//                        int dayRent = Integer.parseInt(Validation.getString("Enter new day rent: ", Validation.REGEX_NUMBER));
//                        if (orderManager.updateOrder(order, null, dayRent)) {
//                            System.out.println("Order updated successfully.");
//                        int dayRent = Integer
//                                .parseInt(Validation.getString("Enter new day rent: ", Validation.REGEX_NUMBER));
//                        if (orderManager.updateOrder(order, null, dayRent)) {
//                            System.out.println("Update successfull.");
//                        } else {
//                            System.out.println("Failed to update the order.");
//                        }
//                        break;
//                    case "3":
//                        System.out.println("Exiting order update.");
//                        break;
//                        System.out.println("Exit updating order.");
//                }
//            }
//        };
//        menuUpdate.run();
//    }
//
//    // --------------------------------------------------------------------------
//    public void searchOrder() {
//        String[] mSearch = { "By Order ID", "By Customer Name", "By Customer ID", "Type Of Room", "Exit." };
//        Menu m = new Menu("Order Searching System!!!", mSearch) {
//            @Override
//            public void execute(final String n) {
//                ArrayList<Order> rs = null;
//                switch (n) {
//                    case "1":
//                        String val = Validation.getString("Enter id you want to search", Validation.REGEX_ID);
//                        rs = orderManager.search(p -> p.getCustomer().getId().equalsIgnoreCase(val));
//                        break;
//                    case "2":
//                        val = Validation.getString("Enter name's customer you want to search", Validation.REGEX_NAME);
//                        rs = orderManager.search(p -> p.getCustomer().getName().equalsIgnoreCase(val));
//                        break;
//                    case "3":
//                        val = Validation.getString("Enter ID's customer you want to search", Validation.REGEX_NAME);
//                        rs = orderManager.search(p -> p.getCustomer().getId().equalsIgnoreCase(val));
//                        break;
//                    case "4":
//                        val = Validation.getString("Enter type of room you want to search", Validation.ROOM_TYPE);
//                        rs = orderManager.search(p -> p.getRoom().getRoomType().equalsIgnoreCase(val));
//                        break;
//                    default:
//                        System.out.println("[ERROR] Invalid input! Please try again.");
//                        break;
//                }
//
//            }
//        };
//        updateMenu.run();
//    }
//    //--------------------------------------------------------------------------
//        private void searchOrder() {
//            String[] searchOptions = {"By Order ID", "By Customer Name", "By Customer ID", "By Room Type", "Exit"};
//            Menu searchMenu = new Menu("Order Searching System!!!", searchOptions) {
//                @Override
//                public void execute(String option) {
//                    ArrayList<Order> result = null;
//                    switch (option) {
//                        case "1":
//                            String orderId = Validation.getString("Enter the order ID: ", Validation.REGEX_NUMBER);
//                            result = orderManager.search(p -> String.valueOf(p.getOrderID()).equalsIgnoreCase(orderId));
//                            break;
//                        case "2":
//                            String customerName = Validation.getString("Enter the customer name: ", Validation.REGEX_NAME);
//                            result = orderManager.search(p -> p.getCustomer().getName().equalsIgnoreCase(customerName));
//                            break;
//                        case "3":
//                            String customerId = Validation.getString("Enter the customer ID: ", Validation.REGEX_ID);
//                            result = orderManager.search(p -> p.getCustomer().getId().equalsIgnoreCase(customerId));
//                            break;
//                        case "4":
//                            String roomType = Validation.getString("Enter the room type: ", Validation.ROOM_TYPE);
//                            result = orderManager.search(p -> p.getRoom().getRoomType().equalsIgnoreCase(roomType));
//                            break;
//                        case "5":
//                            System.out.println("Exiting order search.");
//                            break;
//                        default:
//                            System.out.println("[ERROR] Invalid input! Please try again.");
//                            break;
//                    }
//    
//                    if (result != null && !result.isEmpty()) {
//                        System.out.println(result);
//                    } else {
//                        System.out.println("No matching orders found.");
//                    }
//                }
//            };
//            searchMenu.run();
//        }
//    //--------------------------------------------------------------------------
//    private void deleteOrder() {
//        String customerId = Validation.getString("Enter the customer ID to delete the order: ", Validation.REGEX_ID);
//        String confirmation = Validation.getString("Are you sure you want to delete the order? (Yes/No): ", Validation.REGEX_CONFIRM);
//
//                System.out.println(rs);
//            }
//        };
//        m.run();
//    }
//
//    // --------------------------------------------------------------------------
//    public void deleteOrder() {
//        String id = Validation.getString("Enter customer's id to delete order:", Validation.REGEX_ID);
//        String confirmation = Validation.getString("Are you sure you want to delete order? (Yes/No): ", Validation.REGEX_CONFIRM);
//        if (confirmation.equalsIgnoreCase("yes")) {
//            Order order = orderManager.search(p -> p.getCustomer().getId().equalsIgnoreCase(customerId)).get(0);
//            if (order != null) {
//                if (orderManager.deleteOrder(order)) {
//                    System.out.println("Order " + order.getOrderID() + " has been deleted successfully.");
//                } else {
//                    System.out.println("Failed to delete the order.");
//                }
//
//                if (orderManager.deleteOrder(order)) {
//                    System.out.println("Order " + id + " has been deleted successfully.");
//                } else {
//                    System.out.println("Order with customer ID " + customerId + " does not exist.");
//                }
//            }
//        }
//    }
//
//    //--------------------------------------------------------------------------
//    private void sortOrdersByDayRent() {
//        System.out.println("List of orders after sorting by day rent:");
//        orderManager.sortOrder();
//        displayAllOrders();
//    }
//    // -----------------------------------------------------------------
//    private int generateUniqueOrderNumber() {
//        Random random = new Random();
//        int randomNumber;
//        do {
//            randomNumber = 1000 + random.nextInt(9000);
//        } while (orderManager.isDupplication(randomNumber));
//        return randomNumber;
//    }
//
//    // --------------------------------------------------------------------------
//    public void sortOrderByDayRent() {
//        System.out.println("List order after sort: ");
//        orderManager.sortOrder();
//        displayAllOrder();
//    }
//}
