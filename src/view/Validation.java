package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Validation {
    // --------------------------------------------------------------------
    public static final String REGEX_ID = "\\d+";
    public static final String REGEX_ID_UPDATE = "\\d*";
    public static final String REGEX_NAME = "^[a-zA-Z ]*$";
    public static final String REGEX_NUMBER = "\\d*";
    public static final String REGEX_ADDRESS = "^[A-Za-z0-9 ]*$";
    public static final String REGEX_GENDER = "\\b(?i)true|false+\\b";
    public static final String REGEX_CONFIRM = "(?i)yes|no+";
    public static final String REGEX_RANK = "(?i)member|silver|gold|diamond+";
    public static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]*@*[A-Za-z0-9.-]*$";
    public static final String REGEX_DAYWORK = "^(?:[1-9]|[12]\\d|3[01])$";
    public static final String ROOM_TYPE= "(?i)single room|couple room*";
    public static final String REGEX_ROLE = "(?i)staff|manager+";
    public static final String DATE_FORMAT= "d/M/u";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    //-----------------------------------------------------------------------
    public static String getString(String pr, String pattern) {
        String str;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(pr);
            str = sc.nextLine();
            if(str==null) {
                return null;
            }
            if (!str.matches(pattern)) {
                System.out.println("[ERROR] Invalid input! Please try again.");
            }
        } while (!str.matches(pattern));
        return str;
    } // -------------------------------------------------------

    public static String getString(String pr) {
        String str;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(pr);
            str = sc.nextLine();
            if (str.trim().isEmpty()) {
                System.out.println("[ERROR] Can not empty! Please try again!");
            }
        } while (str.trim().isEmpty());
        return str.toUpperCase();
    }

    // -------------------------------------------------------
    public static boolean validDay(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    // --------------------------------------------------------
    public static LocalDate getLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        if(dateStr==null) {
            return null;
        }
        return LocalDate.parse(dateStr, formatter);
    }
    // --------------------------------------------------------
    public static String getDate(String prompt) {
        String dateStr;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(prompt);
            dateStr = sc.nextLine();
    
            // Check if the input is empty
            if (dateStr.isEmpty()) {
                return null;
            }
    
            if (!validDay(dateStr)) {
                System.out.println("[ERROR] Invalid date format. Please try again.");
            }
        } while (!validDay(dateStr));
    
        return dateStr;
    }
    //---------------------------------------------------------
    public static int getDayWork(String pr){
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(pr);
                String input = sc.nextLine();
                int dayWork = Integer.parseInt(input);
                if (dayWork <= 31 && dayWork > 0) {
                    return dayWork;
                } else {
                    System.out.println("Day work must be bigger than 0 and less than - equal to 31 days ");
                }
            } catch (NumberFormatException e) {
                System.out.println("[Error] Invalid input. Please enter again!!");
            }
        }
    }
    //--------------------------------------------------------------------------
    public static float getFloat(String pr){
        Scanner sc = new Scanner(System.in);
        float result = 0;
        boolean validInput = false;
        while(!validInput){
            try{
            System.out.println(pr);
            String input = sc.nextLine();  
            result = Float.parseFloat(input);
            validInput = true;
            }
            catch(NumberFormatException e){
                System.out.println("[Error] Invalid input! Please enter again.");
            }
        }
        return result;
    }

    //--------------------------------------------------------------------------
    public static String checkValue(String value,String regex){
        if (value.matches(regex)) return value;
        return null;
    }
}

