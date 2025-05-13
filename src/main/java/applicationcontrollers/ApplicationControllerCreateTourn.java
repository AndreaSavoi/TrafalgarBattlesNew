package applicationcontrollers;

import bean.BeanTournCreation;
import dao.TournOrgDAO;
import dao.TournOrgDAOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class ApplicationControllerCreateTourn {

    public void createTournament(BeanTournCreation bean) throws Exception {
        TournOrgDAOImpl dao = new TournOrgDAOImpl();
        dao.addTourn(bean);
    }

}
