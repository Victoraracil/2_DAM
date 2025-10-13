package academyawards;

import academyawards.data.FileHandler;
import academyawards.data.Nomination;
import academyawards.statistics.ActorStatistics;
import academyawards.statistics.FilmStatistics;
import academyawards.statistics.StatisticsUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        List<Nomination> nominations = FileHandler.readNominationFile("the_oscar_award.tsv");

        // Exercise 1
        printFirst10SortedNominations(nominations);

        //Exercise 2
        printMostAwardedActorOrActress(nominations);

        //Exercise 3
        saveMostAwardedMovieOfAllTimeFile(nominations);

        //Exercise 4
        saveMostAwardedMovieByYearFile(nominations);

    }

    private static void saveMostAwardedMovieByYearFile(List<Nomination> nominations) {

        Map< Integer, List<FilmStatistics> > moviesStatisticsByYear = StatisticsUtilities.getFilmStatisticsByYear(nominations);

        List<Integer> years = new ArrayList<>( moviesStatisticsByYear.keySet()) ;
        Collections.sort(years);

        List<FilmStatistics> mostAwardedFilmByYear = new ArrayList<>();
        for(int year: years) {
            mostAwardedFilmByYear.add(moviesStatisticsByYear.get(year).get(0));
        }

        FileHandler.writeFilmStatistics(mostAwardedFilmByYear, "most_awarded_movies_by_year.txt");

    }





    private static void printFirst10SortedNominations(List<Nomination> nominations) {
        Collections.sort(nominations);
        for(int i=0; i<10; i++){
            System.out.println((i+1)+" - "+ nominations.get(i));
        }
    }

    private static void printMostAwardedActorOrActress(List<Nomination> nominations) {
        List<ActorStatistics> actorStatisticsList = StatisticsUtilities.getActorStatistics(nominations);
        Collections.sort(actorStatisticsList);
        ActorStatistics mostAwarded = actorStatisticsList.get(0);
        System.out.println("The most awarded actor or actress is: "+ mostAwarded.getName() + "with :"+mostAwarded.getWinnerNominationCount());
    }

    private static void saveMostAwardedMovieOfAllTimeFile(List<Nomination> nominations) {
        List<FilmStatistics> filmStatisticsList = StatisticsUtilities.getFilmStatistics(nominations);
        Collections.sort(filmStatisticsList);
        FileHandler.writeFilmStatistics(filmStatisticsList, "most_awarded_movies_all_time.txt");
    }
}
