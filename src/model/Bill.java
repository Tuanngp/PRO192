package model;

import model.Order;
import model.person.Customer;
import model.room.Room;
public class Bill {
    Order order;
    double total;
    public Bill(Order order) {
        this.order = order;
    }

    public Bill(){
        
    }
    
    public int getOrderId(){
        return getOrder().getOrderID();
    }
    
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Room getRoom(){
        return getOrder().getRoom();
    }
    
    public Customer getCustomer(){
        return getOrder().getCustomer();
    }
    
    public double calculateTotal(){
        double total = getOrder().getDayRent()*getRoom().getPrice();
        if (getCustomer().getRank().equalsIgnoreCase("silver")){
            total*=0.95;
        }
        else if(getCustomer().getRank().equalsIgnoreCase("gold")){
            total*=0.9;
        }
        else if(getCustomer().getRank().equalsIgnoreCase("diamond")){
            total*=0.85;
        }
        return total;
    }
    
    @Override
    public String toString() {
        return String.format("%-6d,%-16s,%-8s,%-12s,%-18s,%-8s,%-12.0lf",
                getOrderId(),getCustomer().getName(),getCustomer().getId(),
                getCustomer().getPhone(),getCustomer().getEmail(),
                getRoom().getRoomID(),calculateTotal());
    }
}
