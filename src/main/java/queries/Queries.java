package queries;

public class Queries {
    private Queries(){}

    static final String QUERY_LOGIN = "SELECT * FROM trafalgarbattles.login WHERE username = ? and password = ? and type = ?";

    public static String getQueryLogin(){return QUERY_LOGIN;}

    static final String QUERY_ALL_TOURNAMENTS = "SELECT * FROM trafalgarbattles.tournaments;";

    public static String getQueryAllTournaments() {
        return QUERY_ALL_TOURNAMENTS;
    }

    static final String REGISTER_USER = "INSERT INTO trafalgarbattles.login (username, password, email, type) VALUES (?, ?, ?, ?);";

    public static String getRegisterUser() { return REGISTER_USER; }

    static final String QUERY_CURR_TOURN = "SELECT * FROM trafalgarbattles.tournaments WHERE sno = ?;";

    public static String getQueryCurrTourn() { return QUERY_CURR_TOURN; }

    static final String QUERY_ADD_SUB = "INSERT INTO trafalgarbattles.subscription (username, tName) VALUES (?, ?);";

    public static String getQueryAddSub() { return QUERY_ADD_SUB; }

    static final String QUERY_SUB_TOURNAMENTS = "SELECT t.sno, t.tName, t.dates, t.nPartecipants, t.nSubscribed FROM tournaments t INNER JOIN subscription s ON t.tName = s.tName WHERE s.username = ?";

    public static String getQuerySubTournaments() { return QUERY_SUB_TOURNAMENTS; }
}
