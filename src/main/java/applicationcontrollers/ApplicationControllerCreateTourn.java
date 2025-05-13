package applicationcontrollers;

import bean.BeanTournCreation;
import dao.TournOrgDAOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class ApplicationControllerCreateTourn {

    public ApplicationControllerCreateTourn(BeanTournCreation bean) throws SQLException, IOException {
        TournOrgDAOImpl dao = new TournOrgDAOImpl();
        dao.addTourn(bean);
    }

}
