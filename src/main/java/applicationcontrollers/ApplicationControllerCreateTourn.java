package applicationcontrollers;

import bean.BeanTournCreation;
import dao.TournOrgDAO;
import graphiccontrollerscli.MainLauncher;

import java.io.IOException;
import java.sql.SQLException;

public class ApplicationControllerCreateTourn {

    public ApplicationControllerCreateTourn(BeanTournCreation bean) throws SQLException, IOException {
        TournOrgDAO dao = MainLauncher.getDaoFactory().createTournOrgDAO();
        dao.addTourn(bean);
    }

}
