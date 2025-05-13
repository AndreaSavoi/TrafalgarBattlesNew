package applicationcontrollers;

import bean.BeanTournCreation;
import dao.TournOrgDAOImpl;

public class ApplicationControllerCreateTourn {

    public ApplicationControllerCreateTourn(BeanTournCreation bean) throws Exception {
        TournOrgDAOImpl dao = new TournOrgDAOImpl();
        dao.addTourn(bean);
    }

}
