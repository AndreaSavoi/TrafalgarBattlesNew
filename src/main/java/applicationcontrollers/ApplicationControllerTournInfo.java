package applicationcontrollers;

import bean.BeanCurrTourn;
import dao.TournInfoDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationControllerTournInfo {

    List<String> curr = new ArrayList<>();
    private TournInfoDAOImpl getCurrTurnInfo;
    public ApplicationControllerTournInfo(String username, String tname) throws SQLException, IOException {
        addSub(username, tname);
    }
    public ApplicationControllerTournInfo(BeanCurrTourn bCT) throws SQLException, IOException {
        addDatas(bCT);
    }

    private void addDatas(BeanCurrTourn bCT) throws SQLException, IOException {
        getCurrTurnInfo = new TournInfoDAOImpl();
        getCurrTurnInfo.getSpecific(curr, bCT.getSno());
        bCT.settName(curr.get(0));
        bCT.setnPartecipants(curr.get(1));
        bCT.setnSubscribed(curr.get(2));
        bCT.setDates(curr.get(3));
    }

    private void addSub(String username, String tname) throws SQLException, IOException {
        getCurrTurnInfo = new TournInfoDAOImpl();
        getCurrTurnInfo.addSub(username, tname);
    }
}