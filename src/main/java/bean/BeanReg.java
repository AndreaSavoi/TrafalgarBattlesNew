package bean;

public class BeanReg extends BeanLog{
    private final String email;

    public BeanReg(String email, String username, String password) {
        super(username, password);
        this.email = email;
    }

    public String getEmail() { return email; }
}
