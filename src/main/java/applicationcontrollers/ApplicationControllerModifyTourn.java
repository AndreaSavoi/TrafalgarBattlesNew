package applicationcontrollers;

import bean.BeanTournCreation;
import dao.TournOrgDAO;
import dao.TournOrgDAOImpl;
import graphiccontrollerscli.MainLauncher;

import java.io.IOException;
import java.sql.SQLException;

public class ApplicationControllerModifyTourn {

    public ApplicationControllerModifyTourn(BeanTournCreation bean) throws SQLException, IOException {
        TournOrgDAO dao = MainLauncher.getDaoFactory().createTournOrgDAO();
        dao.modifyTourn(bean);
    }

}
