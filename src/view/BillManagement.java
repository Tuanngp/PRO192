package view;

import java.util.ArrayList;

import controller.BillManager;
import model.Bill;
import model.Order;

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
                billManager.saveFileAndExit("bill_output.txt");
                System.out.println("Back to main menu.");
                return;
            default:
            System.out.println("[ERROR] Invalid input! Please try again.");
                break;
            
        }
    }
    
    // ------------------------------------------------------------------------------
    public void displayAllBills() {
            billManager.displayAllBills();
        
    }

    public void loadBillFromFile(){
        try {
            billManager.readOrdersFromFile("order_output.txt");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    // --------------------------------------------------------------------------
    public void addBill() {
        billManager.addBill();
    }
    
    //--------------------------------------------------------------------------
    private void searchBill() {
            String[] searchOptions = {"By Customer ID", "By Customer Name", "By Room Type", "Exit"};
            Menu searchMenu = new Menu("Bill Searching System!!!", searchOptions) {
                @Override
                public void execute(String option) {
                    ArrayList<Bill> result = null;
                    switch (option) {
                        case "1":
                            String customerId = Validation.getString("Enter the customer ID: ", Validation.REGEX_ID_KH);
                            result = billManager.search(p -> p.getCustomer().getId().equalsIgnoreCase(customerId));
                            break;
                        case "2":
                            String customerName = Validation.getString("Enter the customer name: ", Validation.REGEX_NAME);
                            result = billManager.search(p -> p.getCustomer().getName().equalsIgnoreCase(customerName));
                            break;
                        case "3":
                            String roomType = Validation.getString("Enter the room type: ", Validation.ROOM_TYPE);
                            result = billManager.search(p -> p.getRoom().getRoomType().equalsIgnoreCase(roomType));
                            break;
                        case "4":
                            System.out.println("Exiting order search.");
                            return;
                        default:
                            System.out.println("[ERROR] Invalid input! Please try again.");
                            break;
                    }
                    if (result != null && !result.isEmpty()) {
                        System.out.println(result);
                    } else {
                        System.out.println("No matching orders found.");
                    }
                }
            };
            searchMenu.run();
        }
        
}