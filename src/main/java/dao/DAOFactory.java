package dao;

import java.io.IOException;

public interface DAOFactory {
    LogRegDAO createLogRegDAO() throws IOException;
    ProfileDAO createProfileDAO() throws IOException;
    TournInfoDAO createTournInfoDAO() throws IOException;
    TournOrgDAO createTournOrgDAO() throws IOException;
}