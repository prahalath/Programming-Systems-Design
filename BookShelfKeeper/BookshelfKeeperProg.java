// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CSCI455 PA2
// Fall 2020

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main program that prompts for input from user.
 * Calls the corresponding methods in Bookshelf and BookshelfKeeper class to maintain books.
 * Allows only valid operations to be performed on the Bookshelf.
 */
public class BookshelfKeeperProg {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Integer> tempList;
        System.out.println("Please enter initial arrangement of books followed by newline:");
        tempList = getInitialBooks(in);
        if (!isValidBookshelf(tempList)){
            return;
        }
        Bookshelf bookPile = new Bookshelf(tempList);
        BookshelfKeeper bookKeeper = new BookshelfKeeper(bookPile);
        System.out.println(bookKeeper.toString());
        System.out.println("Type pick <index> or put <height> followed by newline. Type end to exit.");
        getOperations(in, bookKeeper);
        System.out.println("Exiting Program.");
    }

    /**
     * Static method to convert list of book heights to integers.
     * @param in - the scanner object to receive input from console.
     * @return - ArrayList of Integers (book heights).
     */
    private static ArrayList<Integer> getInitialBooks(Scanner in){
        String inputLine = in.nextLine();
        Scanner stringScanner = new Scanner(inputLine);
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        while (stringScanner.hasNextInt()){
            tempList.add(stringScanner.nextInt());
        }
        return tempList;
    }

    /**
     * Helper method to process and check operations pick, put, and end.
     * @param in the scanner object to receive input from console.
     * @param bookKeeper the BookshelfKeeper object upon which the above operations are performed.
     */
    private static void getOperations(Scanner in, BookshelfKeeper bookKeeper){
        String operation;
        Scanner stringScanner;
        while (true) {
            operation = in.nextLine();
            stringScanner = new Scanner(operation);
            operation = stringScanner.next();
            if (operation.equals("end")  || !(isValidOperation(operation) )){
                return;
            }
            int num = stringScanner.nextInt();
            if (operation.equals("put")) {
                if (!isValidHeight(num)){
                    return;
                }
                bookKeeper.putHeight(num);
            } else if (operation.equals("pick")) {
                if (!isValidPick(num, bookKeeper)){
                    return;
                }
                bookKeeper.pickPos(num);
            }
            System.out.println(bookKeeper.toString());
        }
    }

    /**
     * Helper method to check validity of initial configuration of books entered.
     * @param tempList the books entered.
     * @return true if the configuration is valid else false.
     */
    private static boolean isValidBookshelf(ArrayList<Integer> tempList) {

        for (int i = 0; i < tempList.size(); i++){
            if (tempList.get(i) <= 0){
                System.out.println("ERROR: Height of a book must be positive.");
                System.out.println("Exiting Program.");
                return false;
            }
        }
        for (int i = 0; i < tempList.size() - 1; i++) {
            if (tempList.get(i + 1) < tempList.get(i)){
                System.out.println("ERROR: Heights must be specified in non-decreasing order.");
                System.out.println("Exiting Program.");
                return false;
            }
        }
        return true;

    }

    /**
     * Checks if height entered is valid for put operation.
     * @param height book height entered by user.
     * @return true if height is valid else false.
     */
    private static boolean isValidHeight(int height){
        if (height <= 0){
            System.out.println("ERROR: Height of a book must be positive.");
            return false;
        }
        return true;
    }

    /**
     * Checks if position entered is valid for pick operation
     * @param position the position entered by user.
     * @param bookKeeper the object upon which the pick validity is checked.
     * @return true if pick is valid else false.
     */
    private static boolean isValidPick(int position, BookshelfKeeper bookKeeper){
        if (position < 0 || position >= bookKeeper.getNumBooks() ){
            System.out.println("ERROR: Entered pick operation is invalid on this shelf.");
            return false;
        }
        return true;
    }

    /**
     * Helper method to check if the entered operation is valid i.e, one of pick, put, end.
     * @param operation operation entered by user.
     * @return true if operation is valid else false.
     */
    private static boolean isValidOperation(String operation){
        if (!operation.equals("pick") && !operation.equals("put") && !operation.equals("end")){
            System.out.println("ERROR: Operation should be either pick or put.");
            return false;
        }
        return true;
    }
}