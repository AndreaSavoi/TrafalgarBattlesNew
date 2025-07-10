package applicationcontrollers;

import dao.TournOrgDAO;
import graphiccontrollerscli.MainLauncher;

import java.io.IOException;
import java.sql.SQLException;

public class ApplicationControllerDeleteTourn {

    public void deleteTournament(String name) throws SQLException, IOException {
        TournOrgDAO dao = MainLauncher.getDaoFactory().createTournOrgDAO();
        dao.deleteTourn(name);
    }

}
