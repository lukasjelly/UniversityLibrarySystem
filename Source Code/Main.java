import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class used to drive library system.
 * Used for control flow of the program
 */

public class Main {
    //Global variable to ensure all methods have access to currently selected library item
    public static LibraryItem selectedItem = null;

    //read each library item from file, add to library object
    static void addLibraryItems(Library library, String fileName) {
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(fileName));

            try {
                String line;
                while ((line = inputStream.readLine()) != null) {
                    String str[] = line.split(",");
                    if (str[0].equals("Book")) {
                        library.addBook(str[0], Integer.parseInt(str[1]), str[2], str[3], str[4], Integer.parseInt(str[5]));
                    }
                    if (str[0].equals("Journal")) {
                        library.addJournal(str[0], Integer.parseInt(str[1]), str[2], str[3], str[4], Integer.parseInt(str[5]));
                    }
                    if (str[0].equals("Movie")) {
                        library.addMovie(str[0], Integer.parseInt(str[1]), str[2], str[3], str[4]);
                    }
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //display project/author information
    static void displayInfo() {
        System.out.println("------------------------------------");
        System.out.println("Assignment 2 Semester 1 2022");
        System.out.println("Submitted by: Jehle, Lukas 20009320");
        System.out.println("------------------------------------");
    }

    //display "main menu" options
    static void displayOptions() {
        System.out.println("Enter 'q' to quit,");
        System.out.println("or enter 's' to sort (first by average rating and then by id) and display all items,");
        System.out.println("or enter 'i' to search by ID,");
        System.out.println("or enter any other key to search by phrase in title");
    }

    //return input from keyboard
    static String readInput() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        return line;
    }

    //search library object for ID.
    //contains control flow code and reads user input
    static void searchId(Library library) {
        String input;

        //waits for method or code to return
        while(true) {
            System.out.println("Enter ID to start search, or enter b to go back to choose search method");
            input = readInput();
            if (input.equals("b")) {return;} //**Control flow** could return to main menu from here

            try{
                selectedItem = library.searchId(Integer.parseInt(input));
                if (selectedItem == null) {
                    String message = String.format("ID '%s' not found\n\n", input);
                    throw new IdException(message);
                }
            }
            catch (IdException e){
                e.printStackTrace();
                continue;
            }

            System.out.println(selectedItem.toString(true));
            System.out.println("Enter 'i' to search other item by ID, or enter other key to select this item");
            input = readInput();
            if (input.equals("i")) {
                continue;
            }
            else {
                System.out.println(selectedItem.selectedItemToString());
                if (selectedItem.getStatus() == "available"){borrowOrRate(library);}
                else {returnOrRate(library);}
                return; //**Control flow** could return to main menu from here
            }
        }

    }

    //search library object by title phrase.
    //contains control flow code and reads user input
    public static void searchByPhrase (Library library, String phrase){
        String input = phrase;
        ArrayList<LibraryItem> searchResults = null;

        //waits for method or code to return
        while (true){
            try{
                searchResults = library.searchPhrase(input);
                if (searchResults.isEmpty()){
                    throw new Exception();
                }
            }
            catch (Exception e){
                System.out.printf("Phrase '%s' not found\n\n", input);
                return; //**Control flow** could return to main menu here
            }

            //successfully found phrase in library item(s)
            //display them on console
            for (LibraryItem item: searchResults){
                System.out.printf("* %d:", searchResults.indexOf(item)+1 );
                System.out.println(item.toString(true));
            }

            System.out.println("Enter item number to select item, or enter any other key to continue searching");
            input = readInput();
            int index;
            try{
                index = Integer.parseInt(input)-1;
                searchResults.get(index);
            }
            catch (NumberFormatException e){
                System.out.println();
                return; //**Control flow** could return to main menu from here
            }
            catch (IndexOutOfBoundsException e){
                System.out.println();
                return; //**Control flow** could return to main menu from here
            }
            selectedItem = searchResults.get(index);
            System.out.println(selectedItem.selectedItemToString());
            if (selectedItem.getStatus() == "available"){borrowOrRate(library);}
            if (selectedItem.getStatus() == "on loan") {returnOrRate(library);}
            return; //**Control flow** could return to main menu from here
        }
    }

    //rate a library item.
    //uses global variable selectedItem which is loaded from searchID or searchPhrase method
    public static void rate(){
        System.out.println("Please enter your rating (0 - 10)");
        String input = readInput();
        selectedItem.addRating(Double.parseDouble(input));
        System.out.println(String.format("This items new average rating is %.1f\n", selectedItem.getAverageRating()));
        System.out.println(selectedItem.selectedItemToString());
    }

    //called when selectedItem is "available"
    public static void borrowOrRate(Library library){
        while (true){
            System.out.println("Enter 'b' to borrow the item, enter 'a' to rate the item, or enter any other key to restart");
            String input = readInput();
            if (input.equals("b")){
                selectedItem.setStatus("on loan");
                System.out.println(selectedItem.getDueDate());
                System.out.println(selectedItem.selectedItemToString());
                return;
            }
            else if (input.equals("a")){
                rate();
            }
            else{
                return;
            }
        }

    }

    //called when selectedItem is "on loan"
    static void returnOrRate(Library library){
        while (true){
            System.out.println("Enter 'r' to return the item, enter 'a' to rate the item, or enter any other key to restart");
            String input = readInput();
            if (input.equals("r")){
                selectedItem.setStatus("available");
                System.out.println("This item is returned\n");
                selectedItem.resetDueDate();
                return;
            }
            else if (input.equals("a")){
                rate();
            }
            else{
                return;
            }
        }
    }


    public static void main(String[] args) {

        //initialize library
        Library universityLibrary = new Library("University Library");
        addLibraryItems(universityLibrary, "library.txt");
        displayInfo();
        universityLibrary.outputAllItems(true);

        //loop exists when user enters 'q'
        while (true){
            displayOptions();
            String input = readInput();
            switch (input){
                case "q":
                    System.exit(0);
                case "s":
                    universityLibrary.sortRatingId();
                    universityLibrary.outputAllItems(false);
                    break;
                case "i":
                    searchId(universityLibrary);
                    break;
                default:
                    searchByPhrase(universityLibrary, input);
                    break;
            }
            //clean up
            selectedItem = null;
        }
    }
}
