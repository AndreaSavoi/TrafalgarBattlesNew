package applicationcontrollers;

import bean.BeanTournList;
import dao.TournInfoDAOImpl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationControllerTournaments {
    private List<String> tName = new ArrayList<>();
    private List<String> nPartecipants = new ArrayList<>();
    private List<String> nSubscribed = new ArrayList<>();
    private List<String> dates = new ArrayList<>();
    private List<String> sno = new ArrayList<>();
    private List<InputStream> logos = new ArrayList<>();
    private TournInfoDAOImpl getTournamentsInfoDAO;

    public ApplicationControllerTournaments(BeanTournList tL, String mode, String username) throws SQLException, IOException {
        addDatas(tL, mode, username);
    }

    private void addDatas(BeanTournList tL, String mode, String username) throws SQLException, IOException {

        getTournamentsInfoDAO = new TournInfoDAOImpl();
        getTournamentsInfoDAO.getAllInfo(tName, nPartecipants, nSubscribed, dates, sno, logos, mode, username);

        int counter = sno.size();

        for (int i = 0;i < counter;i++) {
            tL.addName(tName.get(i));
            tL.addNP(nPartecipants.get(i));
            tL.addNS(nSubscribed.get(i));
            tL.addDates(dates.get(i));
            tL.addSNO(sno.get(i));
            tL.addLogo(logos.get(i));
        }
    }


}
