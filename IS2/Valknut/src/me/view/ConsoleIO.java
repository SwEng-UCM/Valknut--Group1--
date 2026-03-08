package me.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class ConsoleIO {

    protected final Scanner sc;

    public ConsoleIO() {
        this.sc = new Scanner(System.in);
    }

    public void print(String s){
        System.out.print(s);
    }

    public void printLine(String s) {
        System.out.println(s);
    }

    public String readPrompt() {
        String input = sc.next();

        while (input.isEmpty()) {
            System.err.println("Error: Empty String. Please try again.");
            input = sc.next();
        }

        return input;
    }

    public int parseIntInRange(int j, int k) {
        int i = -50;
        while (i < j || i > k) {
            try{
                i = sc.nextInt(); 
                if (i < j || i > k) {
                    System.err.println("Please enter a number between " + j + " and " + k + ".");
                }   
            }
            catch (InputMismatchException imme) {
                    System.err.println("Please enter a number between " + j + " and " + k + ".");
            }
        }

        return i;
    }
}