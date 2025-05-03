package bean;

public class BeanLog {
    private String username;

    private String password;

    private String type;

    public BeanLog(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {return username;}

    public String getPassword() {return password;}

    public String getType() {return type;}
}
