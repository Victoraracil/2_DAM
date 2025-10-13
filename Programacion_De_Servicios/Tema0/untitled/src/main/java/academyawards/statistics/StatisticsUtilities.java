package academyawards.statistics;

import academyawards.data.Nomination;
import academyawards.statistics.ActorStatistics;

import java.util.*;

public class StatisticsUtilities {



    public static List<ActorStatistics> getActorStatistics(List<Nomination> nominations){

        Map<String, ActorStatistics> actorStatisticsMap = new Hashtable<>();

        for (Nomination nomination: nominations
             ) {
            switch (nomination.getCategory()){
                case "ACTOR IN A LEADING ROLE":
                case "ACTOR IN A SUPPORTING ROLE":
                case "ACTRESS IN A LEADING ROLE":
                case "ACTRESS IN A SUPPORTING ROLE":
                    addActorNomination(nomination, actorStatisticsMap);
                    break;
                default:
            }
        }

        return new ArrayList<>(actorStatisticsMap.values());

    }

    private static void addActorNomination(Nomination nomination, Map<String, ActorStatistics> actorStatisticsMap) {
        String name = nomination.getName();

        ActorStatistics actorStatistics = actorStatisticsMap.get(name);

        if(actorStatistics == null){
            actorStatistics = new ActorStatistics(name);
            actorStatisticsMap.put(name, actorStatistics);
        }
        if(nomination.isWinner()) {
            actorStatistics.addWinnerNomination(nomination);
        }
    }


    public static List<FilmStatistics> getFilmStatistics(List<Nomination> nominations){

        Map<FilmStatistics, FilmStatistics> filmStatisticsMap = new Hashtable<>();

        for (Nomination nomination:nominations) {

            if(!nomination.getFilm().isEmpty()) {

                FilmStatistics filmStatistics = new FilmStatistics(nomination.getFilm(), nomination.getYearFilm());

                if (filmStatisticsMap.containsKey(filmStatistics)) {
                    filmStatistics = filmStatisticsMap.get(filmStatistics);
                } else {
                    filmStatisticsMap.put(filmStatistics, filmStatistics);
                }

                if (nomination.isWinner()) {
                    filmStatistics.addWinnerNomination(nomination);
                }
            }

        }

        List<FilmStatistics> sortedStatistics = new ArrayList<>(filmStatisticsMap.values());
        Collections.sort(sortedStatistics);

        return sortedStatistics;

    }

    public static Map<Integer, List<FilmStatistics>> getFilmStatisticsByYear(List<Nomination> nominations) {

        Map<Integer, List<Nomination> > filmNominationsByYear = new Hashtable<>();

        for (Nomination nomination:nominations) {
            List<Nomination> nominationsByYear = filmNominationsByYear.get(nomination.getYearCeremony());
            if(nominationsByYear == null){
                nominationsByYear = new ArrayList<>();
                filmNominationsByYear.put(nomination.getYearCeremony(), nominationsByYear);
            }
            nominationsByYear.add(nomination);
        }

        Map<Integer, List<FilmStatistics>> filmStatisticsByYear = new HashMap<>();
        for(List<Nomination> nominationsByYear: filmNominationsByYear.values()){
            filmStatisticsByYear.put(nominationsByYear.get(0).getYearCeremony(), getFilmStatistics(nominationsByYear));
        }
        return filmStatisticsByYear;

    }
}
