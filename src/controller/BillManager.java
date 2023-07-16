package controller;

import java.util.ArrayList;
import java.util.function.Predicate;

import model.Bill;
import model.Order;

public class BillManager {
    ArrayList<Bill> bills=new ArrayList<>();
    int id=1;
    
    public void addBill(Order order){
        Bill bill=new Bill(order);
        bills.add(bill);
        id++;
    }

    public ArrayList<Bill> getAllBills(){
        return bills;
    }

    public ArrayList<Bill> search (Predicate<Bill>predicate){
        ArrayList<Bill> billList=new ArrayList<>();
        for (Bill bill : bills) {
            if(predicate.test(bill)){
                billList.add(bill);
            }
        }
        return billList;
    }

    public void deleteBillById(int id){
        for (Bill bill : bills) {
            if(bill.getOrderId()==id) bills.remove(bill);
        }
    }
}
