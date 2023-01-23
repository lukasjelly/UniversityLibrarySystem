import java.util.ArrayList;
import java.util.Collections;

/**
 * Class representing a library
 * Contains library Items of different types
 */

public class Library {
    private ArrayList<LibraryItem> libraryItems = new ArrayList<LibraryItem>();
    private String name;

    public Library(String newLibraryName){
        this.name = newLibraryName;
    }

    //unique method for each type of library item
    public void addBook(String itemType, int ID, String title, String year, String author, int numberOfPages){
        LibraryItem newBook = new Book(ID, title, year, author, numberOfPages);
        libraryItems.add(newBook);
    }

    //unique method for each type of library item
    public void addJournal(String itemType, int ID, String title, String year, String volume, int number){
        LibraryItem newJournal = new Journal(ID, title, year, volume, number);
        libraryItems.add(newJournal);
    }

    //unique method for each type of library item
    public void addMovie(String itemType, int ID, String title, String year, String director){
        LibraryItem newMovie = new Movie(ID, title, year, director);
        libraryItems.add(newMovie);
    }

    //display all items in library.
    //initial indicates the type of information outputted for each library item
    public void outputAllItems(boolean initial){
        System.out.println("List of all items in the library:");
        for (LibraryItem item: libraryItems){
            System.out.println(item.toString(initial));
        }
        System.out.println();
    }

    //sort library by rating then ID
    public void sortRatingId(){
        Collections.sort(libraryItems);
    }

    //return library item found by ID search
    public LibraryItem searchId(int id){
        for (LibraryItem item: libraryItems){
            if (item.getID() == id){
                return item;
            }
        }
        return null;
    }

    //return ArrayList of all library items found by search phrase
    public ArrayList<LibraryItem> searchPhrase(String phrase){
        ArrayList<LibraryItem> searchResults = new ArrayList<LibraryItem>();
        for (LibraryItem item: libraryItems){
            if (item.getTitle().contains(phrase)){
                searchResults.add(item);
            }
        }
        return searchResults;
    }
}
