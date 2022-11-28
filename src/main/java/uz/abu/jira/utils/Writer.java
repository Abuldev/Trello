package uz.muhammad.jira.utils;

import java.io.PrintStream;
import java.util.List;

/**
 * @author "Elmurodov Javohir"
 * @since 14/06/22/15:56 (Tuesday)
 * untitled/IntelliJ IDEA
 */
public class Writer {
    private static final PrintStream out = null;


    public static void print(Object data) {
        print(data, Color.BLUE);
    }

    public static void print(Object data, String color) {
        System.out.print(color + data + Color.RESET);
    }

    public static void println(Object data) {
        println(data, Color.BLUE);
    }

    public static void println(Object data, String color) {
        System.out.println(color + data + Color.RESET);
    }

    public static void printMiddle(String text, int space, char with){
        int mid = (space-text.length())/2;
        for (int i = 0; i < mid; i++) {
            System.out.print(with);
        }
        Writer.print(text);
        for (int i = 0; i < mid; i++) {
            System.out.print(with);
        }
    }

    public static void printMiddle(String text, int space, char with, String color){
        int mid = (space-text.length())/2;
        for (int i = 0; i < mid; i++) {
            System.out.print(with);
        }
        Writer.print(text,color);
        for (int i = 0; i < mid; i++) {
            System.out.print(with);
        }
    }

    public static void printlnMiddle(String text, int space, char with, String color){
        printMiddle(text,space,with,color);
        Writer.println("");
    }

    public static void printlnMiddles(String[] texts, int space, char with, String color){
        for (String text : texts) {
            printlnMiddle(text,space,with,color);
        }
    }

    public static void printlnMiddle(String text, int space){
        printMiddle(text,space,' ', Color.GREEN);
        Writer.println("");
    }

    public static void printRight(String text, int space, char with, String color){
        int mid = space-text.length();
        for (int i = 0; i < mid; i++) {
            System.out.print(with);
        }
        Writer.print(text,color);
    }


    public static void printlnRight(String text, int space, char with, String color){
        printRight(text,space,with,color);
        Writer.println("");
    }

    public static void printMiddleFixed(String text, int space, char with, String color) {
        int mid = (space - text.length()) / 2;
        for (int i = 0; i < mid; i++) {
            System.out.print(with);
        }
        Writer.print(text, color);

    }

}
