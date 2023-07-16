package model.room;

public class Room {

    private String roomID;
    private String roomType;
    private float price;
    private boolean status;


    public Room() {
    }

    public Room(String roomID, String roomType, float price, boolean status) {
        this.roomID = roomID;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String status = this.status?"Occupied":"Vacant";
        return String.format("%-8s %-16s %-12.0f %-12s", roomID, roomType, price, status);
    }





}
