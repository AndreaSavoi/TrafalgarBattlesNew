package graphiccontrollerscli;

import com.example.trafalgarbattlesnew.graphiccontrollers.Main;
import dao.DBMSDAOFactory;
import dao.InMemoryDAOFactory;
import dao.DAOFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainLauncher {

    private static DAOFactory daoFactory;

    public static DAOFactory getDaoFactory() {
        return daoFactory;
    }

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            try {
                System.out.println("Please choose one of the following options:");
                System.out.println("1. Graphical User Interface (GUI)");
                System.out.println("2. Command Line Interface (CLI)");
                System.out.print("Insert your choice (1 or 2): ");
                String choice = reader.readLine();
                if ("1".equals(choice.trim())) {
                    System.out.println("Loading Graphical User Interface...");
                    DBChoice(reader);
                    Main.main(args);
                } else if ("2".equals(choice.trim())) {
                    System.out.println("Loading Command Line Interface...");
                    DBChoice(reader);
                    MainCLI.main(args);
                } else {
                    System.out.println("Not valid choice. Try again.");
                    main(args);
                }
            } catch (IOException e) {
                System.err.println("I/O error during input: " + e.getMessage());
                System.exit(1);
            } catch (Exception e) {
                System.err.println("Unexpected error while starting: " + e.getMessage());
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Error while closing the reader: " + e.getMessage());
        }
    }

    public static void DBChoice(BufferedReader reader) throws IOException {
        System.out.print("Choose mode: 1. Full-version (DBMS) 2. Demo-version (In-Memory): ");
        String modeChoice = reader.readLine();
        if ("1".equals(modeChoice.trim())) {
            daoFactory = new DBMSDAOFactory();
            System.out.println("Full-version selected.");
        } else if ("2".equals(modeChoice.trim())) {
            daoFactory = new InMemoryDAOFactory();
            System.out.println("Demo-version selected.");
        } else {
            System.out.println("Invalid mode choice. Defaulting to Full-version.");
            daoFactory = new DBMSDAOFactory();
        }
    }
}