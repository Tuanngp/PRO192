package view;

public class HotelManagement extends Menu<String>{
    static String[] hotelMenu = {"Customer Management", "Room Management", "Order Management", "Employee Management","Issue bill","Exit"};
    private CustomerManagement customerManagement = new CustomerManagement();
    private RoomManagement roomManagement = new RoomManagement();
    private OrderManagement orderManagement = new OrderManagement();
    private EmployeeManagement employeeManagement = new EmployeeManagement();
    private BillManagement billManagement= new BillManagement();
    public HotelManagement(){
        super("Hotel Management System",hotelMenu);
        customerManagement.loadCustomersFromFile();
        employeeManagement.loadEmployeesFromFile();
        orderManagement.loadOrderFromFile();
        billManagement.loadBillFromFile();
    }
    @Override
    public void execute(String selected) {
        switch(selected) {
            case "1":
                customerManagement.run();
                break;
            case "2":
                roomManagement.run();
                break;
            case "3":
                orderManagement.run();
                break;
            case "4":
                employeeManagement.run();
                break;
            case "5":
                billManagement.run();
                break;
            case "6":
                System.out.println("Thanks for using our system, Good bye And you see in the next Semester");
                System.exit(0);
            default:
                System.out.println("Invalid selected! Try again please.");
                break;
        }
    };

    public static void main(String[] args) {
        HotelManagement hotelManagement = new HotelManagement();
        hotelManagement.run();
    }

}
