package queries;

public class Queries {
    private Queries(){}

    static final String QUERY_LOGIN = "SELECT * FROM trafalgarbattles.login WHERE username = ? and password = ?";

    public static String getQueryLogin(){return QUERY_LOGIN;}

    static final String QUERY_ALL_TOURNAMENTS = "SELECT * FROM trafalgarbattles.tournaments;";

    public static String getQueryAllTournaments() {
        return QUERY_ALL_TOURNAMENTS;
    }

    static final String REGISTER_USER = "INSERT INTO trafalgarbattles.login (username, password, email) VALUES (?, ?, ?);";

    public static String getRegisterUser() { return REGISTER_USER; }

    static final String QUERY_CURR_TOURN = "SELECT * FROM trafalgarbattles.tournaments WHERE sno = ?;";

    public static String getQueryCurrTourn() { return QUERY_CURR_TOURN; }

    static final String QUERY_ADD_SUB = "INSERT INTO trafalgarbattles.subscription (names, tname) VALUES (?, ?);";

    public static String getQueryAddSub() { return QUERY_ADD_SUB; }
}
