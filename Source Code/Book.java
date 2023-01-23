/**
 * a specific type of library item containing "books"
 * targets base class LibraryItem
 */

public class Book extends LibraryItem {
    private int numberOfPages;
    private String author;
    private static final int MAX_BORROW_TIME = 28;
    private static final String TYPE = "Book";

    public Book(int ID, String title, String year,  String author, int numberOfPages){
        super(TYPE, ID, title, year, MAX_BORROW_TIME);
        this.numberOfPages = numberOfPages;
        this.author = author;
    }

    //return all information about a generic selected library item and additional information related to a "book"
    @Override
    public String selectedItemToString(){
        StringBuilder str = new StringBuilder();
        str.append(super.selectedItemToString());
        str.append(String.format("Author: %s\n", this.author));
        str.append(String.format("Number of pages: %d\n", this.numberOfPages));
        return str.toString();
    }
}
