package users;

public abstract class User {
    protected String username;

    protected String email;

    protected User(String username, String email) {
        this.username = username; this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {  return email; }

    public abstract String getDashboardFXML();
}
