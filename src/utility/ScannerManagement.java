package utility;

import java.util.Scanner;

public class ScannerManagement {

    private static final Scanner sc = new Scanner(System.in);

    public static String getUserInput(String userInput) {
        return sc.nextLine();
    }

    public static void closeScanner() {
        sc.close();
    }
}
