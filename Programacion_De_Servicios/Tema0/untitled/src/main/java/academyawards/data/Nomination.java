package academyawards.data;

public class Nomination implements Comparable<Nomination>{
    private int yearFilm;
    private int yearCeremony;
    private int ceremony;
    private String category;
    private String name;
    private String film;
    private boolean winner;

    public Nomination(int yearFilm, int yearCeremony, int ceremony, String category, String name, String film, boolean winner) {
        this.yearFilm = yearFilm;
        this.yearCeremony = yearCeremony;
        this.ceremony = ceremony;
        this.category = category;
        this.name = name;
        this.film = film;
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Nomination{" +
                "yearFilm=" + yearFilm +
                ", yearCeremony=" + yearCeremony +
                ", ceremony=" + ceremony +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", film='" + film + '\'' +
                ", winner=" + winner +
                '}';
    }

    @Override
    public int compareTo(Nomination o) {
        return this.name.compareTo(o.name);
    }

    public String getCategory() {
        return category;
    }

    public int getYearFilm() {
        return yearFilm;
    }

    public void setYearFilm(int yearFilm) {
        this.yearFilm = yearFilm;
    }

    public int getYearCeremony() {
        return yearCeremony;
    }

    public void setYearCeremony(int yearCeremony) {
        this.yearCeremony = yearCeremony;
    }

    public int getCeremony() {
        return ceremony;
    }

    public void setCeremony(int ceremony) {
        this.ceremony = ceremony;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
