// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CSCI455 PA2
// Fall 2020


/**
 * Class BookshelfKeeper 
 *
 * Enables users to perform efficient putPos or pickHeight operation on a bookshelf of books kept in 
 * non-decreasing order by height, with the restriction that single books can only be added 
 * or removed from one of the two *ends* of the bookshelf to complete a higher level pick or put 
 * operation.  Pick or put operations are performed with minimum number of such adds or removes.
 */
public class  BookshelfKeeper {

    /**
      Representation invariant:

      The books in a BookshelfKeeper object are always stored in non-decreasing order.

   */
   
   Bookshelf sortedBookPile;
   Bookshelf tempBookPile;
   private int totalNumCalls;
   private int lastNumCalls;


   /**
    * Creates a BookShelfKeeper object with an empty bookshelf
    */
   public BookshelfKeeper() {
      sortedBookPile = new Bookshelf();
      tempBookPile = new Bookshelf();
      totalNumCalls = 0;
      lastNumCalls = 0;
      assert isValidBookshelfKeeper();
   }

   /**
    * Creates a BookshelfKeeper object initialized with the given sorted bookshelf.
    * Note: method does not make a defensive copy of the bookshelf.
    *
    * PRE: sortedBookshelf.isSorted() is true.
    */
   public BookshelfKeeper(Bookshelf sortedBookshelf) {
      assert sortedBookshelf.isSorted();

      sortedBookPile = sortedBookshelf;
      tempBookPile = new Bookshelf();
      totalNumCalls = 0;
      lastNumCalls = 0;
      assert isValidBookshelfKeeper();

   }

   /**
    * Removes a book from the specified position in the bookshelf and keeps bookshelf sorted 
    * after picking up the book.
    * 
    * Returns the number of calls to mutators on the contained bookshelf used to complete this
    * operation. This must be the minimum number to complete the operation.
    * 
    * PRE: position must be in the range [0, getNumBooks()).
    */
   public int pickPos(int position) {
      int numCalls = 0;
      if (position < sortedBookPile.size() / 2) { // comparing with the middle element to determine minimum number of operations.
         numCalls = removeBookFromLeft(position);
         sortedBookPile.removeFront();
         numCalls += addElemFromLeft();
      }
      else {
         numCalls = removeBookFromRight(position);
         sortedBookPile.removeLast();
         numCalls += addElemFromRight();
      }
      numCalls += 1;
      totalNumCalls += numCalls;
      lastNumCalls = numCalls;
      assert isValidBookshelfKeeper();
      return numCalls;
   }


   /**
    * Inserts book with specified height into the shelf.  Keeps the contained bookshelf sorted 
    * after the insertion.
    * 
    * Returns the number of calls to mutators on the contained bookshelf used to complete this
    * operation. This must be the minimum number to complete the operation.
    * 
    * PRE: height > 0
    */
   public int putHeight(int height) {
      int numCalls = 0;
      int position = sortedBookPile.size() / 2; // comparing with the middle element to determine minimum number of operations.
      if (sortedBookPile.size() == 0){
         sortedBookPile.addFront(height);
      }
      else if (height < sortedBookPile.getHeight(position)) {
         while (sortedBookPile.getHeight(0) < height) {
            numCalls += 1;
            int element = sortedBookPile.removeFront();
            tempBookPile.addLast(element);
         }
         sortedBookPile.addFront(height);
         numCalls += addElemFromLeft();
      }
      else {
         while (sortedBookPile.getHeight(sortedBookPile.size()-1) > height) {
            numCalls += 1;
            int element = sortedBookPile.removeLast();
            tempBookPile.addFront(element);
         }
         sortedBookPile.addLast(height);
         numCalls += addElemFromRight();
      }
      numCalls += 1;
      totalNumCalls += numCalls;
      lastNumCalls = numCalls;
      assert isValidBookshelfKeeper();
      return numCalls;
   }


   /**
    * Returns the total number of calls made to mutators on the contained bookshelf
    * so far, i.e., all the ones done to perform all of the pick and put operations
    * that have been requested up to now.
    */
   public int getTotalOperations() {
      
       return totalNumCalls;
   }

   /**
    * Returns the number of books on the contained bookshelf.
    */
   public int getNumBooks() {
      
       return sortedBookPile.size();
   }

   /**
    * Returns string representation of this BookshelfKeeper. Returns a String containing height
    * of all books present in the bookshelf in the order they are on the bookshelf, followed 
    * by the number of bookshelf mutator calls made to perform the last pick or put operation, 
    * followed by the total number of such calls made since we created this BookshelfKeeper.
    * 
    * Example return string showing required format: “[1, 3, 5, 7, 33] 4 10”
    * 
    */
   public String toString() {
      
      return sortedBookPile.toString() + " " + lastNumCalls + " " + totalNumCalls;
      
   }

   /**
    * Returns true iff the BookshelfKeeper data is in a valid state.
    * (See representation invariant comment for details.)
    */
   private boolean isValidBookshelfKeeper() {

      return sortedBookPile.isSorted();

   }

   /**
    * Helper method to remove element from left of the bookshelf and storing it in a temporary bookshelf.
    * @param pos position until which books are removed.
    * @return the number of calls to mutator functions.
    */
   private int removeBookFromLeft(int pos){
      int numCalls = 0;
      for (int i=0; i < pos; i++){
         numCalls += 1;
         int element = sortedBookPile.removeFront();
         tempBookPile.addLast(element);
      }
      return numCalls;
   }

   /**
    * Helper method to add element from left of the bookshelf by accessing elements from temporary bookshelf.
    * @return the number of calls to mutator functions.
    */
   private int addElemFromLeft(){
      int numCalls = 0;
      while (tempBookPile.size() > 0){
         numCalls += 1;
         sortedBookPile.addFront(tempBookPile.removeLast());
      }
      return numCalls;
   }

   /**
    * Helper method to remove element from right of the bookshelf and storing it in a temporary bookshelf.
    * @param pos position until which books are removed.
    * @return the number of calls to mutator functions.
    */
   private int removeBookFromRight(int pos){
      int numCalls = 0;
      for (int i=sortedBookPile.size()-1; i > pos; i--){
         numCalls += 1;
         int element = sortedBookPile.removeLast();
         tempBookPile.addFront(element);
      }
      return numCalls;

   }

   /**
    * Helper method to add element from right of the bookshelf by accessing elements from temporary bookshelf.
    * @return the number of calls to mutator functions.
    */
   private int addElemFromRight(){
      int numCalls = 0;
      while (tempBookPile.size() > 0){
         numCalls += 1;
         sortedBookPile.addLast(tempBookPile.removeFront());
      }
      return numCalls;
   }


}
