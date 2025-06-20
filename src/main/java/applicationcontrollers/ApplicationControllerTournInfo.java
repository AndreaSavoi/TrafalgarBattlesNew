package applicationcontrollers;

import bean.BeanCurrTourn;
import dao.TournInfoDAO;
import exception.AlreadySubscribedException;
import exception.MaxParticipantsReachedException;
import exception.UserNotSubscribedException;
import graphiccontrollerscli.MainLauncher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApplicationControllerTournInfo {

    List<String> curr = new ArrayList<>();
    private final TournInfoDAO getCurrTurnInfo;

    public ApplicationControllerTournInfo(String username, String tname, String mode) throws SQLException, IOException, AlreadySubscribedException, MaxParticipantsReachedException, UserNotSubscribedException {
        getCurrTurnInfo = MainLauncher.getDaoFactory().createTournInfoDAO();
        if(Objects.equals(mode, "sub")) {
            addSub(username, tname);
        } else if (Objects.equals(mode, "unsub")) {
            delSub(username, tname);
        }
    }

    public ApplicationControllerTournInfo(BeanCurrTourn bCT) throws SQLException, IOException {
        getCurrTurnInfo = MainLauncher.getDaoFactory().createTournInfoDAO();
        addDatas(bCT);
    }

    private void addDatas(BeanCurrTourn bCT) throws SQLException, IOException {
        getCurrTurnInfo.getSpecific(curr, bCT.getSno());
        bCT.settName(curr.get(0));
        bCT.setnPartecipants(curr.get(1));
        bCT.setnSubscribed(curr.get(2));
        bCT.setDates(curr.get(3));
    }

    private void addSub(String username, String tname) throws SQLException, IOException, AlreadySubscribedException, MaxParticipantsReachedException {
        try {
            getCurrTurnInfo.addSub(username, tname);
        } catch (SQLException e) {
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("already registered")) {
                throw new AlreadySubscribedException("You're already subscribed to this tournament.");
            } else if (msg.contains("max participants")) {
                throw new MaxParticipantsReachedException("Max partecipants reached for this tournament.");
            } else {
                throw e;
            }
        }
    }

    private void delSub(String username, String tname) throws SQLException, IOException, UserNotSubscribedException {
        try {
            getCurrTurnInfo.removeSub(username, tname);
        } catch (SQLException e) {
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("not registered")) {
                throw new UserNotSubscribedException("You're not subscribed to this tournament.");
            } else {
                throw e;
            }
        }
    }
}