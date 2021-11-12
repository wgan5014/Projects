package cinema.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import cinema.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieTest {
    //String name,String director, List<cast>.String classification,String synopsis,int movieID
    List<String> cast1 = new ArrayList<>();
    Movie m1 = new Movie(1,"movie1","a",cast1,"R","hi", LocalDate.now());

    @Test
    void testGetName(){
        assertEquals("movie1",m1.getName());
    }

    @Test
    void testSetName(){
        m1.setName("moviex");
        assertEquals("moviex",m1.getName());
    }

    @Test
    void testGetID(){
        assertEquals(1,m1.getMovieID());
    }

    @Test
    void testGetDirector(){
        assertEquals("a",m1.getDirector());
    }

    @Test
    void testSetDirector(){
        m1.setDirector("b");
        assertEquals("b",m1.getDirector());
    }

    @Test
    void testGetCast(){
        List<String> actors = new ArrayList<>();
        actors.add("a");
        m1.addActor("a");
        assertEquals(actors,m1.getCast());
    }

    @Test
    void testRemoveActor(){
        List<String> actors = new ArrayList<>();
        m1.removeActor("a");
        assertEquals(actors,m1.getCast());
    }

    @Test
    void testAddActor(){
        List<String> actors = new ArrayList<>();
        actors.add("a");
        m1.addActor("a");
        assertEquals(actors,m1.getCast());
    }

    @Test
    void testGetClassification(){
        assertEquals("R",m1.getClassification());
    }

    @Test
    void testSetClassification(){
        m1.setClassification("G");
        assertEquals("G",m1.getClassification());
    }

    @Test
    void testGetSynopsis(){
        assertEquals("hi",m1.getSynopsis());
    }

    @Test
    void testSetSynopsis(){
        m1.setSynopsis("hello");
        assertEquals("hello",m1.getSynopsis());
    }

    @Test
    void testReleaseDate(){
        LocalDate currentDate = LocalDate.now();
        LocalDate newDate = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth());
        m1.setReleaseDate(newDate);
        assertEquals(newDate, m1.getReleaseDate());
    }

}
