package bean;

public class BeanLog {
    private String username;

    private String password;

    public BeanLog(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {return username;}

    public String getPassword() {return password;}
}
