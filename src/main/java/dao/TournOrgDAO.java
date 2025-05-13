package dao;

import bean.BeanTournCreation;

import java.io.IOException;
import java.sql.SQLException;

public interface TournOrgDAO {

    public void addTourn(BeanTournCreation bean) throws SQLException, IOException;

}
