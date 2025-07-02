package dao;

import users.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CompositeLogRegDAO implements LogRegDAO {
    private final LogRegDAO dbDao;
    private final LogRegDAO fileSystemDao;

    Logger logger = Logger.getLogger(getClass().getName());


    public CompositeLogRegDAO(LogRegDAO dbDao, LogRegDAO fileSystemDao) {
        this.dbDao = dbDao;
        this.fileSystemDao = fileSystemDao;
    }

    @Override
    public User getLogInfo(String username, String password, String type) throws SQLException, IOException {
        User user = null;
        SQLException dbSqlException = null;
        IOException dbIoException = null;
        IllegalArgumentException dbIllegalArgumentException = null;

        try {
            user = dbDao.getLogInfo(username, password, type);
            return user;
        } catch (SQLException e) {
            dbSqlException = e;
            logger.info("Composite: DB login SQL error: " + e.getMessage());
        } catch (IOException e) {
            dbIoException = e;
            logger.info("Composite: DB login IO error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            dbIllegalArgumentException = e;
            logger.info("Composite: DB login invalid credentials/not found: " + e.getMessage());
        }

        try {
            user = fileSystemDao.getLogInfo(username, password, type);
            return user;
        } catch (IllegalArgumentException e) {
            if (dbIllegalArgumentException != null) {
                throw dbIllegalArgumentException;
            } else {
                throw e;
            }
        } catch (IOException e) {
            if (dbSqlException != null) {
                throw new SQLException("Composite: DB and FileSystem login failed.", dbSqlException);
            } else if (dbIoException != null) {
                throw new IOException("Composite: DB and FileSystem login failed.", dbIoException);
            } else {
                throw e;
            }
        }
    }

    @Override
    public boolean register(String email, String username, String password, String type) throws SQLException, IOException {
        Exception dbException = null;
        Exception fileSystemException = null;

        try {
            dbDao.register(email, username, password, type);
        } catch (Exception e) {
            dbException = e;
            logger.info("Composite: DB registration failed for " + username + ": " + e.getMessage());
        }

        try {
            fileSystemDao.register(email, username, password, type);
        } catch (Exception e) {
            fileSystemException = e;
            logger.info("Composite: FileSystem registration failed for " + username + ": " + e.getMessage());
        }

        if (dbException == null && fileSystemException == null) {
            return true;
        } else if (dbException != null && fileSystemException != null) {
            throw new IOException("Registration failed in both DB and FileSystem. DB Error: " + dbException.getMessage() + "; FS Error: " + fileSystemException.getMessage(), dbException);
        } else if (dbException != null) {
            logger.info("Composite Warning: DB registration failed but FileSystem succeeded for {}" + username);
            throw new IOException("DB registration failed, but FileSystem succeeded. Data may be inconsistent.", dbException);
        } else {
            logger.info("Composite Warning: FileSystem registration failed but DB succeeded for {}" + username);
            return true;
        }
    }
}