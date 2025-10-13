package academyawards.statistics;

import academyawards.data.Nomination;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilmStatistics implements Comparable<FilmStatistics>{
    List<Nomination> winnerNominations;
    private String film;
    private int year;

    public FilmStatistics(String film, int year) {
        this.film = film;
        this.year = year;
        winnerNominations = new ArrayList<>();
    }

    public void addWinnerNomination(Nomination nomination){
        if(this.film.equals(nomination.getFilm())
                && this.year == nomination.getYearFilm()
                && nomination.isWinner()){
            winnerNominations.add(nomination);
        }
    }

    public int getWinnerNominationCount(){
        return  this.winnerNominations.size();
    }

    @Override
    public int compareTo(FilmStatistics o) {
        int result = Integer.compare(o.getWinnerNominationCount(), this.getWinnerNominationCount());
        if(result==0){
            return Integer.compare(this.year, o.year);
        }
        return result;
    }

    @Override
    public String toString() {
        return  year + "," + film + "," + this.getWinnerNominationCount();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilmStatistics)) {
            return false;
        }

        FilmStatistics that = (FilmStatistics) o;

        if (year != that.year) {
            return false;
        }
        return Objects.equals(film, that.film);
    }

    @Override
    public int hashCode() {
        int result = film != null ? film.hashCode() : 0;
        result = 31 * result + year;
        return result;
    }
}
