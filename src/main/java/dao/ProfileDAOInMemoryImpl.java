package dao;

import java.util.HashMap;
import java.util.Map;

public class ProfileDAOInMemoryImpl implements ProfileDAO {
    private static final Map<String, Map<String, String>> userProfiles = new HashMap<>();

    public ProfileDAOInMemoryImpl() {
        if (userProfiles.isEmpty()) {
            Map<String, String> playerProfile = new HashMap<>();
            playerProfile.put("birth", "2000-01-01");
            playerProfile.put("game", "Chess");
            playerProfile.put("sex", "Male");
            playerProfile.put("fullname", "Test Player");
            userProfiles.put("testplayer", playerProfile);

            Map<String, String> organizerProfile = new HashMap<>();
            organizerProfile.put("birth", "1990-05-10");
            organizerProfile.put("game", "Poker");
            organizerProfile.put("sex", "Female");
            organizerProfile.put("fullname", "Test Organizer");
            userProfiles.put("testorganizer", organizerProfile);
        }
    }

    @Override
    public Map<String, String> getProfileInfo(String username) {
        return new HashMap<>(userProfiles.getOrDefault(username, new HashMap<>()));
    }

    @Override
    public void updateProfileInfo(String birth, String game, String sex, String fullname, String username) {
        Map<String, String> profile = userProfiles.computeIfAbsent(username, k -> new HashMap<>());
        if (birth != null && !birth.isBlank()) profile.put("birth", birth);
        if (game != null && !game.isBlank()) profile.put("game", game);
        if (sex != null && !sex.isBlank()) profile.put("sex", sex);
        if (fullname != null && !fullname.isBlank()) profile.put("fullname", fullname);
    }
}