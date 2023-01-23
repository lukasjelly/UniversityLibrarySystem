/**
 * a specific type of library item containing "journals"
 * targets base class LibraryItem
 */

public class Journal extends LibraryItem {
    private String  volume;
    private int number;
    private static final int MAX_BORROW_TIME = 14;
    private static final String TYPE = "Journal";

    public Journal(int ID, String title, String year, String volume, int number){
        super(TYPE, ID, title, year, MAX_BORROW_TIME);
        this.volume = volume;
        this.number = number;
    }

    //return all information about a generic selected library item and additional information related to a "journal"
    @Override
    public String selectedItemToString(){
        StringBuilder str = new StringBuilder();
        str.append(super.selectedItemToString());
        str.append(String.format("Volume: %s\n", this.volume));
        str.append(String.format("Number: %d\n", this.number));
        return str.toString();
    }
}
