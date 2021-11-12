package cinema;

import static org.junit.jupiter.api.Assertions.*;

import cinema.IO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import cinema.Cinema;
import cinema.Movie;
import cinema.GUILogic.StaffLoginScreenLogic;
import cinema.GUILogic.StaffMovieLogic;

public class StaffTest {

    @BeforeEach
    void loadData() {
        //IO.deleteFiles();
        IO.load();
    }
    
    @Test
    void testCredentialChecker() {
        assertTrue(StaffLoginScreenLogic.checkCredentials("melissa", "melissa"));
        assertTrue(StaffLoginScreenLogic.checkCredentials("nafi", "nafi")); 
        assertTrue(StaffLoginScreenLogic.checkCredentials("william", "william")); 
        assertTrue(StaffLoginScreenLogic.checkCredentials("ishan", "ishan")); 
        assertTrue(StaffLoginScreenLogic.checkCredentials("ian", "ian"));
        assertFalse(StaffLoginScreenLogic.checkCredentials("test", "test")); 
    }

    @Test
    void testAddMovie() {
        //StaffMovieLogic.addMovie("test", "test", "test", "test", "test");
    }

    @Test
    void testUpdateMovie() {
        //Movie movie = new Movie("test", "test", null, "test", "test", null);
        //StaffMovieLogic.updateMovie(movie, "tester", "tester", null, "tester", "tester");
        //assertEquals(movie.getName(), "tester");
    }

    @Test
    void testRemoveMovie() {
        //Movie movie = new Movie("test", "test", null, "test", "test", null);
        //assertDoesNotThrow(() -> StaffMovieLogic.removeMovie(movie));
    }
}
