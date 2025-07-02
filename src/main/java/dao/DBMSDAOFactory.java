package dao;

import java.io.IOException;
import java.sql.SQLException;

public class DBMSDAOFactory implements DAOFactory {
    @Override
    public LogRegDAO createLogRegDAO() throws IOException {
        LogRegDAO dbImpl = null;
        LogRegDAO fileSystemImpl = null;
        try {
            dbImpl = new LogRegDAOImpl();
            fileSystemImpl = new LogRegDAOFileSystemImpl();
        } catch (SQLException e) {
            throw new IOException("Error creating DB LogRegDAOImpl", e);
        }
        return new CompositeLogRegDAO(dbImpl, fileSystemImpl);
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
