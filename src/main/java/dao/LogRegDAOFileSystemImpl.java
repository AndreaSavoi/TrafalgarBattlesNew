package dao;

import singleton.SessionManager;
import users.Organizer;
import users.Player;
import users.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LogRegDAOFileSystemImpl implements LogRegDAO {

    private static final String FILE_NAME = "users.ser"; // File per salvare i dati serializzati
    private Map<String, SerializableUserDetails> users;

    public LogRegDAOFileSystemImpl() {
        loadUsersFromFile();
    }

    private void loadUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            users = (Map<String, SerializableUserDetails>) ois.readObject();
        } catch (FileNotFoundException _) {
            users = new HashMap<>();
            if (users.isEmpty()) { // Aggiungi dati di esempio solo se il file è nuovo e vuoto
                users.put("fsplayer", new SerializableUserDetails("fsplayer", "fspass", "fsplayer@example.com", "Player"));
                users.put("fsorganizer", new SerializableUserDetails("fsorganizer", "fsorganizerpass", "fsorganizer@example.com", "Organizer"));
                saveUsersToFile();
            }
        } catch (IOException | ClassNotFoundException _) {
            users = new HashMap<>();
        }
    }

    private void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException _) {
            throw new IllegalArgumentException("Could not save users to file");
        }
    }

    @Override
    public User getLogInfo(String username, String password, String type) throws IOException {
        SerializableUserDetails userDetails = users.get(username);
        if (userDetails != null && userDetails.password().equals(password) && userDetails.type().equals(type)) {
            User user;
            if (type.equals("Player")) {
                user = new Player(username, userDetails.email());
            } else if (type.equals("Organizer")) {
                user = new Organizer(username, userDetails.email());
            } else {
                throw new IllegalArgumentException("Invalid user type during login.");
            }
            SessionManager.setCurrentUser(user);
            return user;
        }
        throw new IllegalArgumentException("Invalid credentials or user type.");
    }

    @Override
    public boolean register(String email, String username, String password, String type) throws IOException {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }
        for (SerializableUserDetails userDetails : users.values()) {
            if (userDetails.email().equals(email)) {
                throw new IllegalArgumentException("Email already registered.");
            }
        }

        users.put(username, new SerializableUserDetails(username, password, email, type));
        saveUsersToFile();
        return true;
    }
}