package uz.muhammad.jira.utils;

import uz.muhammad.jira.services.auth.CommentService;

import java.util.Scanner;

public class Reader {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Scanner scannerInt = new Scanner(System.in);

    public static String readLine() {
        return scanner.nextLine();
    }

    public static int readInt() {
        return scannerInt.nextInt();
    }


    public static String readLine(String placeHolder) {
        return readLine(placeHolder, Color.BLUE);
    }


    public static String readLine(String placeHolder, String color) {
        Writer.print(color + placeHolder + Color.RESET);
        return readLine();
    }

    public static int readInt(String placeHolder, String color) {
        Writer.print(color + placeHolder + Color.RESET);
        return readInt();
    }

    public static int readInt(String placeHolder) {
        return readInt(Color.GREEN, placeHolder);
    }

    public static int readIntMiddle(String placeHolder) {
        Writer.printMiddleFixed(placeHolder, 80, ' ', Color.GREEN);
        return readInt();
    }

    public static String readLineMiddle(String s) {
        Writer.printMiddleFixed(s, 80, ' ', Color.GREEN);
        return readLine();
    }
}