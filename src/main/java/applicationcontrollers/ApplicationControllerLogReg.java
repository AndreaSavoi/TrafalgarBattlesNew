package applicationcontrollers;

import bean.BeanLog;
import bean.BeanReg;
import dao.LogRegDAOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class ApplicationControllerLogReg {

    private String email = null;

    private final String username;

    private final String password;

    private final String type;

    public ApplicationControllerLogReg(BeanLog bL) {
        if(bL == null) {
            throw new IllegalArgumentException("Bean cannot be null");
        }

        username = bL.getUsername();
        password = bL.getPassword();
        type = bL.getType();

        if(bL instanceof BeanReg bR) {
            email = bR.getEmail();
            register();
        } else {
            verify();
        }
    }

    public void verify() {
        try {
            LogRegDAOImpl logDao = new LogRegDAOImpl();
            logDao.getLogInfo(username, password, type);
        } catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }

    public void register() {
        try{
            LogRegDAOImpl regDAO = new LogRegDAOImpl();
            regDAO.register(email, username, password, type);
        } catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }
}

