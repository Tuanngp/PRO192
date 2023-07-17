package model;

import model.person.Customer;
import model.room.Room;
public class Bill {
    Order order;
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
        return "Order : " + order.getOrderID()+ "  Customer : " + order.getCustomer().getName() + "  "+ order.getCustomer().getId() + "  "+ order.getCustomer().getPhone() + "  "+ order.getCustomer().getEmail()+ "  Room : "+ order.getRoom().getRoomID()+ "  Bill = "+ calculateTotal()+"]";
    }
    
}
