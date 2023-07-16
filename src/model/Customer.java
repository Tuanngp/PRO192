
package model;

import java.time.LocalDate;

public class Customer extends Person{
    private int dayRent;
 
    public Customer() { 
    }
    
    public Customer(String id, String name, String phone, String address, boolean gender, LocalDate dateOfBirth,String email, int dayRent) {
        super(id, name, phone, address, gender, dateOfBirth, email);
        this.dayRent = dayRent;
    }

    public int getDayRent() {
        return dayRent;
    }


    public void setDayRent(int dayRent) {
        this.dayRent = dayRent;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("%-4d", getDayRent());
    }


}
