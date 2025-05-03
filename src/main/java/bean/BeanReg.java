package bean;

public class BeanReg extends BeanLog{
    private final String email;

    public BeanReg(String email, String username, String password, String type) {
        super(username, password, type);
        this.email = email;
    }

    public String getEmail() { return email; }
}
