package model;


import model.person.Customer;
import model.room.Room;

public class Order {
    public Order(Room room, Customer customer, int orderID, int dayRent) {
        this.room = room;
        this.customer = customer;
        this.orderID = orderID;
        this.dayRent = dayRent;
    }

    private Room room;
    private Customer customer;
    private int orderID;
    private int dayRent;
    
    public Order() {
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getDayRent() {
        return dayRent;
    }

    public void setDayRent(int dayRent) {
        this.dayRent = dayRent;
    }

    @Override
    public String toString() {
        return orderID + "\t" + customer.toString() + "\t" + room.toString();
    }

    /**
     * @return int return the orderID
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

}
