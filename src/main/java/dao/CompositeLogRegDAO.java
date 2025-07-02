package dao;

import users.User;

import java.io.IOException;
import java.sql.SQLException;

public class CompositeLogRegDAO implements LogRegDAO {
    private final LogRegDAO dbDao;
    private final LogRegDAO fileSystemDao;

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
            System.out.println("Composite: Logged in (DB) -> " + username);
            return user;
        } catch (SQLException e) {
            dbSqlException = e;
            System.err.println("Composite: DB login SQL error: " + e.getMessage());
        } catch (IOException e) {
            dbIoException = e;
            System.err.println("Composite: DB login IO error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            dbIllegalArgumentException = e;
            System.err.println("Composite: DB login invalid credentials/not found: " + e.getMessage());
        }

        try {
            user = fileSystemDao.getLogInfo(username, password, type);
            System.out.println("Composite: Logged in (FileSystem) -> " + username);
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
            System.out.println("Composite: Registered to DB -> " + username);
        } catch (Exception e) {
            dbException = e;
            System.err.println("Composite: DB registration failed for " + username + ": " + e.getMessage());
        }

        try {
            fileSystemDao.register(email, username, password, type);
            System.out.println("Composite: Registered to FileSystem -> " + username);
        } catch (Exception e) {
            fileSystemException = e;
            System.err.println("Composite: FileSystem registration failed for " + username + ": " + e.getMessage());
        }

        if (dbException == null && fileSystemException == null) {
            return true;
        } else if (dbException != null && fileSystemException != null) {
            throw new IOException("Registration failed in both DB and FileSystem. DB Error: " + dbException.getMessage() + "; FS Error: " + fileSystemException.getMessage(), dbException);
        } else if (dbException != null) {
            System.err.println("Composite Warning: DB registration failed but FileSystem succeeded for " + username);
            throw new IOException("DB registration failed, but FileSystem succeeded. Data may be inconsistent.", dbException);
        } else {
            System.err.println("Composite Warning: FileSystem registration failed but DB succeeded for " + username);
            return true;
        }
    }
}