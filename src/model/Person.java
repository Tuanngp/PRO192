package model;

import java.time.LocalDate;

public class Person {
 
    private String id,name,phone,address,email;
    private boolean gender;
    private LocalDate dateOfBirth;
    
    public Person(){}
    
    public Person(String id, String name, String phone, String address, boolean gender,LocalDate dateOfBirth,String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return setFormName(name);
    }


    public void setName(String name) {
        this.name = name;
    }

    public static String setFormName(String name) {
        String[] word = name.split("\\s+");
        for(int i=0; i<word.length; i++) {
            word[i] = word[i].substring(0, 1).toUpperCase() + word[i].substring(1).toLowerCase();
        }
        String result = "";
        for(int i=0; i<word.length; i++) {
            result += (word[i] + " ");
        }
        return result.trim();
    }

    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }


    public void setGender(boolean gender) {
        this.gender = gender;
    }


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }


    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        String genderString = this.gender ? "male" : "female";
        return String.format("%-8s %-16s %-12s %-16s %-8s %-12s %-18s" , getId(), getName(), getPhone(), getAddress(), genderString, getDateOfBirth(), getEmail());
    }
}
