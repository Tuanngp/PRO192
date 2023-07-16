package view;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Menu<T> {
    protected String title;
    protected ArrayList<T> listOptions;
//    ------------------------------------------------------------------------------------
    public Menu() {
    }
//    ------------------------------------------------------------------------------------
    public Menu(String title, String[] options) {
        this.title = title;
        this.listOptions = new ArrayList<>();
        for(String s : options) listOptions.add((T)s);
    }
//    ------------------------------------------------------------------------------------
    public void display() {
        System.out.println("\n");
        System.out.println("=======================================");
        System.out.println("\t" + title + "\t");
        System.out.println("=======================================");
        for(int i=0; i<listOptions.size(); i++) {
            System.out.println((i+1) + "." + listOptions.get(i));
        }
        System.out.println("---------------------------------------");
    }
//    ------------------------------------------------------------------------------------    
    public String getSelected() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter selection: ");
            return sc.nextLine();
        }
    }
//    ------------------------------------------------------------------------------------    
    public abstract void execute(String selected);
//    ------------------------------------------------------------------------------------
    public void run() {
        while(true) {
            display();
            String selected = getSelected();
            execute(selected);
            if(selected.equals(Integer.toString(listOptions.size()))) return;
        }
    }
//    ------------------------------------------------------------------------------------
}
