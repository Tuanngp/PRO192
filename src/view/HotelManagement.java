package view;

import model.room.Room;

public class HotelManagement extends Menu<String>{
    static String[] hotelMenu = {"Customer Management", "Room Management", "Order Management", "Menu"};
    private CustomerManagement customerManagement = new CustomerManagement();
    private RoomManagement roomManagement = new RoomManagement();
    private OrderManagement orderManagement = new OrderManagement();
    public HotelManagement(){
        super("-------------/Hotel Management System/----------------",hotelMenu);
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
            case "4":
                break;
            default:
                break;
        }
    };

    public static void main(String[] args) {
        HotelManagement hotelManagement = new HotelManagement();
        hotelManagement.run();
    }

}
