package bean;

import java.io.Serializable;
import java.time.LocalDate;

public class BeanTournCreation implements Serializable {
    private String name;
    private int maxPlayers;
    private LocalDate date;
    private String organizer;

    public BeanTournCreation() {
        //Constructor
    }

    public BeanTournCreation(String name, int maxPlayers, LocalDate date, String organizer) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.date = date;
        this.organizer = organizer;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getMaxPlayers() { return maxPlayers; }

    public void setMaxPlayers(int maxPlayers) { this.maxPlayers = maxPlayers; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public String getOrganizer() {  return organizer; }

    public void setOrganizer(String organizer) { this.organizer = organizer; }

    @Override
    public String toString() {
        return "BeanTournCreation{" +
                "name='" + name + '\'' +
                ", maxPlayers=" + maxPlayers +
                ", date=" + date +
                ", organizer='" + organizer + '\'' +
                '}';
    }
}
