package controller;

import java.util.ArrayList;
import java.util.function.Predicate;
import model.room.CoupleRoom;
import model.room.Room;
import model.room.SingleRoom;

/**
 *
 * @author PC
 */
public class RoomManager {
    private final String[] ID_SINGLE_ROOM = {"101", "102", "103", "104", "105", "201", "202", "203", "204", "205"};
    private final String[] ID_COUPLE_ROOM = {"301", "302", "303", "304", "305", "401", "402", "403", "404", "405"};

    private final ArrayList<Room> rooms;
    public RoomManager() {
        rooms = new ArrayList<>();
        createRoom();
    }
//    ------------------------------------------------------------------------------
    public ArrayList<Room> getListRoom() {
        return rooms;
    }
//    ------------------------------------------------------------------------------
    public void addRoom(Room room) {
        rooms.add(room);
    }
//    ------------------------------------------------------------------------------
    public final void createRoom() {
        for (String idSingleRoom : ID_SINGLE_ROOM) {
            Room singleRoom = new SingleRoom(idSingleRoom);
            addRoom(singleRoom);
        }
        for (String idCoupleRoom : ID_COUPLE_ROOM) {
            Room coupleRoom = new CoupleRoom(idCoupleRoom);
            addRoom(coupleRoom);
        }
    }
//    ------------------------------------------------------------------------------
    public void displayAllRoom() {
        rooms.forEach(p -> System.out.println(p));
    }
//    ------------------------------------------------------------------------------
    public void displayRoom(ArrayList<Room> listRoom) {
        listRoom.forEach(p -> System.out.println(p));
    }
//    ------------------------------------------------------------------------------
    public void displayRentedRoom() {
        ArrayList<Room> rentedRooms = new ArrayList<>();
        rentedRooms = searchRoom(p -> p.getStatus());
        rentedRooms.forEach(p -> System.out.println(p));
    }
//    ------------------------------------------------------------------------------
    public boolean isDupplication(String id) {
        return !searchRoom(p -> p.getRoomID().equals(id)).isEmpty();
    }
//    ------------------------------------------------------------------------------
    public ArrayList<Room> searchRoom(Predicate<Room> p) {
        ArrayList<Room> roomSearch = new ArrayList<>();
        for(Room room: rooms) {
            if(p.test(room)) roomSearch.add(room);
        }
        return roomSearch;
    }
//    ------------------------------------------------------------------------------
    public boolean updateRoom(Room room, String roomId, String roomType, float price) {
        boolean updated = false;
        if(!roomId.trim().isEmpty()) {
            room.setRoomID(roomId);
            updated = true;
        }
        if(!roomType.trim().isEmpty()) {
            room.setRoomType(roomType);
            updated = true;
        }
        if(price>0) {
            room.setPrice(price);
            updated = true;
        }
        return updated;
    }
//    ------------------------------------------------------------------------------
    public boolean releaseRoom(String roomID, String roomType) {
        int cnt=0;
        for (Room room : rooms) {
            if ((roomID != null && room.getRoomID().equalsIgnoreCase(roomID)) ||
                (roomType != null && room.getRoomType().equalsIgnoreCase(roomType))) {
                    if(room.getStatus()) {
                        room.setStatus(false);
                        cnt++;
                    }
            }
        }
        return cnt>0;
    }
    public boolean releaseRoom() {
        int cnt=0;
        for (Room room : rooms) {
            if(room.getStatus()) {
                room.setStatus(false);
                cnt++;
            }
        }
        return cnt>0;
    }
//    ------------------------------------------------------------------------------
}
