package model.person;

import java.time.LocalDate;

public class Employee extends Person {

    private int dayWork;
    private String role;

    public Employee(String id, String name, String phone, String address, boolean gender, LocalDate dateOfBirth,
            String email, int dayWork, String role) {
        super(id, name, phone, address, gender, dateOfBirth, email);
        this.dayWork = dayWork;
        this.role = role;
    }

    public int getDayWork() {
        return dayWork;
    }

    public void setDayWork(int dayWork) {
        this.dayWork = dayWork;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public float getSalary() {
//        return salary;
//    }
//
//    public void setSalary(float salary) {
//        this.salary = salary;
//    }
    public double calculateSalary(){
        double salary;
        if(role.equalsIgnoreCase("staff")) salary = dayWork*350000;
        else salary = dayWork*500000;
        return salary;
    }
    @Override
    public String toString() {
        return super.toString() + String.format(" %-4d %-8s %-12.0f", dayWork, role, calculateSalary());
    }
}
