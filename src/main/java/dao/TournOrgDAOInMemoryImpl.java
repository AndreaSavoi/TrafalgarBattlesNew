package dao;

import bean.BeanTournCreation;
import java.util.Map;

public class TournOrgDAOInMemoryImpl implements TournOrgDAO {

    private final Map<String, TournInfoDAOInMemoryImpl.TournamentDetails> tournaments;
    private final java.util.concurrent.atomic.AtomicInteger nextSno;

    public TournOrgDAOInMemoryImpl() {
        this.tournaments = TournInfoDAOInMemoryImpl.getTournamentsMap();
        this.nextSno = TournInfoDAOInMemoryImpl.getNextSno();
    }

    @Override
    public void addTourn(BeanTournCreation bean) {
        if (tournaments.containsKey(bean.getName())) {
            throw new IllegalArgumentException("Tournament with this name already exists.");
        }
        tournaments.put(bean.getName(), new TournInfoDAOInMemoryImpl.TournamentDetails(
                nextSno.getAndIncrement(),
                bean.getName(),
                bean.getDate().toString(),
                bean.getMaxPlayers(),
                0,
                bean.getOrganizer()
        ));
    }

    @Override
    public void modifyTourn(BeanTournCreation bean) {
        TournInfoDAOInMemoryImpl.TournamentDetails tourn = tournaments.get(bean.getName());
        if (tourn == null) {
            throw new IllegalArgumentException("Tournament not found for modification.");
        }
        tourn.setDates(bean.getDate().toString());
        tourn.setMaxParticipants(bean.getMaxPlayers());
    }

    @Override
    public void deleteTourn(String name) {
        if (!tournaments.containsKey(name)) {
            throw new IllegalArgumentException("Tournament not found for deletion.");
        }
        tournaments.remove(name);
    }
}