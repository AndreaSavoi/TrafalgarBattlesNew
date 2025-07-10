package applicationcontrollers;

import bean.BeanLog;
import bean.BeanReg;
import dao.LogRegDAO;
import graphiccontrollerscli.MainLauncher;

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
            LogRegDAO logDao = MainLauncher.getDaoFactory().createLogRegDAO();
            logDao.getLogInfo(username, password, type);
        } catch (SQLException | IOException _){
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public void register() {
        try{
            LogRegDAO regDAO = MainLauncher.getDaoFactory().createLogRegDAO();
            regDAO.register(email, username, password, type);
        } catch (SQLException | IOException _){
            throw new IllegalArgumentException("Something went wrong");
        }
    }
}

