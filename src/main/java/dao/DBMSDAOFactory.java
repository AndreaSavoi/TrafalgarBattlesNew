package dao;

import java.io.IOException;
import java.sql.SQLException;

public class DBMSDAOFactory implements DAOFactory {
    @Override
    public LogRegDAO createLogRegDAO() throws IOException {
        try {
            return new LogRegDAOImpl();
        } catch (SQLException e) {
            throw new IOException("Error creating LogRegDAOImpl", e);
        }
    }

    @Override
    public ProfileDAO createProfileDAO() throws IOException {
        return new ProfileDAOImpl();
    }

    @Override
    public TournInfoDAO createTournInfoDAO() throws IOException {
        return new TournInfoDAOImpl();
    }

    @Override
    public TournOrgDAO createTournOrgDAO() throws IOException {
        return new TournOrgDAOImpl();
    }
}
