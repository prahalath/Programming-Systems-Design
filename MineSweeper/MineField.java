// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CS 455 PA3
// Fall 2020


import java.util.Random;

/**
   MineField
      class with locations of mines for a game.
      This class is mutable, because we sometimes need to change it once it's created.
      mutators: populateMineField, resetEmpty
      includes convenience method to tell the number of mines adjacent to a location.
 */
public class MineField {
   
   // <put instance variables here>
    private boolean[][] mineArray;
    private int numOfMines;
   
   
   
   /**
      Create a minefield with same dimensions as the given array, and populate it with the mines in the array
      such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa.  numMines() for
      this minefield will corresponds to the number of 'true' values in mineData.
      @param mineData  the data for the mines; must have at least one row and one col,
                       and must be rectangular (i.e., every row is the same length)
    */
   public MineField(boolean[][] mineData) {
       int rows = mineData.length;
       int cols = mineData[0].length; // the length of a row will be the number of columns
       numOfMines = 0;
       mineArray = new boolean[rows][cols];
       for (int row = 0; row < rows; row++){
           for (int col = 0; col < cols; col++){
               mineArray[row][col] = mineData[row][col];
               if (mineArray[row][col] == true){
                   numOfMines += 1;
               }
           }
       }
      
   }
   
   
   /**
      Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once 
      populateMineField is called on this object).  Until populateMineField is called on such a MineField, 
      numMines() will not correspond to the number of mines currently in the MineField.
      @param numRows  number of rows this minefield will have, must be positive
      @param numCols  number of columns this minefield will have, must be positive
      @param numMines   number of mines this minefield will have,  once we populate it.
      PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
    */
   public MineField(int numRows, int numCols, int numMines) {
       numOfMines = numMines;
       mineArray = new boolean[numRows][numCols];
       resetEmpty();
   }
      

   

   /**
      Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
      ensuring that no mine is placed at (row, col).
      @param row the row of the location to avoid placing a mine
      @param col the column of the location to avoid placing a mine
      PRE: inRange(row, col)
    */
   public void populateMineField(int row, int col) {
       Random random = new Random();
       int numMines = numMines();
       int randNum1 = 0;
       int randNum2 = 0;
       resetEmpty();
       while (numMines > 0){
           randNum1 = random.nextInt(mineArray.length);
           randNum2 = random.nextInt(mineArray[0].length);
           if ((randNum1 == row && randNum2 == col) || mineArray[randNum1][randNum2] == true) {
               continue;
           }
           mineArray[randNum1][randNum2] = true;
           numMines -= 1;
       }
   }



    /**
      Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or numCols()
      Thus, after this call, the actual number of mines in the minefield does not match numMines().  
      Note: This is the state a minefield created with the three-arg constructor is in 
         at the beginning of a game.
    */
   public void resetEmpty() {
       for (int row = 0; row < mineArray.length; row++){
           for (int col = 0; col < mineArray[0].length; col++){
               mineArray[row][col] = false;
           }
       }
   }

   
  /**
     Returns the number of mines adjacent to the specified mine location (not counting a possible 
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
   */
   public int numAdjacentMines(int row, int col) {
       int numAdjMines = 0;
       int[] rowDirections = new int[]{-1, -1, -1, 0, 0, 1, 1, 1}; // all possible row directions
       int[] colDirections = new int[]{-1, 0, 1, -1, 1, -1, 0, 1}; // all possible column directions
       for (int i = 0; i < rowDirections.length; i++){
           if (inRange(row + rowDirections[i], col + colDirections[i])) {
               if (mineArray[row + rowDirections[i]][col + colDirections[i]] == true) {
                   numAdjMines = numAdjMines + 1;
               }
           }
       }
       return numAdjMines;
   }
   
   
   /**
      Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
      start from 0.
      @param row  row of the location to consider
      @param col  column of the location to consider
      @return whether (row, col) is a valid field location
   */
   public boolean inRange(int row, int col) {
       if (row >= mineArray.length || col >= mineArray[0].length || col < 0 || row < 0) {
           return false;
       }
       return true;
   }
   
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */  
   public int numRows() {
      return mineArray.length;
   }
   
   
   /**
      Returns the number of columns in the field.
      @return number of columns in the field
   */    
   public int numCols() {

       return mineArray[0].length;
   }
   
   
   /**
      Returns whether there is a mine in this square
      @param row  row of the location to check
      @param col  column of the location to check
      @return whether there is a mine in this square
      PRE: inRange(row, col)   
   */    
   public boolean hasMine(int row, int col) {

       return mineArray[row][col];
   }
   
   
   /**
      Returns the number of mines you can have in this minefield.  For mines created with the 3-arg constructor,
      some of the time this value does not match the actual number of mines currently on the field.  See doc for that
      constructor, resetEmpty, and populateMineField for more details.
    * @return
    */
   public int numMines() {

       return numOfMines;
   }

   
   // <put private methods here>
   
         
}

