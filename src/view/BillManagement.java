package view;

import controller.BillManager;

public class BillManagement extends Menu<String> {
    static String[] menu = { "Display All Bill", "Add Bill", "Search Bill", "Exit." };
    private BillManager billManager = new BillManager();

    public BillManagement() {
        super("Bill Management System", menu);
    }

    @Override
    public void execute(String selected) {
        switch(selected) {
            case "1":
                displayAllBills();
                break;
            case "2":
                addBill();
                break;
            case "3":
                searchBill();
                break;
            case "4":
                System.out.println("Back to main menu.");
                
                return;
            default:
            System.out.println("[ERROR] Invalid input! Please try again.");
                break;
            
        }
    }
    
    // ------------------------------------------------------------------------------
    private void displayAllBills() {
            billManager.displayAllBills();
        
    }
    // --------------------------------------------------------------------------
    public void addBill() {
        billManager.addBill();
    }
    
    //--------------------------------------------------------------------------
        private void searchBill() {
            
        }
        
}