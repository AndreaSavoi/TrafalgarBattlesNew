package dao;

import java.util.HashMap;
import java.util.Map;

public class ProfileDAOInMemoryImpl implements ProfileDAO {

    private static final String KEY_BIRTH = "birth";
    private static final String KEY_GAME = "game";
    private static final String KEY_SEX = "sex";
    private static final String KEY_FULLNAME = "fullname";

    private static final Map<String, Map<String, String>> userProfiles = new HashMap<>();

    public ProfileDAOInMemoryImpl() {
        if (userProfiles.isEmpty()) {
            Map<String, String> playerProfile = new HashMap<>();
            playerProfile.put(KEY_BIRTH, "2000-01-01");
            playerProfile.put(KEY_GAME, "Chess");
            playerProfile.put(KEY_SEX, "Male");
            playerProfile.put(KEY_FULLNAME, "Test Player");
            userProfiles.put("testplayer", playerProfile);

            Map<String, String> organizerProfile = new HashMap<>();
            organizerProfile.put(KEY_BIRTH, "1990-05-10");
            organizerProfile.put(KEY_GAME, "Poker");
            organizerProfile.put(KEY_SEX, "Female");
            organizerProfile.put(KEY_FULLNAME, "Test Organizer");
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
        if (birth != null && !birth.isBlank()) profile.put(KEY_BIRTH, birth);
        if (game != null && !game.isBlank()) profile.put(KEY_GAME, game);
        if (sex != null && !sex.isBlank()) profile.put(KEY_SEX, sex);
        if (fullname != null && !fullname.isBlank()) profile.put(KEY_FULLNAME, fullname);
    }
}