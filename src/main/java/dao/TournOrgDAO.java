package dao;

import bean.BeanTournCreation;

import java.io.IOException;
import java.sql.SQLException;

public interface TournOrgDAO {

    public void addTourn(BeanTournCreation bean) throws SQLException, IOException;
    public void modifyTourn(BeanTournCreation bean) throws SQLException, IOException;

    public void deleteTourn(String name) throws SQLException, IOException;
}
