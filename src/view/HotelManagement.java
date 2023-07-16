package view;

import model.room.Room;

public class HotelManagement extends Menu<String>{
    public static void main(String[] args) {
        
    }
    static String[] hotelMenu = {"Customer Management", "Room Management", "Order Management", "Menu"};
    private CustomerManagement customerManagement = new CustomerManagement();
    private RoomManagement roomManagement = new RoomManagement();
    private OrderManagement orderManagement = new OrderManagement();
    @Override
    public void execute(String selected) {
        switch(selected) {
            case "1":

            break;

        }
    };

}
