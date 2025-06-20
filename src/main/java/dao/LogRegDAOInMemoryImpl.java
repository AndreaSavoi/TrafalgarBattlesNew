package dao;

import singleton.SessionManager;
import users.Organizer;
import users.Player;
import users.User;

import java.util.HashMap;
import java.util.Map;

public class LogRegDAOInMemoryImpl implements LogRegDAO {

    private static final Map<String, UserDetailsForInMemory> users = new HashMap<>();

    private static class UserDetailsForInMemory {
        String username;
        String password;
        String email;
        String type;

        UserDetailsForInMemory(String username, String password, String email, String type) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.type = type;
        }
    }

    public LogRegDAOInMemoryImpl() {
        if (users.isEmpty()) {
            users.put("testplayer", new UserDetailsForInMemory("testplayer", "playerpass", "player@example.com", "Player"));
            users.put("testorganizer", new UserDetailsForInMemory("testorganizer", "orgpass", "organizer@example.com", "Organizer"));
        }
    }

    @Override
    public User getLogInfo(String username, String password, String type) {
        UserDetailsForInMemory userDetails = users.get(username);
        if (userDetails != null && userDetails.password.equals(password) && userDetails.type.equals(type)) {
            User user;
            if (type.equals("Player")) {
                user = new Player(username, userDetails.email);
            } else if (type.equals("Organizer")) {
                user = new Organizer(username, userDetails.email);
            } else {
                throw new IllegalArgumentException("Invalid user type during login.");
            }
            SessionManager.setCurrentUser(user);
            return user;
        }
        throw new IllegalArgumentException("Invalid credentials or user type.");
    }

    @Override
    public boolean register(String email, String username, String password, String type) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }
        for (UserDetailsForInMemory userDetails : users.values()) {
            if (userDetails.email.equals(email)) {
                throw new IllegalArgumentException("Email already registered.");
            }
        }

        users.put(username, new UserDetailsForInMemory(username, password, email, type));
        return true;
    }
}