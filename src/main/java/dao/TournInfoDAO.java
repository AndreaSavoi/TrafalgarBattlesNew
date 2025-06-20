package dao;

import exception.AlreadySubscribedException;
import exception.MaxParticipantsReachedException;
import exception.UserNotSubscribedException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TournInfoDAO {
    void getAllInfo(List<String> tName, List<String> nPartecipants, List<String> nSubscribed, List<String> dates, List<String> sno, String mode, String username) throws SQLException, IOException;

    void getSpecific(List<String> curr, int sno) throws SQLException, IOException;

    void addSub(String username, String tname) throws SQLException, IOException, AlreadySubscribedException, MaxParticipantsReachedException;

    void removeSub(String username, String tName) throws SQLException, IOException, UserNotSubscribedException;
}

