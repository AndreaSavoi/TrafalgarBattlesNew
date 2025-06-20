package dao;

import exception.AlreadySubscribedException;
import exception.MaxParticipantsReachedException;
import exception.UserNotSubscribedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TournInfoDAOInMemoryImpl implements TournInfoDAO {

    public static class TournamentDetails {
        int sno;
        String tName;
        String dates;
        int maxParticipants;
        int currentSubscribed;
        String organizer;

        public TournamentDetails(int sno, String tName, String dates, int maxParticipants, int currentSubscribed, String organizer) {
            this.sno = sno;
            this.tName = tName;
            this.dates = dates;
            this.maxParticipants = maxParticipants;
            this.currentSubscribed = currentSubscribed;
            this.organizer = organizer;
        }

        public int getSno() { return sno; }
        public String gettName() { return tName; }
        public String getDates() { return dates; }
        public int getMaxParticipants() { return maxParticipants; }
        public int getCurrentSubscribed() { return currentSubscribed; }
        public String getOrganizer() { return organizer; }

        public void setDates(String dates) { this.dates = dates; }
        public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }
        public void setCurrentSubscribed(int currentSubscribed) { this.currentSubscribed = currentSubscribed; }
    }

    private static final Map<String, TournamentDetails> tournaments = new HashMap<>();
    private static final Map<String, List<String>> subscriptions = new HashMap<>();
    private static final AtomicInteger nextSno = new AtomicInteger(1);

    public TournInfoDAOInMemoryImpl() {
        if (tournaments.isEmpty()) {
            // Add some fake data for testing
            tournaments.put("DummyTourn1", new TournamentDetails(nextSno.getAndIncrement(), "DummyTourn1", "2025-07-10", 10, 0, "testorganizer"));
            tournaments.put("DummyTourn2", new TournamentDetails(nextSno.getAndIncrement(), "DummyTourn2", "2025-08-15", 5, 2, "testorganizer"));
            tournaments.put("PublicTourn", new TournamentDetails(nextSno.getAndIncrement(), "PublicTourn", "2025-09-01", 20, 5, "anotherorg"));

            // Add some fake subscriptions
            subscriptions.computeIfAbsent("testplayer", k -> new ArrayList<>()).add("DummyTourn2");
        }
    }

    public static Map<String, TournamentDetails> getTournamentsMap() {
        return tournaments;
    }

    public static AtomicInteger getNextSno() {
        return nextSno;
    }

    @Override
    public void getAllInfo(List<String> tNameList, List<String> nPartecipantsList, List<String> nSubscribedList, List<String> datesList, List<String> snoList, String mode, String username) {
        tNameList.clear();
        nPartecipantsList.clear();
        nSubscribedList.clear();
        datesList.clear();
        snoList.clear();

        List<TournamentDetails> filteredTournaments = new ArrayList<>();

        for (TournamentDetails tourn : tournaments.values()) {
            boolean add = false;
            if ("all".equals(mode)) {
                add = true;
            } else if ("sub".equals(mode) && username != null && subscriptions.containsKey(username) && subscriptions.get(username).contains(tourn.tName)) {
                add = true;
            } else if ("org".equals(mode) && tourn.organizer.equals(username)) {
                add = true;
            }
            if (add) {
                filteredTournaments.add(tourn);
            }
        }

        filteredTournaments.sort((t1, t2) -> Integer.compare(t1.sno, t2.sno));

        for (TournamentDetails tourn : filteredTournaments) {
            tNameList.add(tourn.tName);
            nPartecipantsList.add(String.valueOf(tourn.maxParticipants));
            nSubscribedList.add(String.valueOf(tourn.currentSubscribed));
            datesList.add(tourn.dates);
            snoList.add(String.valueOf(tourn.sno));
        }
    }

    @Override
    public void getSpecific(List<String> curr, int sno) {
        curr.clear();
        for (TournamentDetails tourn : tournaments.values()) {
            if (tourn.sno == sno) {
                curr.add(tourn.tName);
                curr.add(String.valueOf(tourn.maxParticipants));
                curr.add(String.valueOf(tourn.currentSubscribed));
                curr.add(tourn.dates);
                return;
            }
        }
        // If not found, curr remains empty. Application controllers should handle this.
    }

    @Override
    public void addSub(String username, String tName) throws AlreadySubscribedException, MaxParticipantsReachedException {
        TournamentDetails tourn = tournaments.get(tName);
        if (tourn == null) {
            throw new IllegalArgumentException("Tournament '" + tName + "' not found.");
        }

        List<String> userSubs = subscriptions.computeIfAbsent(username, k -> new ArrayList<>());

        if (userSubs.contains(tName)) {
            throw new AlreadySubscribedException("You're already subscribed to this tournament.");
        }

        if (tourn.currentSubscribed >= tourn.maxParticipants) {
            throw new MaxParticipantsReachedException("Max participants reached for this tournament.");
        }

        userSubs.add(tName);
        tourn.currentSubscribed++;
    }

    @Override
    public void removeSub(String username, String tName) throws UserNotSubscribedException {
        List<String> userSubs = subscriptions.get(username);
        if (userSubs == null || !userSubs.contains(tName)) {
            throw new UserNotSubscribedException("You're not subscribed to this tournament.");
        }

        userSubs.remove(tName);
        TournamentDetails tourn = tournaments.get(tName);
        if (tourn != null) {
            tourn.currentSubscribed--;
        }
    }
}