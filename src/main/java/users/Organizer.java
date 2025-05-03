package users;

public class Organizer extends User {

    public Organizer(String username) {
        super(username);
    }

    @Override
    public String getDashboardFXML() {
        return "MainViewOrganizer.fxml";
    }
}