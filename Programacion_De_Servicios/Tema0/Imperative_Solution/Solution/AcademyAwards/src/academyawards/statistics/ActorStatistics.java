package academyawards.statistics;

import academyawards.data.Nomination;

import java.util.ArrayList;
import java.util.List;

public class ActorStatistics implements Comparable<ActorStatistics>{
    String name;
    List<Nomination> winnerNomitations;

    public ActorStatistics(String name) {
        this.name = name;
        winnerNomitations = new ArrayList<>();
    }

    public void addWinnerNomination(Nomination nomination){
        if(nomination.isWinner()) {
            winnerNomitations.add(nomination);
        }
    }

    public int getWinnerNominationCount(){
        return winnerNomitations.size();
    }

    @Override
    public int compareTo(ActorStatistics o) {
        return Integer.compare(o.getWinnerNominationCount(), this.getWinnerNominationCount());
    }

    public String getName() {
        return name;
    }
}
