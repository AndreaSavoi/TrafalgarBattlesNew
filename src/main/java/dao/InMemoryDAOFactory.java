
package dao;

public class InMemoryDAOFactory implements DAOFactory {
    @Override
    public LogRegDAO createLogRegDAO() {
        return new LogRegDAOInMemoryImpl();
    }

    @Override
    public ProfileDAO createProfileDAO() {
        return new ProfileDAOInMemoryImpl();
    }

    @Override
    public TournInfoDAO createTournInfoDAO() {
        return new TournInfoDAOInMemoryImpl();
    }

    @Override
    public TournOrgDAO createTournOrgDAO() {
        return new TournOrgDAOInMemoryImpl();
    }
}