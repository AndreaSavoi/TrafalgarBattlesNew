package applicationcontrollers;

import bean.BeanTournCreation;
import dao.TournOrgDAOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class ApplicationControllerModifyTourn {

    public ApplicationControllerModifyTourn(BeanTournCreation bean) throws SQLException, IOException {
        TournOrgDAOImpl dao = new TournOrgDAOImpl();
        dao.modifyTourn(bean);
    }

}
