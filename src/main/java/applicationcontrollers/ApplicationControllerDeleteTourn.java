package applicationcontrollers;

import dao.TournOrgDAOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class ApplicationControllerDeleteTourn {

    public void deleteTournament(String name) throws SQLException, IOException {
        TournOrgDAOImpl dao = new TournOrgDAOImpl();
        dao.deleteTourn(name);
    }

}
