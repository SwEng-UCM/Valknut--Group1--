package me.view;

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
        sc.nextLine(); //clean buffer

        while (input.isEmpty()) {
            System.err.println("Error: Empty String. Please try again.");
            input = sc.next();
            sc.nextLine(); //clean buffer
        }

        return input;
    }

    public int parseIntInRange(int j, int k) {
        int i = -50;
        while (i < j || i > k) {
            try{
                print("Select (ex: 1): ");
                String x = sc.next();
                sc.nextLine(); //clean buffer 
                i = Integer.parseInt(x);
                if (i < j || i > k) {
                    System.err.println("Please enter a number between " + j + " and " + k + "." + Messages.NEW_LINE);
                }   
            }
            catch (NumberFormatException imme) {
                    System.err.println("Please enter a number between " + j + " and " + k + "." + Messages.NEW_LINE);
            }
        }

        return i;
    }

    public void pause(){
        printLine(Messages.PRESS_EN);
        String s = sc.nextLine();
    }
}