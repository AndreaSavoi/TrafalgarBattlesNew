package com.example.trafalgarbattlesnew;

import graphiccontrollerscli.MainCLI;
import com.example.trafalgarbattlesnew.graphiccontrollers.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainLauncher {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Graphical User Interface (GUI)");
        System.out.println("2. Command Line Interface (CLI)");
        System.out.print("Insert your choice (1 or 2): ");

        try {
            String choice = reader.readLine();
            switch (choice.trim()) {
                case "1":
                    System.out.println("Loading Graphical User Interface...");
                    Main.main(args);
                    break;
                case "2":
                    System.out.println("Loading Command Line Interface...");
                    MainCLI.main(args);
                    break;
                default:
                    System.out.println("Not valid choice. Try again.");
                    main(args);
                    break;
            }
        } catch (IOException e) {
            System.err.println("I/O error during input: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error while starting: " + e.getMessage());
            System.exit(1);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Error while closing the reader: " + e.getMessage());
            }
        }
    }
}