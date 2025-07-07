package applicationcontrollers;

import bean.BeanTournList;
import dao.TournInfoDAO;
import graphiccontrollerscli.MainLauncher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationControllerTournaments {
    private final List<String> tName = new ArrayList<>();
    private final List<String> nparticipants = new ArrayList<>();
    private final List<String> nSubscribed = new ArrayList<>();
    private final List<String> dates = new ArrayList<>();
    private final List<String> sno = new ArrayList<>();
    private final TournInfoDAO getTournamentsInfoDAO;
    private boolean hasTournaments = false;

    public ApplicationControllerTournaments(BeanTournList tL, String mode, String username) throws SQLException, IOException {
        this.getTournamentsInfoDAO = MainLauncher.getDaoFactory().createTournInfoDAO();
        this.hasTournaments = addDatas(tL, mode, username);
    }

    public boolean hasTournaments() {
        return hasTournaments;
    }

    private boolean addDatas(BeanTournList tL, String mode, String username) throws SQLException, IOException {

        getTournamentsInfoDAO.getAllInfo(tName, nparticipants, nSubscribed, dates, sno, mode, username);

        if(sno.isEmpty()) {
            return false;
        }

        for (int i = 0;i < sno.size();i++) {
            tL.addName(tName.get(i));
            tL.addNP(nparticipants.get(i));
            tL.addNS(nSubscribed.get(i));
            tL.addDates(dates.get(i));
            tL.addSNO(sno.get(i));
        }

        return true;
    }

}
