package graphiccontrollerscli;

import applicationcontrollers.ApplicationControllerLogReg;
import bean.BeanLog;
import bean.BeanReg;

import java.io.BufferedReader;
import java.io.IOException;

public class LogRegCLI {
    private final BufferedReader reader; //

    public LogRegCLI(BufferedReader reader) { //
        this.reader = reader; //
    }

    private record CommonLoginRegInput(String username, String password, String type) {}

    private CommonLoginRegInput getCommonLoginRegistrationInput() throws IOException {
        System.out.print("Username: "); //
        String username = reader.readLine();
        System.out.print("Password: "); //
        String password = reader.readLine();
        System.out.print("Are you an organizer? (y/n): "); //
        String roleChoice = reader.readLine();
        String type = roleChoice.equalsIgnoreCase("y") ? "Organizer" : "Player";
        return new CommonLoginRegInput(username, password, type);
    }

    public void showLoginForm() throws IOException {
        System.out.println("\n--- Login ---");
        CommonLoginRegInput commonInput = getCommonLoginRegistrationInput();

        String username = commonInput.username();
        String password = commonInput.password();
        String type = commonInput.type();

        if (username == null || username.isBlank() || password == null || password.isBlank()){
            System.out.println("Username or password can't be blank.");
            return;
        }

        BeanLog bL = new BeanLog(username, password, type);
        try{
            new ApplicationControllerLogReg(bL);
            System.out.println("Login successful.!");
        } catch (IllegalArgumentException e) {
            System.out.println("Login error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error during login.");
        }
    }

    public void showRegistrationForm(){
        try {
            System.out.println("\n--- Registration ---");
            System.out.print("Email: ");
            String email = reader.readLine();

            CommonLoginRegInput commonInput = getCommonLoginRegistrationInput();

            String username = commonInput.username();
            String password = commonInput.password();
            String type = commonInput.type();

            if (email == null || email.isBlank() || !email.contains("@") || username == null || username.isBlank() || password == null || password.isBlank()){
                System.out.println("Not valid credentials, please try again (make sure the mail contains '@' and all fields are entered correctly).");
                return;
            }

            BeanReg bR = new BeanReg(email, username, password, type); //
            try{
                new ApplicationControllerLogReg(bR); //
                System.out.println("Registration successful! You can now log in.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error during registration: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error during registration.");
            }
        } catch (IOException e) {
            System.err.println("I/O error during registration: " + e.getMessage());
        }
    }
}