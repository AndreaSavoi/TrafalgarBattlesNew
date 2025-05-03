package users;

public abstract class User {
    protected String username;

    protected User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public abstract String getDashboardFXML();
}
