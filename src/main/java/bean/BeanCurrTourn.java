package bean;

public class BeanCurrTourn {
    private static BeanCurrTourn instance;
    private int sno;
    private String tName;
    private String nPartecipants;
    private String nSubscribed;
    private String dates;

    public BeanCurrTourn() {
        //Costruttore
    }

    public static BeanCurrTourn getInstance() {
        if (instance == null) {
            instance = new BeanCurrTourn();
        }
        return instance;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public void settName(String tName) { this.tName = tName; }

    public void setDates(String dates) { this.dates = dates;}

    public void setnPartecipants(String nPartecipants) { this.nPartecipants = nPartecipants;}

    public void setnSubscribed(String nSubscribed) { this.nSubscribed = nSubscribed;}

    public int getSno() {
        return sno;
    }

    public String getDates() { return dates; }

    public String getnPartecipants() { return nPartecipants; }

    public String getnSubscribed() { return nSubscribed; }

    public String gettName() { return tName; }
}
