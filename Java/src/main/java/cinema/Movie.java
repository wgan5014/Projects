package cinema;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;

public class Movie {
    private String name;
    private String director;
    private List<String> cast;
    private String classification;
    private String synopsis;
    private int movieID;
    private LocalDate releaseDate;
    private int runtime;


    public Movie(String name, String director, List<String> cast, String classification, String synopsis, LocalDate releaseDate){
        this.movieID = (int) Math.round(Math.random() * 1000000);
        this.name = name;
        this.director = director;
        this.cast = cast;
        this.classification = classification;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
    }

    public Movie(int movieID,String name, String director, List<String> cast, String classification, String synopsis, LocalDate releaseDate){
        this.movieID = movieID;
        this.name = name;
        this.director = director;
        this.cast = cast;
        this.classification = classification;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
    }

    public String getName(){
        return name;
    }
    public String getDirector(){
        return director;
    }
    public String getSynopsis(){
        return synopsis;
    }
    public String getClassification(){
        return classification;
    }
    public LocalDate getReleaseDate() {return releaseDate;}
  
    public List<String> getCast(){
        return cast;
    }
  
    public void addActor(String x){
        cast.add(x);
    }
    public void removeActor(String x){
        cast.remove(x);
    }


    public void setName(String x){
        this.name = x;
    }
    public void setClassification(String x){
        this.classification = x;
    }
    public void setDirector(String x){
        this.director = x;
    }
    public void setSynopsis(String x){
        this.synopsis = x;
    }
    public void setReleaseDate(LocalDate newDate){ this.releaseDate = newDate;}

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public int getMovieID() {
        return movieID;
    }

    
    public LocalDate releaseDate() {
    	return releaseDate;
    }

    public int getRunTime() {
    	return runtime;
    }
    
    public void setRunTime(int rt) {
    	runtime = rt;
    }
}

