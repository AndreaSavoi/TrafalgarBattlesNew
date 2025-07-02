package dao;

import java.io.IOException;
import java.sql.SQLException;

public class FileSystemDAOFactory implements DAOFactory {
    @Override
    public LogRegDAO createLogRegDAO() throws IOException {
        return new LogRegDAOFileSystemImpl(); // Restituisce l'implementazione ibrida
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