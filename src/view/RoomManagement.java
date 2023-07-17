package view;

import controller.RoomManager;
import java.util.ArrayList;
import model.room.Room;

public class RoomManagement extends Menu<String>{
    static String[] roomOptions = { "Display all room.", "Search room.", "Update Room.", "Release room.", "Statistic room" , "Exit"};
    RoomManager roomManager = new RoomManager();

    public RoomManagement() {
        super("Room Management System", roomOptions);
    }
    
    @Override
    public void execute(String selected) {
        switch (selected) {
            case "1":
                displayRoom();
                break;
            case "2":
                searchRoom();
                break;
            case "3":
                updateRoom();
                break;
            case "4":
                releaseRoom();
                break;
            case "5":
                statisticRoom();
                break;
            default:
                System.out.println("[ERROR], Try again please.");
                break;
        }
    }
    
    public void displayRoom() {
        if(roomManager.getListRoom().isEmpty()) {
            System.out.println("Empty list! Try again please.");
        } else {
            System.out.println("List room: ");
            roomManager.displayAllRoom();
        }
    }
    
    public void displayRoom(ArrayList<Room> rooms) {
        if(roomManager.getListRoom().isEmpty()) {
            System.out.println("Empty list! Try again please.");
        } else {
            System.out.println("List room: ");
            roomManager.displayRoom(rooms);
        }
    }
    //-------------------------------------------------------------------
    public void displayAvailableRoom() {
    ArrayList<Room> availableRooms = roomManager.getAvailableRooms(); // Retrieve only the available (unoccupied) rooms
    if (availableRooms.isEmpty()) {
        System.out.println("No available rooms to display.");
    } else {
        System.out.println("List of available rooms:");
        roomManager.displayRoom(availableRooms);
    }
}

    public void searchRoom() {
        String[] searchOptions = {"Search by ID", "Search by Type", "Exit"};
        Menu searchMenu = new Menu("Searching Room", searchOptions) {
            @Override
            public void execute(String selected) {
                switch (selected) {
                    case "1":
                        String roomID = Validation.getString("Enter Room ID: ", Validation.REGEX_NUMBER);
                        displayRoom(roomManager.searchRoom(p -> p.getRoomID().equals(roomID)));
                        break;
                    case "2":
                        String roomType = Validation.getString("Enter Room Type: ", Validation.REGEX_NAME);
                        displayRoom(roomManager.searchRoom(p -> p.getRoomType().equalsIgnoreCase(roomType)));
                        break;
                    case "3":
                        System.out.println("Exit Searching Menu!");
                        break;
                    default:
                        System.out.println("[ERROR], Try again please.");
                        break;
                        
                }
            }
        };
        searchMenu.run();
    }
    
    public void updateRoom() {
        ArrayList<Room> updateRoom = new ArrayList<>();
        
        do {            
            System.out.println("Updating Room:");
            String roomId = Validation.getString("(*)Enter Room ID want to update: ", Validation.REGEX_NUMBER);
            updateRoom = roomManager.searchRoom(p -> p.getRoomID().equals(roomId));
        
            if(updateRoom.isEmpty()) {
                System.out.println("Room "+ roomId + " does not exist!");
            }
        } while (updateRoom.isEmpty());

            
        System.out.println("--------------------------------------------------------");
        System.out.println("Inputting information to update (Enter to pass)");
        String roomIdNew=null;
        do {
            roomIdNew = Validation.getString("Enter Room ID: ", Validation.REGEX_NUMBER);
            if (roomManager.isDupplication(roomIdNew)) {
                System.out.println("Room " + roomIdNew + " has been existed! Try again please.");
            }
        } while(roomManager.isDupplication(roomIdNew));
        String roomType = Validation.getString("Enter Room Type: ", Validation.ROOM_TYPE);
        float price = -1;
        try {
            price = Float.parseFloat(Validation.getString("Enter Room Price: ", Validation.REGEX_NUMBER));
        } catch (NumberFormatException e) {
            price = -1;
        }
        System.out.println("--------------------------------------------------------");
        
        if(roomManager.updateRoom(updateRoom.get(0), roomIdNew, roomType, price)) {
            System.out.println("Room " + roomIdNew + "has been updated succesfully.");
        } else {
            System.out.println("[ERROR], No updating can be performed!");
        }
    }
    
    public void releaseRoom() {
        String[] releaseOptions = {"Release All Room","Release Room by ID", "Release Room by Type", "Exit"};
        Menu releaseMenu = new Menu("Release Room", releaseOptions) {
            @Override
            public void execute(String selected) {
                switch (selected) {
                    case "1":
                        if(roomManager.releaseRoom()) {
                            System.out.println("All Room has been released successfully.");
                        } else {
                            System.out.println("[ERROR] No rooms are being rented!");
                        }
                        break;
                    case "2":
                        String roomId ;
                        do{
                            roomId = Validation.getString("Enter Room ID: ", Validation.REGEX_NUMBER);
                            if(!roomManager.isDupplication(roomId)) {
                                System.out.println("Room " + roomId + " not exist!");
                            }
                        } while( !roomManager.isDupplication(roomId));
                        
                        if(roomManager.releaseRoom(roomId, null)) {
                            System.out.println("Room " + roomId + " has been release successfully!");
                        } else {
                            System.out.println("Room " + roomId + " is not being rented!");
                        }
                        break;
                    case "3":
                        String roomType = Validation.getString("Enter Room Type (Single Room | Couple Room): ", Validation.ROOM_TYPE);
                        if(roomManager.releaseRoom(null, roomType)) {
                            System.out.println("All " + roomType + " has been release successfully!");
                        } else {
                            System.out.println("All " + roomType + " are not being rented!");
                        }
                        break;
                    case "4":
                        System.out.println("Exit Release Menu!");
                        break;
                    default:
                        System.out.println("[ERROR], Try again please.");
                        break;
                }

            }
        };
        releaseMenu.run();
    }
    
    public void statisticRoom() {
        String[] statisticOptions = {"Room type", "Status", "Exit"};
        Menu statisticMenu = new Menu("Statistic room", statisticOptions) {
            @Override
            public void execute(String selected) {
                switch (selected) {
                    case "1":
                        statisticRoomType();
                        break;
                    case "2":
                        statisticRoomStatus();
                        break;
                    case "3":
                        System.out.println("Exit!");
                        break;
                    default:
                        System.out.println("[ERROR], Try again please.");
                        break;
                }
            }
        };
        statisticMenu.run();
    }
    
    public void statisticRoomType() {
        String[] statisticOptions = {"Single Room", "Couple Room", "Exit"};
        Menu statisticMenu = new Menu("Statistic Room Type", statisticOptions) {
            @Override
            public void execute(String selected) {
                switch (selected) {
                    case "1":
                        displayRoom(roomManager.searchRoom(p -> p.getRoomType().equals("Single Room")));
                        break;
                    case "2":
                        displayRoom(roomManager.searchRoom(p -> p.getRoomType().equals("Couple Room")));
                        break;
                    case "3":
                        System.out.println("Exit!");
                        break;
                    default:
                        System.out.println("[ERROR], Try again please.");
                        break;
                }
            }
        };
        statisticMenu.run();
    }
        
    public void statisticRoomStatus() {
        String[] statisticOptions = {"Occupied", "Vacant", "Exit"};
        Menu statisticMenu = new Menu("Statistic room", statisticOptions) {
            @Override
            public void execute(String selected) {
                switch (selected) {
                    case "1":
                        displayRoom(roomManager.searchRoom(p -> p.getStatus()));
                        break;
                    case "2":
                        displayRoom(roomManager.searchRoom(p -> !p.getStatus()));
                        break;
                    case "3":
                        System.out.println("Exit!");
                        break;
                    default:
                        System.out.println("[ERROR], Try again please.");
                        break;
                }
            }
        };
        statisticMenu.run();
    }

    public Room getRoom() {
        displayAvailableRoom();
        String roomId = Validation.getString("Enter Room ID: ", Validation.REGEX_NUMBER);
        Room room = roomManager.searchRoom(p -> p.getRoomID().equals(roomId)).get(0);
        if(roomManager.orderRoom(roomId)) {
            System.out.println("Order room " + roomId + " has been successfully.");
            return room;
        } else {
            System.out.println("Room " + roomId + " is been failed.");
                   return null;
        }
    }
}
