package users;

public class Player extends User {

    public Player(String username, String email) {
        super(username, email);
    }

    @Override
    public String getDashboardFXML() {
        return "MainView.fxml";
    }
}
