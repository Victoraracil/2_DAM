package academyawards;

import academyawards.data.FileHandler;
import academyawards.data.Nomination;
import academyawards.statistics.ActorStatistics;
import academyawards.statistics.FilmStatistics;
import academyawards.statistics.StatisticsUtilities;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Nomination> nominations = FileHandler.readNominationFile("the_oscar_award.tsv");

        // Exercise 1
        nominations.stream()
                .sorted(Comparator.comparing(Nomination::getName))
                .limit(10)
                .forEach(System.out::println);

        //Exercise 2
        printMostAwardedActorOrActress(nominations);

        //Exercise 3
        saveMostAwardedMovieOfAllTimeFile(nominations);

        //Exercise 4
        saveMostAwardedMovieByYearFile(nominations);

    }

    private static void printMostAwardedActorOrActress(List<Nomination> nominations) {
        String [] categoriesArray = {};
        Set<String> categories = new HashSet<>(List.of(categoriesArray));
        printMostAwardedActorOrActress(nominations);

        Map<String, Long> oscarsByName = nominations.stream()
                .filter(Nomination::isWinner)
                .filter(n -> categories.contains(n.getCategory()))
                .collect(Collectors.groupingBy(Nomination::getName,Collectors.counting()));

        oscarsByName.entrySet().stream().sorted(Comparator.comparing(Map.Entry :: getValue)).toList().getLast();
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




    private static void saveMostAwardedMovieOfAllTimeFile(List<Nomination> nominations) {
        List<FilmStatistics> filmStatisticsList = StatisticsUtilities.getFilmStatistics(nominations);
        Collections.sort(filmStatisticsList);
        FileHandler.writeFilmStatistics(filmStatisticsList, "most_awarded_movies_all_time.txt");
    }
}
