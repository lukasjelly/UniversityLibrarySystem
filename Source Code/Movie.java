/**
 * a specific type of library item containing "movies"
 * targets base class LibraryItem
 */

public class Movie extends LibraryItem {
    private String  director;
    private static final int MAX_BORROW_TIME = 7;
    private static final String TYPE = "Movie";

    public Movie(int ID, String title, String year, String director){
        super(TYPE, ID, title, year, MAX_BORROW_TIME);
        this.director = director;
    }

    //return all information about a generic selected library item and additional information related to a "movie"
    @Override
    public String selectedItemToString(){
        StringBuilder str = new StringBuilder();
        str.append(super.selectedItemToString());
        str.append(String.format("Director: %s\n", this.director));
        return str.toString();
    }
}
