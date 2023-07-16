package model.person;

import java.time.LocalDate;

public class Employee extends Person {

    private int dayWork;
    private String role;
    private float salary;

    public Employee(String id, String name, String phone, String address, boolean gender, LocalDate dateOfBirth,
            String email, int dayWork, String role, float salary) {
        super(id, name, phone, address, gender, dateOfBirth, email);
        this.dayWork = dayWork;
        this.role = role;
        this.salary = salary;
    }

    public Employee(int dayWork, String role, float salary) {
        this.dayWork = dayWork;
        this.role = role;
        this.salary = salary;
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

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        String genderString = isGender() ? "male" : "female";
        return super.toString() + String.format(" %-4d %-8s %-12f", dayWork, role, salary);
    }
}
