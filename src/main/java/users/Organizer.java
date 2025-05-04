package users;

public class Organizer extends User {

    public Organizer(String username, String email) {
        super(username, email);
    }

    @Override
    public String getDashboardFXML() {
        return "MainViewOrganizer.fxml";
    }
}