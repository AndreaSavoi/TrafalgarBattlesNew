package queries;

public class Queries {
    private Queries(){}

    static final String QueryLogin= "SELECT * FROM trafalgarbattles.login WHERE username = ? and password = ?";

    public static String getQueryLogin(){return QueryLogin;}

    static final String QueryAllTournaments = "SELECT * FROM trafalgarbattles.tournaments;";

    public static String getQueryAllTournaments() {
        return QueryAllTournaments;
    }

    static final String RegisterUser = "INSERT INTO trafalgarbattles.login (username, password, email) VALUES (?, ?, ?);";

    public static String getRegisterUser() { return RegisterUser; }

    static final String QueryCurrTourn = "SELECT * FROM trafalgarbattles.tournaments WHERE sno = ?;";

    public static String getQueryCurrTourn() { return QueryCurrTourn; }

    static final String QueryAddSub = "INSERT INTO trafalgarbattles.subscription (names, tname) VALUES (?, ?);";

    public static String getQueryAddSub() { return QueryAddSub; }
}
