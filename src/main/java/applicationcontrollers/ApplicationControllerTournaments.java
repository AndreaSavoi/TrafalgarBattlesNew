package applicationcontrollers;

import bean.BeanTournList;
import dao.TournInfoDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationControllerTournaments {
    private List<String> tName = new ArrayList<>();
    private List<String> nPartecipants = new ArrayList<>();
    private List<String> nSubscribed = new ArrayList<>();
    private List<String> dates = new ArrayList<>();
    private List<String> sno = new ArrayList<>();
    private TournInfoDAOImpl getTournamentsInfoDAO;
    private boolean hasTournaments = false;

    public ApplicationControllerTournaments(BeanTournList tL, String mode, String username) throws SQLException, IOException {
        this.hasTournaments = addDatas(tL, mode, username);
    }

    public boolean hasTournaments() {
        return hasTournaments;
    }

    private boolean addDatas(BeanTournList tL, String mode, String username) throws SQLException, IOException {

        getTournamentsInfoDAO = new TournInfoDAOImpl();
        getTournamentsInfoDAO.getAllInfo(tName, nPartecipants, nSubscribed, dates, sno, mode, username);

        if(sno.isEmpty()) {
            return false;
        }

        for (int i = 0;i < sno.size();i++) {
            tL.addName(tName.get(i));
            tL.addNP(nPartecipants.get(i));
            tL.addNS(nSubscribed.get(i));
            tL.addDates(dates.get(i));
            tL.addSNO(sno.get(i));
        }

        return true;
    }

}
