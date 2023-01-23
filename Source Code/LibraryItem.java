import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Base class representing a generic library item (non-specific type)
 * Implements comparable library item to enable sorting in Library class
 */

public abstract class LibraryItem implements Comparable<LibraryItem> {
    private String type;
    private String title;
    private int ID;
    private String year;
    private ArrayList<Double> ratings;
    private double averageRating;
    private int numberOfReviews;
    private String status;
    private Date dueDate;
    private int maxBorrowTime;



    public LibraryItem(String type, int ID, String title, String year, int maxBorrowTime){
        this.type = type;
        this.title = title;
        this.ID = ID;
        this.year = year;
        this.ratings = new ArrayList<Double>();
        this.averageRating =  0.0;
        this.numberOfReviews = 0;
        this.status = "available"; //always available when first created
        this.dueDate = null;
        this.maxBorrowTime = maxBorrowTime;

    }

    //return a String containing library items' information
    //boolean 'Initial' specifies type of information returned
    public String toString(boolean initial){
        if (initial){
            return String.format("ID: %s   Type: %s   Title: %s", this.ID, this.type, this.title);
        }
        else {
            return String.format("Average Rating: %f   Number of reviewers: %d   ID: %s   Type: %-7s   Title: %s",
                    this.averageRating, this.numberOfReviews, this.ID, this.type, this.title);
        }
    }

    //return all information about a generic selected library item
    public String selectedItemToString(){
        System.out.println("Selected item is:");
        StringBuilder str = new StringBuilder();
        str.append(String.format("Type: %s\n", this.type));
        str.append(String.format("Title: %s\n", this.title));
        str.append(String.format("ID: %d\n", this.ID));
        str.append(String.format("Year: %s\n", this.year));
        str.append(String.format("Average rating: %.1f\n", this.averageRating));
        str.append(String.format("Number of reviewers: %d\n", this.numberOfReviews));
        str.append(String.format("Status: %s\n", this.status));
        //no due date if library item is 'available' - generic to all types of library items
        if (this.dueDate != null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            str.append(String.format("Due date: %s\n", formatter.format(this.dueDate)));
        }
        return str.toString();
    }

    public int getID(){
        return this.ID;
    }

    public String getTitle(){
        return this.title;
    }

    public String getStatus (){
        return this.status;
    }

    public void setStatus(String newStatus){
        this.status = newStatus;
        //due date must be set when status changed
        this.dueDate = setDueDate();
    }

    public String getDueDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return String.format("This items due data is %s\n", formatter.format(this.dueDate));
    }

    //set due date based on items' max borrow time and current date
    public Date setDueDate(){
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, maxBorrowTime);
        return c.getTime();
    }

    //when item is returned
    public void resetDueDate(){
        this.dueDate = null;
    }

    //add a new rating and recalculate average rating
    public void addRating(double newRating){
        this.ratings.add(newRating);
        this.numberOfReviews++;
        this.averageRating = calculateAverageRating();
    }

    //calculate average rating based on number of reviews and each rating in ArrayList
    public double calculateAverageRating(){
        double total = 0;
        for (Double rating: ratings){
            total+=rating;
        }
        return total/numberOfReviews;
    }

    public double getAverageRating(){
        return this.averageRating;
    }

    //return hierarchy of library item when sorting by average rating then ID
    //implementation of Comparable<libraryItem>
    @Override
    public int compareTo(LibraryItem item){
        return Comparator.comparing(LibraryItem::getAverageRating).reversed()
                .thenComparing(LibraryItem::getID)
                .compare(this, item);
    }
}
