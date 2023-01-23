import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library testLibrary;

    //create test library
    @BeforeEach
    public void setupLibrary(){
        testLibrary = new Library("test");
        testLibrary.addBook("book", 20212,"How To Pass University", "1991", "John Smith", 200);
        testLibrary.addBook("book", 51672,"Student Finance For University Students", "2011", "Liz Bool", 123);
    }

    //test ID search method
    @Test
    void searchId() {
        assertEquals(20212, testLibrary.searchId(20212).getID());
        assertEquals(51672, testLibrary.searchId(51672).getID());
    }

    //test multiple search results from phrase search
    @Test
    void searchPhrase1() {
        ArrayList<LibraryItem> searchResults = testLibrary.searchPhrase("University");
        assertEquals("How To Pass University", searchResults.get(0).getTitle());
        assertEquals("Student Finance For University Students", searchResults.get(1).getTitle());
    }

    //test single search result from phrase search
    @Test
    void searchPhrase2() {
        ArrayList<LibraryItem> searchResults = testLibrary.searchPhrase("How");
        assertEquals("How To Pass University", searchResults.get(0).getTitle());
    }

    //test zero results from phrase search
    @Test
    void searchPhrase3() {
        ArrayList<LibraryItem> searchResults = testLibrary.searchPhrase("Teacher");
        assertTrue(searchResults.isEmpty());
    }
}