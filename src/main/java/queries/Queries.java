package queries;

public class Queries {
    private Queries(){}

    static final String QUERY_LOGIN = "SELECT * FROM trafalgarbattles.login WHERE username = ? and password = ? and type = ?";

    public static String getQueryLogin(){   return QUERY_LOGIN; }

    static final String QUERY_ALL_TOURNAMENTS = "SELECT * FROM trafalgarbattles.tournaments;";

    public static String getQueryAllTournaments() { return QUERY_ALL_TOURNAMENTS;   }

    static final String REGISTER_USER = "INSERT INTO trafalgarbattles.login (username, password, email, type) VALUES (?, ?, ?, ?);";

    public static String getRegisterUser() { return REGISTER_USER; }

    static final String QUERY_CURR_TOURN = "SELECT * FROM trafalgarbattles.tournaments WHERE sno = ?;";

    public static String getQueryCurrTourn() { return QUERY_CURR_TOURN; }

    static final String QUERY_ADD_SUB = "INSERT INTO trafalgarbattles.subscription (username, tName) VALUES (?, ?);";

    public static String getQueryAddSub() { return QUERY_ADD_SUB; }

    static final String QUERY_SUB_TOURNAMENTS = "SELECT t.sno, t.tName, t.dates, t.nparticipants, t.nSubscribed FROM tournaments t INNER JOIN subscription s ON t.tName = s.tName WHERE s.username = ?";

    public static String getQuerySubTournaments() { return QUERY_SUB_TOURNAMENTS; }

    static final String QUERY_DEL_SUB = "DELETE FROM subscription WHERE username = ? AND tName = ?";

    public static String getQueryDelSub() { return QUERY_DEL_SUB; }

    static final String QUERY_CHECK_SUB = "SELECT 1 FROM subscription WHERE username = ? AND tName = ?";

    public static String getQueryCheckSub() { return QUERY_CHECK_SUB; }

    static final String QUERY_PROFILE_INFO = "SELECT birth, game, sex, fullname FROM login WHERE username = ?";

    public static String getQueryProfileInfo() { return QUERY_PROFILE_INFO; }

    static final String ADD_PROFILE_INFO = "UPDATE login SET birth = ?, game = ?, sex = ?, fullname = ? WHERE username = ?";

    public static String getAddProfileInfo() { return ADD_PROFILE_INFO; }

    static final String QUERY_ORG_TOURNAMENTS = "SELECT * FROM trafalgarbattles.tournaments WHERE organizer = ?";

    public static String getQueryOrgTournaments() { return QUERY_ORG_TOURNAMENTS; }

    static final String ADD_TOURNAMENT = "INSERT INTO trafalgarbattles.tournaments (tName, dates, nparticipants, nSubscribed, organizer) VALUES (?, ?, ?, ?, ?)";

    public static String getAddTournament() { return ADD_TOURNAMENT; }

    static final String MOD_TOURNAMENT = "UPDATE trafalgarbattles.tournaments SET dates = ?, nparticipants = ? WHERE tName = ?";

    public static String getModTournament() { return MOD_TOURNAMENT; }

    static final String DEL_TOURNAMENT = "DELETE FROM trafalgarbattles.tournaments WHERE tName = ?";

    public static String getDelTournament() { return DEL_TOURNAMENT; }
}
