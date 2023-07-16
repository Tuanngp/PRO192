
package model.person;

import java.time.LocalDate;

public class Customer extends Person{

    private String rank;
 
    public Customer() { 
    }
    
    public Customer(String id, String name, String phone, String address, boolean gender, LocalDate dateOfBirth,String email, String rank) {
        super(id, name, phone, address, gender, dateOfBirth, email);
        this.rank = rank;
        // setName("KH" + id);
    }
    
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format("%-10s", getRank());
    }

}
