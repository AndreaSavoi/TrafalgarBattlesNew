package bean;

import java.util.ArrayList;
import java.util.List;

public class BeanTournList {
    public final List<String> tName;
    public final List<String> nPartecipants;
    public final List<String> nSubscribed;
    public final List<String> dates;
    public final List<String> sno;

    public BeanTournList() {
        tName = new ArrayList<>();
        nPartecipants = new ArrayList<>();
        nSubscribed = new ArrayList<>();
        dates = new ArrayList<>();
        sno = new ArrayList<>();
    }

    public void addName(String name) {tName.add(name);}
    public void addNP(String nP) {nPartecipants.add(nP);}
    public void addNS(String nS) {nSubscribed.add(nS);}
    public void addDates(String date) {dates.add(date);}
    public void addSNO(String snoN) {sno.add(snoN);}

    public String getName(int i) {return tName.get(i);}
    public String getNP(int i) {return nPartecipants.get(i);}
    public String getNS(int i) {return nSubscribed.get(i);}
    public String getDate(int i) {return dates.get(i);}
    public String getSNO(int i) {return sno.get(i);}
}
