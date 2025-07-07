package bean;

public class BeanCurrTourn {
    private static BeanCurrTourn instance;
    private int sno;
    private String tName;
    private String nparticipants;
    private String nSubscribed;
    private String dates;

    public BeanCurrTourn() {
        //Constructor
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

    public void setnparticipants(String nparticipants) { this.nparticipants = nparticipants;}

    public void setnSubscribed(String nSubscribed) { this.nSubscribed = nSubscribed;}

    public int getSno() {
        return sno;
    }

    public String getDates() { return dates; }

    public String getnparticipants() { return nparticipants; }

    public String getnSubscribed() { return nSubscribed; }

    public String gettName() { return tName; }
}
