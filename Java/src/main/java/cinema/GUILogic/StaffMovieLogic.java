package cinema.GUILogic;

import cinema.Cinema;
import cinema.Movie;

import java.time.LocalDate;
import java.util.Arrays;

public class StaffMovieLogic {

    public static void updateMovie(Movie movie, String name, String director, String cast, String classification, String synopsis, LocalDate releaseDate) {
        movie.setName(name);
        movie.setSynopsis(synopsis);
        movie.setDirector(director);
        movie.setClassification(classification);
        String castString = cast.replace("[", "").replace("]", "");
        movie.setCast(Arrays.asList(castString.split(",")));
    }

    public static void addMovie(String name, String director, String cast, String classification, String synopsis, LocalDate releaseDate) {
        String castString = cast.replace("[", "").replace("]", "");
        Movie movie = new Movie(name, director, Arrays.asList(castString.split(",")), classification, synopsis, releaseDate);
        System.out.println(movie.getMovieID());
        Cinema.addMovie(movie);
    }

    public static void removeMovie(Movie movie) {
        Cinema.removeMovie(movie.getMovieID());
    }
}
