package video;

import member.Account;

public class Movie {
    private static Movie movie = null;

    //    싱글톤
    public static Movie getInstance() {
        if (movie == null) {
            movie =  new Movie();
            return movie;
        }
        return movie;
    }

    private String title;
    String type;
    int runtime;
    String start_year;
    int genre;
    double rating;
    String viewing_class;
    String account_id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStart_year() {
        return start_year;
    }

    public void setStart_year(String start_year) {
        this.start_year = start_year;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getViewing_class() {
        return viewing_class;
    }

    public void setViewing_class(String viewing_class) {
        this.viewing_class = viewing_class;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }
}
