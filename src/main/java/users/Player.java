package users;

public class Player extends User {

    public Player(String username) {
        super(username);
    }

    @Override
    public String getDashboardFXML() {
        return "MainView.fxml";
    }
}
